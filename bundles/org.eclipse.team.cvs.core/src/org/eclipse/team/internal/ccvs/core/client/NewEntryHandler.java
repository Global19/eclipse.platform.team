/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.team.internal.ccvs.core.client;


import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.team.internal.ccvs.core.*;
import org.eclipse.team.internal.ccvs.core.syncinfo.MutableResourceSyncInfo;
import org.eclipse.team.internal.ccvs.core.syncinfo.ResourceSyncInfo;

public class NewEntryHandler extends ResponseHandler {

	/*
	 * @see ResponseHandler#getResponseID()
	 */
	public String getResponseID() {
		return "New-entry"; //$NON-NLS-1$
	}

	/*
	 * @see ResponseHandler#handle(Session, String, IProgressMonitor)
	 */
	public void handle(Session session, String localDir, IProgressMonitor monitor)
		throws CVSException {
			
		// read additional data for the response
		String repositoryFile = session.readLine();
		String entryLine = session.readLine();
		
		// Clear the recorded mod-time
		session.setModTime(null);
		
		// Get the local file		
		String fileName = repositoryFile.substring(repositoryFile.lastIndexOf("/") + 1); //$NON-NLS-1$
		ICVSFolder mParent = session.getLocalRoot().getFolder(localDir);
		ICVSFile mFile = mParent.getFile(fileName);

		ResourceSyncInfo fileInfo = mFile.getSyncInfo();
		MutableResourceSyncInfo newInfo = fileInfo.cloneMutable();
		newInfo.setEntryLine(entryLine);
		// see bug 106876
		if(newInfo.getTag() != null && newInfo.getTag().getName().equals(CVSTag.BASE.getName())) { 
			newInfo.setTag(fileInfo.getTag());
		}
		mFile.setSyncInfo(newInfo, ICVSFile.UNKNOWN);
	}
}
