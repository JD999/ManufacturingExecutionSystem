/*******************************************************************************
 * Copyright (c) 2011 Kai Toedter and others.
 * 
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Kai Toedter - initial API and implementation
 ******************************************************************************/

package com.bafconsulting.e4.ui.workbench.addons.generic;

import java.util.List;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MAddon;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.commands.MCommand;
import org.eclipse.e4.ui.model.application.commands.MCommandsFactory;
import org.eclipse.e4.ui.model.application.commands.MHandler;
import org.eclipse.e4.ui.model.application.impl.ApplicationFactoryImpl;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.model.application.ui.menu.MHandledMenuItem;
import org.eclipse.e4.ui.model.application.ui.menu.MMenu;
import org.eclipse.e4.ui.model.application.ui.menu.MMenuFactory;

@SuppressWarnings("restriction")
public class MinMaxProcessor {
	private static final String BUNDLE_ID = "platform:/plugin/com.bafconsulting.e4.ui.workbench.addons.generic"; //$NON-NLS-1$
	private static final String BUNDLE_CLASS = "bundleclass://com.bafconsulting.e4.ui.workbench.addons.generic"; //$NON-NLS-1$

	public static final String GEN_MIN_MAX_CONTRIBUTION_URI = BUNDLE_CLASS
			+ "/com.bafconsulting.e4.ui.workbench.addons.generic.minmax.GenericMinMaxAddon";

	@Execute
	void addMinMaxAddon(MApplication application) {
		List<MAddon> addons = application.getAddons();

		// Prevent multiple copies
		for (MAddon addon : addons) {
			if (addon.getContributionURI().equals(GEN_MIN_MAX_CONTRIBUTION_URI)) {
				return;
			}
		}

		// Insert the addon into the system
		MAddon minMaxAddon = ApplicationFactoryImpl.eINSTANCE.createAddon();
		minMaxAddon.setElementId("GenericMinMaxAddon"); //$NON-NLS-1$
		minMaxAddon.setContributionURI(GEN_MIN_MAX_CONTRIBUTION_URI);
		application.getAddons().add(minMaxAddon);

		MCommand resetWindowsCommand = MCommandsFactory.INSTANCE.createCommand();
		resetWindowsCommand.setElementId("com.bafconsulting.e4.ui.workbench.addons.generic.testCommand");
		resetWindowsCommand.setCommandName("testCommand");
		resetWindowsCommand.setDescription("testCommand description");
		application.getCommands().add(resetWindowsCommand);

		MHandler resetWindowsHandler = MCommandsFactory.INSTANCE.createHandler();
		resetWindowsHandler.setContributionURI(BUNDLE_CLASS
				+ "/com.bafconsulting.e4.ui.workbench.addons.generic.minmax.ResetWindowsHandler");
		resetWindowsHandler.setCommand(resetWindowsCommand);
		application.getHandlers().add(resetWindowsHandler);

		MWindow mWindow = (MWindow) application.getChildren().get(0);
		MMenu mainMenu = mWindow.getMainMenu();
		System.out.println("Main Menu ID: " + mainMenu.getElementId());

		MMenu windowMenu = MMenuFactory.INSTANCE.createMenu();
		windowMenu.setLabel("%windowMenu"); //$NON-NLS-1$
		windowMenu.setContributorURI(BUNDLE_ID);
		mainMenu.getChildren().add(windowMenu);

		MHandledMenuItem menuItem = MMenuFactory.INSTANCE.createHandledMenuItem();
		menuItem.setContributorURI(BUNDLE_ID);
		menuItem.setLabel("%resetWindows"); //$NON-NLS-1$
		menuItem.setCommand(resetWindowsCommand);
		windowMenu.getChildren().add(menuItem);

	}
}
