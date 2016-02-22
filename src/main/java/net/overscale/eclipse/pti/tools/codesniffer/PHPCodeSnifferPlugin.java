/*******************************************************************************
 * Copyright (c) 2009, 2010, 2011 Sven Kiera
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package net.overscale.eclipse.pti.tools.codesniffer;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.osgi.framework.BundleContext;

import net.overscale.eclipse.pti.core.AbstractPHPToolPlugin;

/**
 * The activator class controls the plug-in life cycle
 */
public class PHPCodeSnifferPlugin extends AbstractPHPToolPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "net.overscale.eclipse.pti.tools.codesniffer";

	// The shared instance
	private static PHPCodeSnifferPlugin plugin;

	/**
	 * The constructor
	 */
	public PHPCodeSnifferPlugin() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext )
	 */

	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext )
	 */

	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static PHPCodeSnifferPlugin getDefault() {
		return plugin;
	}

	public IPath[] getPluginIncludePaths(IProject project) {
		IPath[] res = { resolvePluginResource("/php/tools") };

		return res;
	}
}
