dataSource {
}
hibernate {
    cache.use_second_level_cache=true
    cache.use_query_cache=true
    cache.provider_class='com.opensymphony.oscache.hibernate.OSCacheProvider'
}
// environment specific settings
environments {
	development {
		dataSource {
		}
	}
	test {
		dataSource {
		}
	}
	production {
		dataSource {

		}
	}
}