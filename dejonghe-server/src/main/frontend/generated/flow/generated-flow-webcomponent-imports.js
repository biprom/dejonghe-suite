import { injectGlobalWebcomponentCss } from 'Frontend/generated/jar-resources/theme-util.js';

import { injectGlobalCss } from 'Frontend/generated/jar-resources/theme-util.js';

import { css, unsafeCSS, registerStyles } from '@vaadin/vaadin-themable-mixin';
import $cssFromFile_0 from 'Frontend/generated/jar-resources/styles/dateRangePicker.css?inline';
import 'Frontend/generated/jar-resources/flow-component-renderer.js';
import '@vaadin/polymer-legacy-adapter/style-modules.js';
import '@vaadin/combo-box/theme/lumo/vaadin-combo-box.js';
import 'Frontend/generated/jar-resources/comboBoxConnector.js';
import 'Frontend/generated/jar-resources/vaadin-grid-flow-selection-column.js';
import '@vaadin/grid/theme/lumo/vaadin-grid-column.js';
import '@vaadin/tooltip/theme/lumo/vaadin-tooltip.js';
import '@vaadin/icon/theme/lumo/vaadin-icon.js';
import '@vaadin/upload/theme/lumo/vaadin-upload.js';
import '@vaadin/context-menu/theme/lumo/vaadin-context-menu.js';
import 'Frontend/generated/jar-resources/contextMenuConnector.js';
import 'Frontend/generated/jar-resources/contextMenuTargetConnector.js';
import '@vaadin/form-layout/theme/lumo/vaadin-form-item.js';
import '@vaadin/multi-select-combo-box/theme/lumo/vaadin-multi-select-combo-box.js';
import '@vaadin/grid/theme/lumo/vaadin-grid.js';
import '@vaadin/grid/theme/lumo/vaadin-grid-sorter.js';
import '@vaadin/checkbox/theme/lumo/vaadin-checkbox.js';
import 'Frontend/generated/jar-resources/gridConnector.ts';
import '@vaadin/button/theme/lumo/vaadin-button.js';
import '@vaadin/split-layout/theme/lumo/vaadin-split-layout.js';
import '@vaadin/checkbox-group/theme/lumo/vaadin-checkbox-group.js';
import 'Frontend/generated/jar-resources/menubarConnector.js';
import '@vaadin/menu-bar/theme/lumo/vaadin-menu-bar.js';
import '@vaadin/form-layout/theme/lumo/vaadin-form-row.js';
import '@vaadin/text-field/theme/lumo/vaadin-text-field.js';
import '@vaadin/icons/vaadin-iconset.js';
import '@vaadin/date-picker/theme/lumo/vaadin-date-picker.js';
import 'Frontend/generated/jar-resources/datepickerConnector.js';
import '@vaadin/form-layout/theme/lumo/vaadin-form-layout.js';
import '@vaadin/dialog/theme/lumo/vaadin-dialog.js';
import '@vaadin/charts/theme/lumo/vaadin-chart.js';
import '@vaadin/text-area/theme/lumo/vaadin-text-area.js';
import '@vaadin/vertical-layout/theme/lumo/vaadin-vertical-layout.js';
import '@vaadin/horizontal-layout/theme/lumo/vaadin-horizontal-layout.js';
import 'Frontend/generated/jar-resources/vaadin-spreadsheet/vaadin-spreadsheet.js';
import 'Frontend/generated/jar-resources/disableOnClickFunctions.js';
import '@vaadin/select/theme/lumo/vaadin-select.js';
import 'Frontend/generated/jar-resources/selectConnector.js';
import '@vaadin/grid/theme/lumo/vaadin-grid-column-group.js';
import 'Frontend/generated/jar-resources/lit-renderer.ts';
import '@vaadin/confirm-dialog/theme/lumo/vaadin-confirm-dialog.js';
import '@vaadin/notification/theme/lumo/vaadin-notification.js';
import '@vaadin/side-nav/theme/lumo/vaadin-side-nav.js';
import '@vaadin/grid/theme/lumo/vaadin-grid-tree-toggle.js';
import '@vaadin/app-layout/theme/lumo/vaadin-app-layout.js';
import '@vaadin/card/theme/lumo/vaadin-card.js';
import '@vaadin/virtual-list/theme/lumo/vaadin-virtual-list.js';
import 'Frontend/generated/jar-resources/virtualListConnector.js';
import '@vaadin/side-nav/theme/lumo/vaadin-side-nav-item.js';
import '@vaadin/app-layout/theme/lumo/vaadin-drawer-toggle.js';
import '@vaadin/scroller/theme/lumo/vaadin-scroller.js';
import '@vaadin/common-frontend/ConnectionIndicator.js';
import '@vaadin/vaadin-lumo-styles/sizing.js';
import '@vaadin/vaadin-lumo-styles/spacing.js';
import '@vaadin/vaadin-lumo-styles/style.js';
import '@vaadin/vaadin-lumo-styles/vaadin-iconset.js';
import 'Frontend/generated/jar-resources/ReactRouterOutletElement.tsx';

injectGlobalCss($cssFromFile_0.toString(), 'CSSImport end', document);
injectGlobalWebcomponentCss($cssFromFile_0.toString());

const loadOnDemand = (key) => {
  const pending = [];
  if (key === '7ae8dc2d559a1116ac192b3dc6b7c4ce9eb2823df613f0c0ce3427dadc4a15a3') {
    pending.push(import('./chunks/chunk-214caec37fcde86f86dace56eb9c749a9405b27d2556796678899d704a388fd9.js'));
  }
  if (key === '18713c97dd3921bf3be8992350be10d28c5c9b121747eed77d6a1eff34648c05') {
    pending.push(import('./chunks/chunk-5242cfa07318a5d51c8ea62a8c3cdbf460aab87a1e67b9632059b73b2b2f309f.js'));
  }
  if (key === 'c0fcbf36702ee3b71ae68081210d58a99edf1bdcb2827ea8f44b62f703095e36') {
    pending.push(import('./chunks/chunk-cb85724a0ba96ca25811403f9413e81fb725278a7bcfd1324d4a85e2d75aa98d.js'));
  }
  if (key === 'ea868aade4b3f008734fa35008db2e520f14c6456bd6acb60cf769538703550d') {
    pending.push(import('./chunks/chunk-a0ad3470aba25f752028ced7735bff8a447eeba1640df964c4e1b803358a336b.js'));
  }
  if (key === '5767a8ce4b5b1f6265f2cf1bb2bff5abb555b86a3b4ac59a4699ecee93c6178c') {
    pending.push(import('./chunks/chunk-448d5fec3738ff0bb02f3c0f1cd46e7650819c86f04e0f067b46b3c8c0d4b402.js'));
  }
  if (key === '71f0fb3a229dcb5025208f4222e9350d1c28efc15c87d012d19556338aebf395') {
    pending.push(import('./chunks/chunk-93dc686cdf81d42540b708258b1ba83e8f42d2391917fb2ce9a312003816d2c8.js'));
  }
  if (key === '4b4037e4dcdbe5a031929ef11f741e26bf66da62a1588992573aae92d4dbe23c') {
    pending.push(import('./chunks/chunk-07314c88986c1d7c14eba0c9921a913e5bb023740d8b16398c8ffae57e62c2be.js'));
  }
  if (key === '0f9f0a89df1e973758bb0c23a0f190d5509e8074a5864f478b241dfd8a63d5fe') {
    pending.push(import('./chunks/chunk-97bfa442ac324d95f3609fce4573bc99e99da998fa3c362ada045cfe555da73e.js'));
  }
  if (key === 'c4c17cf4668b46f0f22e893de2a0226b0c1bc0616ca740a6dc4e2501c9ec1bc5') {
    pending.push(import('./chunks/chunk-6972c5b7977607829e12f371ec7001792086a54137423735ed24de256ce09e5c.js'));
  }
  if (key === '8f733c29205a2dbe91b47fc71b48828024e7665220ff5c2bad98c7cca2276d41') {
    pending.push(import('./chunks/chunk-aca48db3aa1851de6e4c0b1ec86c558c150e77ea95c01e2b2f7b55e3e260d047.js'));
  }
  if (key === 'cfb60433fef5e0d33d2a45ffb09c152dae23a7b7c7719d26a5bb65b8315b4f1e') {
    pending.push(import('./chunks/chunk-448d5fec3738ff0bb02f3c0f1cd46e7650819c86f04e0f067b46b3c8c0d4b402.js'));
  }
  if (key === 'b2f374db44e0006484183b692366fb8fef526f026918c363628fb6ac71c10802') {
    pending.push(import('./chunks/chunk-9f6ea6ddba04aede06d4e461d1a2fca9c8cc1152fd54ecd3cbd8d469707e50c6.js'));
  }
  if (key === '6cd2a8dc631b64763b338de3d0a5af2c010c504447d07d91e41dee0d775fe4e2') {
    pending.push(import('./chunks/chunk-d37880df982770a90fc3f49640e5cc5ef0246ffaea8442f3df5d4b8591fe52e3.js'));
  }
  if (key === '5ad88d587318e7cac0e814837c4f87fcb2810b5112fc5960b3d98c184d0accb6') {
    pending.push(import('./chunks/chunk-f97e9962947a9c8c68ae52ed4216d5996a9f4d859efdf4b625d2b091352cf760.js'));
  }
  if (key === 'a030b13e49c3c633a4a695236c797691285a764d84113430cfb799ac3e9ab8d2') {
    pending.push(import('./chunks/chunk-aca48db3aa1851de6e4c0b1ec86c558c150e77ea95c01e2b2f7b55e3e260d047.js'));
  }
  if (key === 'ccfa0a18ffee5a750a43313e301d548d6f502e3a734d7e814ddc67d68da76272') {
    pending.push(import('./chunks/chunk-c33b421bafaa844a6f44bdfd778a2f13ca1f06082f9f5d4720cde725b42947dd.js'));
  }
  if (key === 'aeb0b677eefaa7efa2195888963285708b18b81faabadeef1fe9af9386b59c7a') {
    pending.push(import('./chunks/chunk-97bfa442ac324d95f3609fce4573bc99e99da998fa3c362ada045cfe555da73e.js'));
  }
  if (key === '6ba563f7f9884d7353c318a823928500fdf5b3b6c26eca362616b6400468ee6c') {
    pending.push(import('./chunks/chunk-d37880df982770a90fc3f49640e5cc5ef0246ffaea8442f3df5d4b8591fe52e3.js'));
  }
  return Promise.all(pending);
}

window.Vaadin = window.Vaadin || {};
window.Vaadin.Flow = window.Vaadin.Flow || {};
window.Vaadin.Flow.loadOnDemand = loadOnDemand;
window.Vaadin.Flow.resetFocus = () => {
 let ae=document.activeElement;
 while(ae&&ae.shadowRoot) ae = ae.shadowRoot.activeElement;
 return !ae || ae.blur() || ae.focus() || true;
}