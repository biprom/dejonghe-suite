import { injectGlobalWebcomponentCss } from 'Frontend/generated/jar-resources/theme-util.js';

import { injectGlobalCss } from 'Frontend/generated/jar-resources/theme-util.js';

import { css, unsafeCSS, registerStyles } from '@vaadin/vaadin-themable-mixin';
import $cssFromFile_66 from '@vaadin/vaadin-lumo-styles/lumo.css?inline';
import $cssFromFile_69 from 'Frontend/generated/jar-resources/styles/dateRangePicker.css?inline';
import $cssFromFile_70 from '@splidejs/splide/dist/css/splide.min.css?inline';
import $cssFromFile_71 from '@splidejs/splide-extension-video/dist/css/splide-extension-video.min.css?inline';
import $cssFromFile_72 from 'Frontend/generated/jar-resources/styles/splide.css?inline';
import '@vaadin/vertical-layout/src/vaadin-vertical-layout.js';
import '@vaadin/notification/src/vaadin-notification.js';
import 'Frontend/generated/jar-resources/flow-component-renderer.js';
import 'Frontend/generated/jar-resources/flow-component-directive.js';
import 'lit';
import '@vaadin/dialog/src/vaadin-dialog.js';
import '@vaadin/grid/src/vaadin-grid.js';
import '@vaadin/grid/src/vaadin-grid-column.js';
import '@vaadin/grid/src/vaadin-grid-sorter.js';
import '@vaadin/checkbox/src/vaadin-checkbox.js';
import 'Frontend/generated/jar-resources/gridConnector.ts';
import '@vaadin/component-base/src/debounce.js';
import '@vaadin/component-base/src/async.js';
import '@vaadin/grid/src/vaadin-grid-active-item-mixin.js';
import 'Frontend/generated/jar-resources/vaadin-grid-flow-selection-column.js';
import '@vaadin/tooltip/src/vaadin-tooltip.js';
import '@vaadin/grid/src/vaadin-grid-column-group.js';
import 'Frontend/generated/jar-resources/lit-renderer.ts';
import 'lit/directives/live.js';
import '@vaadin/context-menu/src/vaadin-context-menu.js';
import 'Frontend/generated/jar-resources/contextMenuConnector.js';
import 'Frontend/generated/jar-resources/contextMenuTargetConnector.js';
import '@vaadin/component-base/src/gestures.js';
import 'Frontend/generated/jar-resources/disableOnClickFunctions.js';
import '@vaadin/button/src/vaadin-button.js';
import '@vaadin/icons/vaadin-iconset.js';
import '@vaadin/icon/src/vaadin-icon.js';
import '@vaadin/confirm-dialog/src/vaadin-confirm-dialog.js';
import '@vaadin/horizontal-layout/src/vaadin-horizontal-layout.js';
import '@vaadin/text-area/src/vaadin-text-area.js';
import '@vaadin/text-field/src/vaadin-text-field.js';
import '@vaadin/number-field/src/vaadin-number-field.js';
import '@vaadin/radio-group/src/vaadin-radio-group.js';
import '@vaadin/radio-group/src/vaadin-radio-button.js';
import '@vaadin/form-layout/src/vaadin-form-layout.js';
import '@vaadin/form-layout/src/vaadin-form-item.js';
import '@vaadin/form-layout/src/vaadin-form-row.js';
import '@vaadin/split-layout/src/vaadin-split-layout.js';
import 'Frontend/generated/jar-resources/menubarConnector.js';
import '@vaadin/menu-bar/src/vaadin-menu-bar.js';
import '@vaadin/date-picker/src/vaadin-date-picker.js';
import 'Frontend/generated/jar-resources/datepickerConnector.js';
import 'date-fns/parse';
import '@vaadin/date-picker/src/vaadin-date-picker-helper.js';
import '@vaadin/select/src/vaadin-select.js';
import 'Frontend/generated/jar-resources/selectConnector.js';
import '@vaadin/vaadin-lumo-styles/vaadin-iconset.js';
import '@vaadin/combo-box/src/vaadin-combo-box.js';
import 'Frontend/generated/jar-resources/comboBoxConnector.js';
import '@vaadin/combo-box/src/vaadin-combo-box-placeholder.js';
import '@vaadin/multi-select-combo-box/src/vaadin-multi-select-combo-box.js';
import '@vaadin/upload/src/vaadin-upload.js';
import 'Frontend/generated/jar-resources/vaadin-upload-manager-connector.ts';
import '@vaadin/upload/vaadin-upload-manager.js';
import 'Frontend/generated/jar-resources/vaadin-spreadsheet/vaadin-spreadsheet.js';
import 'Frontend/generated/jar-resources/vaadin-spreadsheet/spreadsheet-export.js';
import 'Frontend/generated/jar-resources/vaadin-spreadsheet/vaadin-spreadsheet-styles.js';
import '@vaadin/charts/src/vaadin-chart.js';
import '@vaadin/checkbox-group/src/vaadin-checkbox-group.js';
import '@vaadin/app-layout/src/vaadin-app-layout.js';
import '@vaadin/side-nav/src/vaadin-side-nav.js';
import '@vaadin/side-nav/src/vaadin-side-nav-item.js';
import '@vaadin/app-layout/src/vaadin-drawer-toggle.js';
import '@vaadin/scroller/src/vaadin-scroller.js';
import '@vaadin/card/src/vaadin-card.js';
import '@vaadin/virtual-list/src/vaadin-virtual-list.js';
import 'Frontend/generated/jar-resources/virtualListConnector.js';
import '@vaadin/grid/src/vaadin-grid-tree-toggle.js';
import 'Frontend/generated/jar-resources/treeGridConnector.ts';
import 'Frontend/generated/jar-resources/src/vcf-splide.js';
import '@splidejs/splide';
import '@splidejs/splide-extension-video';
import '@vaadin/integer-field/src/vaadin-integer-field.js';
import '@vaadin/common-frontend/ConnectionIndicator.js';
import 'Frontend/generated/jar-resources/ReactRouterOutletElement.tsx';
import 'react-router';
import 'react';

injectGlobalWebcomponentCss($cssFromFile_66.toString());
injectGlobalWebcomponentCss($cssFromFile_69.toString());
injectGlobalWebcomponentCss($cssFromFile_70.toString());
injectGlobalWebcomponentCss($cssFromFile_71.toString());
injectGlobalWebcomponentCss($cssFromFile_72.toString());
const loadOnDemand = (key) => {
  const pending = [];
  if (key === 'aeb0b677eefaa7efa2195888963285708b18b81faabadeef1fe9af9386b59c7a') {
    pending.push(import('./chunks/chunk-252cb5d5c88981735ec08cbe7b250ef8c9fcf6f778d86fab5a386f40df9008a1.js'));
  }
  if (key === 'b9a56c42df0eaec148debc666cfd7e494e227ae7a1280e1e953c0a60f4633e4e') {
    pending.push(import('./chunks/chunk-b35a9750a34682ed2e962f563229bb4b253f866c15c53922e26a2404cc040077.js'));
  }
  if (key === '3ed86d8aba65df9a61f298883238eec107460a35f276367a0f24bc6b3f401c7e') {
    pending.push(import('./chunks/chunk-5e2b9ebcf6fbbab2c66b4b9d1980d5e722ac31f9b93c9c7df827d274b865c121.js'));
  }
  if (key === '6ba563f7f9884d7353c318a823928500fdf5b3b6c26eca362616b6400468ee6c') {
    pending.push(import('./chunks/chunk-9a277da57be547c7c249a1f242bacf303dbfca7b3c9befdd6c75803880dd26b7.js'));
  }
  if (key === 'b2f374db44e0006484183b692366fb8fef526f026918c363628fb6ac71c10802') {
    pending.push(import('./chunks/chunk-35660ab4a048a2b91f26438be6ffc1bb1b0068768a8e1e6344f55c59b7c47a86.js'));
  }
  if (key === '47f3986dee4bffb09e40a9ccb3424de224375f2d188570acf053a78e35702c38') {
    pending.push(import('./chunks/chunk-28600be230e3fe91c7e8f73242f846a793664adbe45550b4578aff880015bf06.js'));
  }
  if (key === '23728de529d57a42175025728350e94667c2e8ebeedf43cbfd12aa978807b9a4') {
    pending.push(import('./chunks/chunk-dba0a85cec59ba5857aa85b0351ba3c2ff691e43c0c418e5c9f5eb5d850b06cf.js'));
  }
  if (key === '7ae8dc2d559a1116ac192b3dc6b7c4ce9eb2823df613f0c0ce3427dadc4a15a3') {
    pending.push(import('./chunks/chunk-e8742cf9fab43c7f68d6c58b3fa0db931570731755f44791ba856559e2e2cb85.js'));
  }
  if (key === '71f0fb3a229dcb5025208f4222e9350d1c28efc15c87d012d19556338aebf395') {
    pending.push(import('./chunks/chunk-5de90a66bcd94eb9aa4a0f4da97a9d892a93e4253666716ce900de224df00fc3.js'));
  }
  if (key === 'dfadbc3b335871ea9c35674ba36f5c90c3b0d415026aff4150dc493da639f72f') {
    pending.push(import('./chunks/chunk-90af9de68b1b10d54125e99352405a58f050e5740ac491645d362c879e86afac.js'));
  }
  if (key === '8f733c29205a2dbe91b47fc71b48828024e7665220ff5c2bad98c7cca2276d41') {
    pending.push(import('./chunks/chunk-d49050a60e79e7af4d855f82b8520c773f789f23e0ce06b88a786e8dfd91e561.js'));
  }
  if (key === 'c4c17cf4668b46f0f22e893de2a0226b0c1bc0616ca740a6dc4e2501c9ec1bc5') {
    pending.push(import('./chunks/chunk-2f2e00d13d28de2073fbb278e927bae94ba869f4adcba9e2481b181f5218d63e.js'));
  }
  if (key === 'cfb60433fef5e0d33d2a45ffb09c152dae23a7b7c7719d26a5bb65b8315b4f1e') {
    pending.push(import('./chunks/chunk-91ec646cd3cbecfe5ef0df870d88853694ab658c68859317cdde744e928a9053.js'));
  }
  if (key === '4b4037e4dcdbe5a031929ef11f741e26bf66da62a1588992573aae92d4dbe23c') {
    pending.push(import('./chunks/chunk-71223c8ea6b90b46b0c61c67bd9b4551123683a7e25595cd38fef788ca674582.js'));
  }
  if (key === 'c0fcbf36702ee3b71ae68081210d58a99edf1bdcb2827ea8f44b62f703095e36') {
    pending.push(import('./chunks/chunk-e2f72616b664b890a62cbba47272cfd21237ff85a307324e143e7874fed32b19.js'));
  }
  if (key === '5767a8ce4b5b1f6265f2cf1bb2bff5abb555b86a3b4ac59a4699ecee93c6178c') {
    pending.push(import('./chunks/chunk-91ec646cd3cbecfe5ef0df870d88853694ab658c68859317cdde744e928a9053.js'));
  }
  if (key === '0f9f0a89df1e973758bb0c23a0f190d5509e8074a5864f478b241dfd8a63d5fe') {
    pending.push(import('./chunks/chunk-b6b960cc4f99e0a36c70d1c7b13362c2839a259eb5c32bde28f4236d9876051f.js'));
  }
  if (key === '6cd2a8dc631b64763b338de3d0a5af2c010c504447d07d91e41dee0d775fe4e2') {
    pending.push(import('./chunks/chunk-968d2553c8a1c82368b2bf2520b5828c996f8f209c10cf63320a35e9704e63f9.js'));
  }
  if (key === '5ad88d587318e7cac0e814837c4f87fcb2810b5112fc5960b3d98c184d0accb6') {
    pending.push(import('./chunks/chunk-7dfafe1b4940bd5f2008d46d6177ddcc5a184304992673db2ebe275aadb70aa4.js'));
  }
  if (key === '18713c97dd3921bf3be8992350be10d28c5c9b121747eed77d6a1eff34648c05') {
    pending.push(import('./chunks/chunk-5e71d5837938827877c8edc160eed8bd678fe287d6c75882e5bcdb539741eb58.js'));
  }
  if (key === 'ccfa0a18ffee5a750a43313e301d548d6f502e3a734d7e814ddc67d68da76272') {
    pending.push(import('./chunks/chunk-8e97e30e75a23589cabe0bd06acbc87365327e79e9b74b52f14fe9bc5b515820.js'));
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