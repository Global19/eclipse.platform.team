/*******************************************************************************
 * Copyright (c) 2000, 2003 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.team.internal.ccvs.ui.model;


import org.eclipse.team.internal.ccvs.core.CVSTag;
import org.eclipse.team.internal.ccvs.core.ICVSRemoteFolder;
import org.eclipse.team.internal.ccvs.ui.CVSUIMessages;
import org.eclipse.team.internal.ccvs.ui.ICVSUIConstants;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;

public class CVSRemoteFolderPropertySource implements IPropertySource {
	ICVSRemoteFolder folder;
	
	// Property Descriptors
	static protected IPropertyDescriptor[] propertyDescriptors = new IPropertyDescriptor[2];
	{
		PropertyDescriptor descriptor;
		String category = CVSUIMessages.cvs; 
		
		// resource name
		descriptor = new PropertyDescriptor(ICVSUIConstants.PROP_NAME, CVSUIMessages.CVSRemoteFolderPropertySource_name); 
		descriptor.setAlwaysIncompatible(true);
		descriptor.setCategory(category);
		propertyDescriptors[0] = descriptor;
		// tag
		descriptor = new PropertyDescriptor(ICVSUIConstants.PROP_TAG, CVSUIMessages.CVSRemoteFolderPropertySource_tag); 
		descriptor.setAlwaysIncompatible(true);
		descriptor.setCategory(category);
		propertyDescriptors[1] = descriptor;
	}

	/**
	 * Create a PropertySource and store its file
	 */
	public CVSRemoteFolderPropertySource(ICVSRemoteFolder folder) {
		this.folder = folder;
	}
	
	/**
	 * Do nothing because properties are read only.
	 */
	public Object getEditableValue() {
		return this;
	}

	/**
	 * Return the Property Descriptors for the receiver.
	 */
	public IPropertyDescriptor[] getPropertyDescriptors() {
		return propertyDescriptors;
	}

	/*
	 * @see IPropertySource#getPropertyValue(Object)
	 */
	public Object getPropertyValue(Object id) {
		if (id.equals(ICVSUIConstants.PROP_NAME)) {
			return folder.getName();
		}
		if (id.equals(ICVSUIConstants.PROP_TAG)) {
			CVSTag tag = folder.getTag();
			if (tag == null) {
				return CVSUIMessages.CVSRemoteFolderPropertySource_none; 
			}
			return tag.getName();
		}
		return ""; //$NON-NLS-1$
	}

	/**
	 * Answer true if the value of the specified property 
	 * for this object has been changed from the default.
	 */
	public boolean isPropertySet(Object property) {
		return false;
	}
	/**
	 * Reset the specified property's value to its default value.
	 * Do nothing because properties are read only.
	 * 
	 * @param   property    The property to reset.
	 */
	public void resetPropertyValue(Object property) {
	}
	/**
	 * Do nothing because properties are read only.
	 */
	public void setPropertyValue(Object name, Object value) {
	}
}
