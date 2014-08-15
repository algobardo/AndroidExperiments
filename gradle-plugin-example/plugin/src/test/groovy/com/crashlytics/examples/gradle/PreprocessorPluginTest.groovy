package com.crashlytics.examples.gradle

import org.junit.Test
import org.gradle.testfixtures.ProjectBuilder
import org.gradle.api.Project
import static org.junit.Assert.*

class PreprocessorPluginTest {

    @Test
    public void crashlyticsPluginHasCustomizableManifest() {
        Project project = ProjectBuilder.builder().build()
        project.apply plugin: 'crashlytics'
        String otherString = 'some/other/path/to/AndroidManifest.xml'
        project.configure(project) {
          crashlytics {
            manifestPath = otherString
          }
        }

        assertTrue(project.getExtensions().findByName('crashlytics').manifestPath.equals(otherString))
    }
}
