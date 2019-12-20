/**
 * 
 *    Copyright 2017 Florian Erhard
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 * 
 */
package gedi.startup;

import gedi.app.Startup;
import gedi.app.classpath.ClassPath;
import gedi.centeredDiskIntervalTree.CenteredDiskIntervalTreeStorage;
import gedi.centeredDiskIntervalTree.CenteredDiskIntervalTreeStorageLoader;
import gedi.core.region.GenomicRegionStorage;
import gedi.core.region.GenomicRegionStorageCapabilities;
import gedi.core.region.GenomicRegionStorageExtensionPoint;
import gedi.core.region.intervalTree.MemoryIntervalTreeStorage;
import gedi.core.workspace.loader.WorkspaceItemLoaderExtensionPoint;

public class CitStartup implements Startup {

	@Override
	public void accept(ClassPath t) {
		
		WorkspaceItemLoaderExtensionPoint.getInstance().addExtension(CenteredDiskIntervalTreeStorageLoader.class,"cit");
		
		
		GenomicRegionStorageExtensionPoint.getInstance().addExtension(CenteredDiskIntervalTreeStorage.class,
				GenomicRegionStorageCapabilities.Disk,
				GenomicRegionStorageCapabilities.Fill
				);
		
	}
	
	
}
