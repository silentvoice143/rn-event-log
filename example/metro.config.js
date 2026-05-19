const path = require('path');
const { getDefaultConfig } = require('@react-native/metro-config');

const projectRoot = __dirname;
const workspaceRoot = path.resolve(projectRoot, '..');

/**
 * Metro configuration
 * https://reactnative.dev/docs/metro
 *
 * @type {import('metro-config').MetroConfig}
 */
const config = getDefaultConfig(projectRoot);

// Watch the workspace root
config.watchFolders = [workspaceRoot];

// Resolve packages from both example and root node_modules
config.resolver.nodeModulesPaths = [
  path.resolve(projectRoot, 'node_modules'),
  path.resolve(workspaceRoot, 'node_modules'),
];

// Enable workspace package resolution
config.resolver.disableHierarchicalLookup = false;

module.exports = config;
