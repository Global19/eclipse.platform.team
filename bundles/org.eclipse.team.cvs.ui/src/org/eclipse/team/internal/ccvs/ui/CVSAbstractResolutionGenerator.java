/*******************************************************************************
 * Copyright (c) 2002 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Common Public License v0.5
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v05.html
 * 
 * Contributors:
 * IBM - Initial API and implementation
 ******************************************************************************/

package org.eclipse.team.internal.ccvs.ui;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IMarkerResolutionGenerator;
import org.eclipse.ui.IWorkbenchWindow;

public abstract class CVSAbstractResolutionGenerator implements IMarkerResolutionGenerator {
	protected void run(final IRunnableWithProgress runnable) throws InvocationTargetException, InterruptedException {
		final Exception[] exception = new Exception[] {null};
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				try {
					Shell shell;
					IWorkbenchWindow window = CVSUIPlugin.getPlugin().getWorkbench().getActiveWorkbenchWindow();
					boolean disposeShell = false;
					if (window != null) {
						shell = window.getShell();
					} else {
						Display display = Display.getCurrent();
						shell = new Shell(display);
						disposeShell = true;
					}
					new ProgressMonitorDialog(shell).run(true, true, runnable);
					if (disposeShell) shell.dispose();
				} catch (InterruptedException e) {
					exception[0] = e;
				} catch (InvocationTargetException e) {
					exception[0] = e;
				}
			}
		});
		if (exception[0] != null) {
			if (exception[0] instanceof InvocationTargetException) {
				throw (InvocationTargetException)exception[0];
			} else if (exception[0] instanceof InterruptedException) {
				throw (InterruptedException)exception[0];
			} else {
				throw new InvocationTargetException(exception[0]);
			}
		}
	}
	
	/**
	 * Shows the given errors to the user.
	 * 
	 * @param status  the status containing the error
	 * @param title  the title of the error dialog
	 * @param message  the message for the error dialog
	 * @param shell  the shell to open the error dialog in
	 */
	protected void handle(Throwable exception, String title, final String message) {
		CVSUIPlugin.openError(null, title, message, exception, CVSUIPlugin.LOG_NONTEAM_EXCEPTIONS);
	}
}
