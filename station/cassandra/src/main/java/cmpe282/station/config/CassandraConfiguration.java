package cmpe282.station.config;

import java.util.Arrays;

import org.springframework.cassandra.core.keyspace.CreateKeyspaceSpecification;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.config.java.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@Configuration
@PropertySource(value = {"classpath:application.properties"})
@EnableCassandraRepositories(basePackages={"cmpe.restapi.respository"})

public class CassandraConfiguration extends AbstractCassandraConfiguration {
	
	@Bean
	public CassandraClusterFactoryBean cluster() {
		final CassandraClusterFactoryBean cluster = new CassandraClusterFactoryBean();
		cluster.setContactPoints("localhost");
		cluster.setKeyspaceCreations(Arrays.asList(new CreateKeyspaceSpecification("yourdb").ifNotExists()));
		return cluster;
	}
	
	
	@Override
	protected String getKeyspaceName() {
		return "yourdb";
	}
	
	@Override
	public SchemaAction getSchemaAction(){
		return SchemaAction.CREATE_IF_NOT_EXISTS;
	}
	
	@Override
	public String[] getEntityBasePackages(){
		return new String[]{"cmpe.restapi.entity"};
	}

}
