/*******************************************************************************
 * Copyright (c) 2009, 2010, 2011 Sven Kiera
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.hevada.eclipse.pti.tools.codesniffer.validator;

import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.compiler.problem.IProblem;
import org.eclipse.wst.validation.AbstractValidator;
import org.eclipse.wst.validation.ValidationResult;
import org.eclipse.wst.validation.ValidationState;

import com.hevada.eclipse.pti.core.PHPToolkitUtil;
import com.hevada.eclipse.pti.tools.codesniffer.ICodeSnifferConstants;
import com.hevada.eclipse.pti.tools.codesniffer.core.PHPCodeSniffer;
import com.hevada.eclipse.pti.tools.codesniffer.core.preferences.PHPCodeSnifferPreferences;
import com.hevada.eclipse.pti.tools.codesniffer.core.preferences.PHPCodeSnifferPreferencesFactory;
import com.hevada.eclipse.pti.ui.Logger;

public class PHPCodeSnifferValidator extends AbstractValidator {
	public ValidationResult validate(IResource resource, int kind, ValidationState state, IProgressMonitor monitor) {
		// process only PHP files
		if (resource.getType() != IResource.FILE) {
			return null;
		}

		PHPCodeSnifferPreferences prefs = PHPCodeSnifferPreferencesFactory.factory(resource);
		String[] fileExtensions = prefs.getFileExtensions();
		if ((fileExtensions == null || fileExtensions.length == 0)) {
			if (!(PHPToolkitUtil.isPhpFile((IFile) resource)))
				return null;
		} else {
			String fileName = resource.getName();
			boolean allowed = false;
			for (String ext : fileExtensions) {
				if (fileName.endsWith("." + ext)) {
					allowed = true;
					break;
				}
			}

			if (!allowed)
				return null;
		}

		ValidationResult result = new ValidationResult();
		validateFile(result, (IFile) resource, kind);
		return result;
	}

	protected void validateFile(ValidationResult result, IFile file, int kind) {
		// remove the markers currently existing for this resource
		// in case of project/folder, the markers are deleted recursively
		try {
			file.deleteMarkers(ICodeSnifferConstants.CS_MARKER_ID, false, IResource.DEPTH_INFINITE);
		} catch (CoreException e) {
		}

		PHPCodeSniffer cs = PHPCodeSniffer.getInstance();
		IProblem[] problems;
		try {
			problems = cs.parse(file);
			for (IProblem problem : problems) {
				IMarker marker = file.createMarker(ICodeSnifferConstants.CS_MARKER_ID);
				int line = problem.getSourceLineNumber();
				marker.setAttribute(IMarker.LINE_NUMBER, line);

				if (problem.isWarning()) {
					marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_WARNING);
					result.incrementWarning(1);
				} else {
					marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
					result.incrementError(1);
				}
				// IDocumentProvider provider = new TextFileDocumentProvider();
				// provider.connect(file);
				// IDocument doc = provider.getDocument(file);
				int offset = 0; // doc.getLineOffset(line - 1);

				int start = problem.getSourceStart() + offset;
				marker.setAttribute(IMarker.CHAR_START, start);
				int end = offset + problem.getSourceEnd();
				marker.setAttribute(IMarker.CHAR_END, end);
				marker.setAttribute(IMarker.MESSAGE, problem.getMessage());
			}
		} catch (CoreException | IOException e) {
			Logger.logException(e);
		}
	}
}