/*******************************************************************************
 * Copyright (c) 2000, 2017 IBM Corporation and others.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.team.tests.ccvs.core;
import org.eclipse.team.tests.ccvs.core.mappings.ResourceMapperTests;
import org.eclipse.team.tests.ccvs.core.provider.AllTestsProvider;
import org.eclipse.team.tests.ccvs.core.subscriber.AllTestsTeamSubscriber;
import org.eclipse.team.tests.ccvs.ui.AllUITests;

import org.eclipse.jface.util.Util;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests extends EclipseTest {

	public AllTests() {
		super();
	}

	public AllTests(String name) {
		super(name);
	}

	public static Test suite() {
		TestSuite suite = new TestSuite();

		// Bug 525817: Disable CVS tests on Mac
		if (Util.isMac())
			return suite;

		suite.addTest(AllTestsProvider.suite());
		suite.addTest(AllTestsTeamSubscriber.suite());
		suite.addTest(AllUITests.suite());
		suite.addTest(ResourceMapperTests.suite());
		return new CVSUITestSetup(suite);
	}
}

