package com.bafconsulting.configurator.portal.uaa.cucumber;

import org.junit.runner.RunWith;


import com.bafconsulting.configurator.portal.uaa.AbstractCassandraTest;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = "pretty", features = "src/test/features")
public class CucumberTest extends AbstractCassandraTest {

}
