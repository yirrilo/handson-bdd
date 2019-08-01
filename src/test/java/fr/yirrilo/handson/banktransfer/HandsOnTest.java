package fr.yirrilo.handson.banktransfer;
import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions( features = "src/test/resources/", plugin = {"json:target/cucumber.json"})
public class HandsOnTest {
        
}
