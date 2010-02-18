package gov.nih.nci.bda.repository;

import gov.nih.nci.bda.domain.Product;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class JdbcProductDao extends SimpleJdbcDaoSupport implements ProductDao {
    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = LogFactory.getLog(getClass());


    public List<Product> getProductList() {
        logger.info("Getting products!");
        return getSimpleJdbcTemplate().query(
                "select id, product, certification_status, single_command_build, single_command_deployment," +
                        " remote_upgrade, database_integration, template_validation, private_properties, ci_build," +
                        " deployment_shakeout, bda_enabled, commandline_installer from project_certification_status",
                new ProductMapper());
    }

    private static class ProductMapper implements ParameterizedRowMapper<Product> {

        public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
            Product prod = new Product(rs.getString("product"));
            prod.setId(rs.getInt("id"));
            prod.setCertificationStatus(rs.getString("certification_status"));
            prod.setSingleCommandBuild(rs.getString("single_command_build"));
            prod.setSingleCommandDeploy(rs.getString("single_command_deployment"));
            prod.setRemoteUpgrade(rs.getString("remote_upgrade"));
            prod.setDbIntegration(rs.getString("database_integration"));
            prod.setTemplateValidation(rs.getString("template_validation"));
            prod.setPrivateProperties(rs.getString("private_properties"));
            prod.setCiBuild(rs.getString("ci_build"));
            prod.setDeploymentShakeout(rs.getString("deployment_shakeout"));
            prod.setBdaEnabled(rs.getString("bda_enabled"));
            prod.setCommandLineInstall(rs.getString("commandline_installer"));
            return prod;
        }
    }
}
