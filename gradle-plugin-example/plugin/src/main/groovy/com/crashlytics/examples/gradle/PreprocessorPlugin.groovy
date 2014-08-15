package com.crashlytics.examples.gradle

import org.gradle.api.Project
import org.gradle.api.Plugin
import org.gradle.api.tasks.Exec
public class PreprocessorPlugin implements Plugin<Project> {

/*
  public class PreprocessorPluginExtension {
      public PreprocessorPluginExtension(String base){
        this.instrumentationPath = base;
      }
      String instrumentationPath
  }
*/
  void apply(Project project) {
    //project.extensions.create("testInstrumentationConfig", PreprocessorPluginExtension, "nothing")
    project.configure(project) {
      if(it.hasProperty("android")) {
            project.android.testVariants.all { variant ->

            String sootPath = "./soot/SootAndroidInstrumentation/bin/SootAndroidInstrumentation"

            println sootPath;

            logger.warn(variant.packageApplication.outputFile.toString())
            logger.warn(variant.packageApplication.toString())
            
            variant.javaCompile << {
              println "Working on " + variant.javaCompile.destinationDir.toString()  
            }

            def mytask = project.tasks.create("runtask${variant.baseName}",Exec.class)

            mytask.configure {
              dependsOn variant.javaCompile
              doLast {
                println 'Done instrumenting the tests'
              }
              commandLine "${sootPath} ${variant.javaCompile.destinationDir}"
            }

            variant.dex.dependsOn mytask


            /*
            variant.javaCompile.doLast {
              logger.warn("Done JAVA COMPILE.")
            }
            */
            /*
            // get output file
            def outputFile = variant.outputFile

            // add new signing task.
            def signingTask = project.tasks.create("double${variant.name}Sign", MyCustomSigning)
            // configure task
            signingTask.inputFile = outputFile
            // create the final apk name using baseName to guarantee it's unique.
            signingTask.outputFile = new File(project.buildDir, "apks/${project.name}-${variant.baseName}-2sign.apk")

            // configure the task to be run after the default signing task.
            signingTask.dependsOn variant.packageApplication

            // configure zip align task to use the output of the 2nd signing task,
            // and also to run after 2nd signing task.
            variant.zipAlign.inputFile = signingTask.outputFile
            variant.zipAlign.dependsOn signingTask
            */
          }
      }



/*


        tasks.whenTaskAdded { theTask -> 
          
              def manifestPath = "build/manifests/${flavorPath}/AndroidManifest.xml"
              
              def compileTask = "compileDebugJava".toString()
              if(compileTask.equals(theTask.name.toString())) {
                def yourTaskName = "myhelloworld"
                project.task(yourTaskName) << {
                  logger.warn("***Hello World!***")
                }
                theTask.dependsOn(yourTaskName)
                def processTask = "process${taskAffix}Resources"
                project.(yourTaskName.toString()).dependsOn(processTask)
              }
            }
          }
        }
*/
          
    }
  }
}
