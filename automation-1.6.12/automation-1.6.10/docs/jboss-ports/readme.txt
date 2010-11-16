These ports are pulled from the ncicb-bindings.xml managed by the systems team.  The following ports were not found in thier bindings file.  I added values based on a combination of the first 3 digits of the http port and the last two of the default value from jboss(if it was for 19280 and the defulat was 3873 then it would be 19273).

jboss.ejbinvoker.port=
jboss.hajrmi.port=
jboss.messaging.port=
jboss.pooledha.port=

