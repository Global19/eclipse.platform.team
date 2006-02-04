/*******************************************************************************
 * Copyright (c) 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.team.internal.core.mapping;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.mapping.ResourceMapping;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.team.core.TeamException;
import org.eclipse.team.core.mapping.IResourceMappingScopeManager;
import org.eclipse.team.core.mapping.provider.ResourceMappingScopeManager;
import org.eclipse.team.internal.core.BackgroundEventHandler;

public class ScopeManagerEventHandler extends BackgroundEventHandler {

	public static final int REFRESH = 10;
	private Set toRefresh = new HashSet();
	private IResourceMappingScopeManager manager;

	class ResourceMappingEvent extends Event {
		private final ResourceMapping[] mappings;
		public ResourceMappingEvent(ResourceMapping[] mappings) {
			super(REFRESH);
			this.mappings = mappings;
		}
	}

	public ScopeManagerEventHandler(ResourceMappingScopeManager manager) {
		super("Reconciling Scope", "Error occurred reconsiling the scope");
		this.manager = manager;
	}

	protected boolean doDispatchEvents(IProgressMonitor monitor)
			throws TeamException {
		ResourceMapping[] mappings = (ResourceMapping[]) toRefresh.toArray(new ResourceMapping[toRefresh.size()]);
		toRefresh.clear();
		if (mappings.length > 0) {
			try {
				manager.refresh(mappings, monitor);
			} catch (CoreException e) {
				throw TeamException.asTeamException(e);
			}
		}
		return mappings.length > 0;
	}

	protected void processEvent(Event event, IProgressMonitor monitor)
			throws CoreException {
		if (event instanceof ResourceMappingEvent) {
			ResourceMappingEvent rme = (ResourceMappingEvent) event;
			for (int i = 0; i < rme.mappings.length; i++) {
				ResourceMapping mapping = rme.mappings[i];
				toRefresh.add(mapping);
			}
		}

	}

	public void refresh(ResourceMapping[] mappings) {
		queueEvent(new ResourceMappingEvent(mappings), false);
	}

	protected Object getJobFamiliy() {
		return manager;
	}
}
