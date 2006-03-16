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
package org.eclipse.team.internal.ccvs.core.mapping;

import org.eclipse.team.core.subscribers.Subscriber;
import org.eclipse.team.internal.core.subscribers.ActiveChangeSet;
import org.eclipse.team.internal.core.subscribers.SubscriberChangeSetCollector;

public class CVSActiveChangeSetCollector extends SubscriberChangeSetCollector {

	public CVSActiveChangeSetCollector(Subscriber subscriber) {
		super(subscriber);
	}
	
	protected ActiveChangeSet doCreateSet(String name) {
		return new CVSActiveChangeSet(this, name);
	}

	
}