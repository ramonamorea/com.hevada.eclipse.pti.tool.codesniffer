/*******************************************************************************
 * Copyright (c) 2009, 2010, 2011 Sven Kiera
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package net.overscale.eclipse.pti.tools.codesniffer.core.preferences;

public class PHPCodeSnifferPreferences extends AbstractPEARPHPToolPreferences {
	protected Standard[] standards;
	protected int tabWidth;
	protected String ignorePattern;
	protected String[] ignoreSniffs;
	protected String[] fileExtensions;
	protected String extraArgs;

	/**
	 * Keep the old constructor for backward compatibility
	 * 
	 * @param phpExecutable
	 * @param printOutput
	 * @param phpCs
	 * @param standards
	 * @param tabWidth
	 * @param fileExtensions
	 * @param ignorePattern
	 * @param ignoreSniffs
	 */
	public PHPCodeSnifferPreferences(String phpExecutable, boolean printOutput, String phpCs, Standard[] standards, int tabWidth,
		String[] fileExtensions, String ignorePattern, String[] ignoreSniffs) {
		this(phpExecutable, printOutput, phpCs, standards, tabWidth, fileExtensions, ignorePattern, ignoreSniffs, null);
	}

	/**
	 * The new constructor utilizing extraArgs
	 * 
	 * @param phpExecutable
	 * @param printOutput
	 * @param phpCs
	 * @param standards
	 * @param tabWidth
	 * @param fileExtensions
	 * @param ignorePattern
	 * @param ignoreSniffs
	 */
	public PHPCodeSnifferPreferences(String phpExecutable, boolean printOutput, String phpCs, Standard[] standards, int tabWidth,
		String[] fileExtensions, String ignorePattern, String[] ignoreSniffs, String extraArgs) {
		super(phpExecutable, printOutput, phpCs);
		this.standards = standards;
		this.tabWidth = tabWidth;
		this.fileExtensions = fileExtensions;
		if (ignorePattern != null && ignorePattern.length() > 0)
			this.ignorePattern = ignorePattern;
		else
			this.ignorePattern = null;
		this.ignoreSniffs = ignoreSniffs;
		this.extraArgs = extraArgs;
	}

	public Standard[] getStandards() {
		return standards;
	}

	public int getTabWidth() {
		return tabWidth;
	}

	public String[] getFileExtensions() {
		return fileExtensions;
	}

	public String getIgnorePattern() {
		return ignorePattern;
	}

	public String[] getIgnoreSniffs() {
		return ignoreSniffs;
	}

	public String getExtraArgs() {
		return extraArgs;
	}
}