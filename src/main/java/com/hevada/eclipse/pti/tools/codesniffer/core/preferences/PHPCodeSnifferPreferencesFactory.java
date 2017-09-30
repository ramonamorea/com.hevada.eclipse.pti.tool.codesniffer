package com.hevada.eclipse.pti.tools.codesniffer.core.preferences;

import java.util.ArrayList;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;

public class PHPCodeSnifferPreferencesFactory {
  public static PHPCodeSnifferPreferences factory(IFile file) {
    return factory(file.getProject());
  }
  
  public static PHPCodeSnifferPreferences factory(IResource resource) {
    return factory(resource.getProject());
  }
  
  public static PHPCodeSnifferPreferences factory(IProject project) {
    String extraArgs = "", ignoreSniffs = extraArgs, ignorePattern = ignoreSniffs, fileExtensions = ignorePattern, customStandardPaths = fileExtensions, customStandardNames = customStandardPaths, phpCs = customStandardNames, activeStandards = phpCs, defaultStandard = activeStandards, phpExe = defaultStandard;
    int tabWidth = 4;
    boolean printOutput = false;
    IScopeContext[] preferenceScopes = createPreferenceScopes(project);
    if (preferenceScopes[0] instanceof ProjectScope) {
      IEclipsePreferences node = preferenceScopes[0].getNode("com.hevada.eclipse.pti.tools.codesniffer");
      if (node != null) {
        phpExe = node.get("php_executable", phpExe);
        defaultStandard = node.get("default_standard_path", defaultStandard);
        activeStandards = node.get("active_standards", activeStandards);
        phpCs = node.get("phpcs_path", phpCs);
        customStandardNames = node.get("custom_standard_names", 
            customStandardNames);
        customStandardPaths = node.get("custom_standard_paths", 
            customStandardPaths);
        tabWidth = node.getInt("default_tab_width", tabWidth);
        fileExtensions = node.get("file_extension", fileExtensions);
        printOutput = node.getBoolean("debug_print_output", printOutput);
        ignorePattern = node.get("ignore_pattern", ignorePattern);
        ignoreSniffs = node.get("pref_ignore_sniffs", ignoreSniffs);
        extraArgs = node.get("extra_args", extraArgs);
      } 
    } 
    String[] fileExtensionsList = (fileExtensions == null || fileExtensions.length() == 0) ? new String[0] : 
      fileExtensions.split(" *, *");
    String[] activeList = new String[0];
    if (activeStandards != null) {
      activeList = activeStandards.split(";");
    } else if (defaultStandard != null) {
      activeList = new String[] { defaultStandard };
    } 
    String[] customNameList = new String[0];
    String[] customPathList = new String[0];
    if (customStandardNames != null && customStandardPaths != null) {
      customNameList = customStandardNames.split(";");
      customPathList = customStandardPaths.split(";");
    } 
    ArrayList<Standard> standards = new ArrayList<>(activeList.length);
    byte b;
    int i;
    String[] arrayOfString1;
    for (i = (arrayOfString1 = activeList).length, b = 0; b < i; ) {
      String s = arrayOfString1[b];
      Standard standard = new Standard();
      standard.name = s;
      for (int j = 0; j < customNameList.length; j++) {
        if (customNameList[j].equals(s)) {
          standard.path = customPathList[j];
          standard.custom = true;
          break;
        } 
      } 
      standards.add(standard);
      b++;
    } 
    return new PHPCodeSnifferPreferences(phpExe, printOutput, phpCs, standards.<Standard>toArray(new Standard[0]), 
        tabWidth, fileExtensionsList, ignorePattern, (ignoreSniffs == null || ignoreSniffs.length() == 0) ? null : 
        ignoreSniffs.split(" *, *"), extraArgs);
  }
  
  protected static IScopeContext[] createPreferenceScopes(IProject project) {
    if (project != null)
      return new IScopeContext[] { (IScopeContext)new ProjectScope(project), InstanceScope.INSTANCE, DefaultScope.INSTANCE }; 
    return new IScopeContext[] { InstanceScope.INSTANCE, DefaultScope.INSTANCE };
  }
}
