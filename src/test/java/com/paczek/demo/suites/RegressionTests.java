package com.paczek.demo.suites;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;


@Suite
@SelectPackages( {"com.paczek.demo.tests.functional"})
public class RegressionTests {
}
