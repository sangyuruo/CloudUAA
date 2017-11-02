package com.emcloud.uaa.cucumber.stepdefs;

import com.emcloud.uaa.EmCloudUaaApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = EmCloudUaaApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
