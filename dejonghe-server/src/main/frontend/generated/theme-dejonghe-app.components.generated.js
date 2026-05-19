import { unsafeCSS, registerStyles } from '@vaadin/vaadin-themable-mixin/register-styles';

import vaadinGridCss from 'themes/dejonghe-app/components/vaadin-grid.css?inline';


if (!document['_vaadintheme_dejonghe-app_componentCss']) {
  registerStyles(
        'vaadin-grid',
        unsafeCSS(vaadinGridCss.toString())
      );
      
  document['_vaadintheme_dejonghe-app_componentCss'] = true;
}

if (import.meta.hot) {
  import.meta.hot.accept((module) => {
    window.location.reload();
  });
}

