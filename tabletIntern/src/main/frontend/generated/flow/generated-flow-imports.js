import '@vaadin/vertical-layout/src/vaadin-vertical-layout.js';
import '@vaadin/dialog/src/vaadin-dialog.js';
import 'Frontend/generated/jar-resources/flow-component-renderer.js';
import 'Frontend/generated/jar-resources/flow-component-directive.js';
import 'lit';
import '@vaadin/notification/src/vaadin-notification.js';
import '@vaadin/date-picker/src/vaadin-date-picker.js';
import 'Frontend/generated/jar-resources/datepickerConnector.js';
import 'date-fns/parse';
import '@vaadin/date-picker/src/vaadin-date-picker-helper.js';
import '@vaadin/tooltip/src/vaadin-tooltip.js';
import '@vaadin/combo-box/src/vaadin-combo-box.js';
import 'Frontend/generated/jar-resources/comboBoxConnector.js';
import '@vaadin/component-base/src/debounce.js';
import '@vaadin/component-base/src/async.js';
import '@vaadin/combo-box/src/vaadin-combo-box-placeholder.js';
import '@vaadin/multi-select-combo-box/src/vaadin-multi-select-combo-box.js';
import '@vaadin/text-field/src/vaadin-text-field.js';
import '@vaadin/button/src/vaadin-button.js';
import 'Frontend/generated/jar-resources/disableOnClickFunctions.js';
import '@vaadin/grid/src/vaadin-grid.js';
import '@vaadin/grid/src/vaadin-grid-column.js';
import '@vaadin/grid/src/vaadin-grid-sorter.js';
import '@vaadin/checkbox/src/vaadin-checkbox.js';
import 'Frontend/generated/jar-resources/gridConnector.ts';
import '@vaadin/grid/src/vaadin-grid-active-item-mixin.js';
import 'Frontend/generated/jar-resources/vaadin-grid-flow-selection-column.js';
import '@vaadin/grid/src/vaadin-grid-column-group.js';
import 'Frontend/generated/jar-resources/lit-renderer.ts';
import 'lit/directives/live.js';
import '@vaadin/context-menu/src/vaadin-context-menu.js';
import 'Frontend/generated/jar-resources/contextMenuConnector.js';
import 'Frontend/generated/jar-resources/contextMenuTargetConnector.js';
import '@vaadin/component-base/src/gestures.js';
import '@vaadin/icons/vaadin-iconset.js';
import '@vaadin/icon/src/vaadin-icon.js';
import '@vaadin/confirm-dialog/src/vaadin-confirm-dialog.js';
import '@vaadin/horizontal-layout/src/vaadin-horizontal-layout.js';
import '@vaadin/text-area/src/vaadin-text-area.js';
import 'Frontend/generated/jar-resources/menubarConnector.js';
import '@vaadin/menu-bar/src/vaadin-menu-bar.js';
import '@vaadin/form-layout/src/vaadin-form-layout.js';
import '@vaadin/form-layout/src/vaadin-form-item.js';
import '@vaadin/form-layout/src/vaadin-form-row.js';
import '@vaadin/split-layout/src/vaadin-split-layout.js';
import '@vaadin/select/src/vaadin-select.js';
import 'Frontend/generated/jar-resources/selectConnector.js';
import '@vaadin/number-field/src/vaadin-number-field.js';
import '@vaadin/radio-group/src/vaadin-radio-group.js';
import '@vaadin/radio-group/src/vaadin-radio-button.js';
import '@vaadin/vaadin-lumo-styles/vaadin-iconset.js';
import '@vaadin/grid/src/vaadin-grid-tree-toggle.js';
import 'Frontend/generated/jar-resources/treeGridConnector.ts';
import '@vaadin/app-layout/src/vaadin-app-layout.js';
import '@vaadin/side-nav/src/vaadin-side-nav.js';
import '@vaadin/side-nav/src/vaadin-side-nav-item.js';
import '@vaadin/app-layout/src/vaadin-drawer-toggle.js';
import '@vaadin/scroller/src/vaadin-scroller.js';
import '@vaadin/charts/src/vaadin-chart.js';
import '@vaadin/virtual-list/src/vaadin-virtual-list.js';
import 'Frontend/generated/jar-resources/virtualListConnector.js';
import '@vaadin/card/src/vaadin-card.js';
import '@vaadin/common-frontend/ConnectionIndicator.js';
import 'Frontend/generated/jar-resources/ReactRouterOutletElement.tsx';
import 'react-router';
import 'react';

const loadOnDemand = (key) => {
  const pending = [];
  if (key === '1d7c9355d03b403c4ed1db075d0eb4d04c937af5cc1b8290ded63265f8884f01') {
    pending.push(import('./chunks/chunk-077c098ddd6562689582cc080feae3e43a0866e59cb283afc6f58bbb0efae633.js'));
  }
  if (key === 'd8cfb5b7bb63fb9c81e7bb6c2042b31b58e4d217f7e2268f48741636c19f4ba7') {
    pending.push(import('./chunks/chunk-0c61c2bac07d3e1afcc71cb4e9c0923a92db055cc9008213642846198c7ae0d0.js'));
  }
  if (key === 'af2974be36deb5ae6c7e2e772fe70e7d5fdff6c3da6cf51b32a9aa51b243653a') {
    pending.push(import('./chunks/chunk-4a6901fb8331aa99e408bff93d9446abfc1fa80409445854d7c7c53335fe61f9.js'));
  }
  if (key === '49151b368fcfb2737be1c464fc61d6104d09ad43bd99bd3e06b5e16a0bf0ce41') {
    pending.push(import('./chunks/chunk-ce4a4d80c31b0ff01cbc37589ed01d5d55d111aee5f8d14183d7dc7681346803.js'));
  }
  if (key === '6c8612196c53ba032ee42b86fea5a02b9d6574d06d3cf461e023db23dcdeef2b') {
    pending.push(import('./chunks/chunk-1547ed6a798136be1fbdc095bfb84e9b4dea95022d36d696382ce98c46fb8487.js'));
  }
  if (key === 'fa79ee55acf44817c884482a717d50175311423309436d18ae7c6033b0251fe1') {
    pending.push(import('./chunks/chunk-693e88e6d1a6225ff93c2206db8065416c1fe8c0d4488272dd2dfa42e53d2904.js'));
  }
  if (key === 'fd08411e57170c8348ef2eefdfcc6892aca55ff9dd4cee3bdc99acc414c38ba7') {
    pending.push(import('./chunks/chunk-1fdb09cbaf646c02d4250604b3abb4e602ff96ad538ecbb471e927ef0a8d8694.js'));
  }
  if (key === 'a9f67bd58d86c5b4fed017dd7703c8a9d784dd430e0912a853e4ae26260f4074') {
    pending.push(import('./chunks/chunk-5de90a66bcd94eb9aa4a0f4da97a9d892a93e4253666716ce900de224df00fc3.js'));
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