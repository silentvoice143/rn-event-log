import Analytics from '../index';

let previousRoute: string | undefined;

export function trackNavigation(navigationRef: any) {
  const route = navigationRef?.getCurrentRoute?.();

  const currentRoute = route?.name;

  if (!currentRoute) {
    return;
  }

  if (previousRoute === currentRoute) {
    return;
  }

  previousRoute = currentRoute;

  Analytics.trackScreen(currentRoute);
}
