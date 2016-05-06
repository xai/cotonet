package br.com.riselabs.cotonet.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import br.com.riselabs.cotonet.test.suites.CotonetBuilderTestSuite;
import br.com.riselabs.cotonet.test.suites.CotonetCrawlerTestSuite;

/**
 * @author alcemirsantos
 *
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	// Crawler
	CotonetCrawlerTestSuite.class,
	// Builders
	CotonetBuilderTestSuite.class
})
public class ReposCrawlerAppTest {

	
}