function SpreadsheetApi(){var Pb='',Qb=0,Rb='gwt.codesvr=',Sb='gwt.hosted=',Tb='gwt.hybrid',Ub='SpreadsheetApi',Vb='#',Wb='?',Xb='/',Yb=1,Zb='img',$b='clear.cache.gif',_b='baseUrl',ac='script',bc='SpreadsheetApi.nocache.js',cc='base',dc='//',ec='meta',fc='name',gc='gwt:property',hc='content',ic='=',jc='gwt:onPropertyErrorFn',kc='Bad handler "',lc='" for "gwt:onPropertyErrorFn"',mc='gwt:onLoadErrorFn',nc='" for "gwt:onLoadErrorFn"',oc='modernie',pc='MSIE',qc='Trident',rc='yes',sc='none',tc='user.agent',uc='webkit',vc='safari',wc='msie',xc=10,yc=11,zc='ie10',Ac=9,Bc='ie9',Cc=8,Dc='ie8',Ec='gecko',Fc='gecko1_8',Gc=2,Hc=3,Ic=4,Jc='Single-script hosted mode not yet implemented. See issue ',Kc='http://code.google.com/p/google-web-toolkit/issues/detail?id=2079',Lc='7EB15E843C7CDC1046209FE54D546773',Mc=':1',Nc=':2',Oc=':',Pc='DOMContentLoaded',Qc=50;var l=Pb,m=Qb,n=Rb,o=Sb,p=Tb,q=Ub,r=Vb,s=Wb,t=Xb,u=Yb,v=Zb,w=$b,A=_b,B=ac,C=bc,D=cc,F=dc,G=ec,H=fc,I=gc,J=hc,K=ic,L=jc,M=kc,N=lc,O=mc,P=nc,Q=oc,R=pc,S=qc,T=rc,U=sc,V=tc,W=uc,X=vc,Y=wc,Z=xc,$=yc,_=zc,ab=Ac,bb=Bc,cb=Cc,db=Dc,eb=Ec,fb=Fc,gb=Gc,hb=Hc,ib=Ic,jb=Jc,kb=Kc,lb=Lc,mb=Mc,nb=Nc,ob=Oc,pb=Pc,qb=Qc;var rb=window,sb=document,tb,ub,vb=l,wb={},xb=[],yb=[],zb=[],Ab=m,Bb,Cb;if(!rb.__gwt_stylesLoaded){rb.__gwt_stylesLoaded={}}if(!rb.__gwt_scriptsLoaded){rb.__gwt_scriptsLoaded={}}function Db(){var b=false;try{var c=rb.location.search;return (c.indexOf(n)!=-1||(c.indexOf(o)!=-1||rb.external&&rb.external.gwtOnLoad))&&c.indexOf(p)==-1}catch(a){}Db=function(){return b};return b}
function Eb(){if(tb&&ub){tb(Bb,q,vb,Ab)}}
function Fb(){function e(a){var b=a.lastIndexOf(r);if(b==-1){b=a.length}var c=a.indexOf(s);if(c==-1){c=a.length}var d=a.lastIndexOf(t,Math.min(c,b));return d>=m?a.substring(m,d+u):l}
function f(a){if(a.match(/^\w+:\/\//)){}else{var b=sb.createElement(v);b.src=a+w;a=e(b.src)}return a}
function g(){var a=Ib(A);if(a!=null){return a}return l}
function h(){var a=sb.getElementsByTagName(B);for(var b=m;b<a.length;++b){if(a[b].src.indexOf(C)!=-1){return e(a[b].src)}}return l}
function i(){var a=sb.getElementsByTagName(D);if(a.length>m){return a[a.length-u].href}return l}
function j(){var a=sb.location;return a.href==a.protocol+F+a.host+a.pathname+a.search+a.hash}
var k=g();if(k==l){k=h()}if(k==l){k=i()}if(k==l&&j()){k=e(sb.location.href)}k=f(k);return k}
function Gb(){var b=document.getElementsByTagName(G);for(var c=m,d=b.length;c<d;++c){var e=b[c],f=e.getAttribute(H),g;if(f){if(f==I){g=e.getAttribute(J);if(g){var h,i=g.indexOf(K);if(i>=m){f=g.substring(m,i);h=g.substring(i+u)}else{f=g;h=l}wb[f]=h}}else if(f==L){g=e.getAttribute(J);if(g){try{Cb=eval(g)}catch(a){alert(M+g+N)}}}else if(f==O){g=e.getAttribute(J);if(g){try{Bb=eval(g)}catch(a){alert(M+g+P)}}}}}}
var Hb=function(a,b){return b in xb[a]};var Ib=function(a){var b=wb[a];return b==null?null:b};function Jb(a,b){var c=zb;for(var d=m,e=a.length-u;d<e;++d){c=c[a[d]]||(c[a[d]]=[])}c[a[e]]=b}
function Kb(a){var b=yb[a](),c=xb[a];if(b in c){return b}var d=[];for(var e in c){d[c[e]]=e}if(Cb){Cb(a,d,b)}throw null}
yb[Q]=function(){{var a=rb.navigator.userAgent;if(a.indexOf(R)==-1&&a.indexOf(S)!=-1){return T}return U}};xb[Q]={'none':m,'yes':u};yb[V]=function(){var a=navigator.userAgent.toLowerCase();var b=sb.documentMode;if(function(){return a.indexOf(W)!=-1}())return X;if(function(){return a.indexOf(Y)!=-1&&(b>=Z&&b<$)}())return _;if(function(){return a.indexOf(Y)!=-1&&(b>=ab&&b<$)}())return bb;if(function(){return a.indexOf(Y)!=-1&&(b>=cb&&b<$)}())return db;if(function(){return a.indexOf(eb)!=-1||b>=$}())return fb;return X};xb[V]={'gecko1_8':m,'ie10':u,'ie8':gb,'ie9':hb,'safari':ib};SpreadsheetApi.onScriptLoad=function(a){SpreadsheetApi=null;tb=a;Eb()};if(Db()){alert(jb+kb);return}Fb();Gb();try{var Lb;Jb([U,fb],lb);Jb([T,fb],lb+mb);Jb([U,X],lb+nb);Lb=zb[Kb(Q)][Kb(V)];var Mb=Lb.indexOf(ob);if(Mb!=-1){Ab=Number(Lb.substring(Mb+u))}}catch(a){return}var Nb;function Ob(){if(!ub){ub=true;Eb();if(sb.removeEventListener){sb.removeEventListener(pb,Ob,false)}if(Nb){clearInterval(Nb)}}}
if(sb.addEventListener){sb.addEventListener(pb,function(){Ob()},false)}var Nb=setInterval(function(){if(/loaded|complete/.test(sb.readyState)){Ob()}},qb)}
SpreadsheetApi();(function () {var $gwt_version = "2.9.0";var $wnd = window;var $doc = $wnd.document;var $moduleName, $moduleBase;var $stats = $wnd.__gwtStatsEvent ? function(a) {$wnd.__gwtStatsEvent(a)} : null;var $strongName = '7EB15E843C7CDC1046209FE54D546773';function K(){}
function LE(){}
function HE(){}
function Ng(){}
function Ug(){}
function fb(){}
function sb(){}
function mf(){}
function mn(){}
function en(){}
function tn(){}
function Fn(){}
function Mn(){}
function Tn(){}
function $n(){}
function go(){}
function no(){}
function uo(){}
function Fo(){}
function Mo(){}
function Uo(){}
function _o(){}
function _E(){}
function lp(){}
function rp(){}
function xp(){}
function DF(){}
function FF(){}
function iG(){}
function HH(){}
function JH(){}
function bI(){}
function dI(){}
function sK(){}
function WK(){}
function YK(){}
function XL(){}
function hM(){}
function $N(){}
function $Z(){}
function pZ(){}
function CO(){}
function SP(){}
function WP(){}
function WQ(){}
function jT(){}
function lT(){}
function qT(){}
function DT(){}
function l1(){}
function i2(){}
function t2(){}
function $2(){}
function t3(){}
function v3(){}
function x3(){}
function A3(){}
function C3(){}
function E3(){}
function G3(){}
function I3(){}
function K3(){}
function M3(){}
function N3(){}
function P3(){}
function R3(){}
function S3(){}
function U3(){}
function W3(){}
function X3(){}
function Z3(){}
function $3(){}
function a4(){}
function b4(){}
function d4(){}
function f4(){}
function h4(){}
function j4(){}
function l4(){}
function n4(){}
function n7(){}
function d7(){}
function f7(){}
function h7(){}
function j7(){}
function l7(){}
function p7(){}
function r7(){}
function r6(){}
function t7(){}
function u7(){}
function v7(){}
function x7(){}
function z7(){}
function B7(){}
function D7(){}
function H7(){}
function J7(){}
function L7(){}
function N7(){}
function Ufb(){}
function Vlb(){}
function cmb(){}
function kmb(){}
function smb(){}
function zqb(){}
function $rb(){}
function $sb(){}
function asb(){}
function csb(){}
function Ysb(){}
function Lbb(){}
function bdb(){}
function kdb(){}
function ldb(){}
function rdb(){}
function sdb(){}
function _nb(){}
function _qb(){}
function Pqb(){}
function Sqb(){}
function Vqb(){}
function Yqb(){}
function Bpb(){}
function Fpb(){}
function Hpb(){}
function crb(){}
function frb(){}
function irb(){}
function lrb(){}
function cF(a){}
function R2(a){}
function ti(){Gh()}
function Ni(){Gh()}
function PG(){OG()}
function AH(){yH()}
function DH(){hH()}
function bM(){VL()}
function eM(){VL()}
function oM(){nM()}
function kH(a){RF(a)}
function dcb(){Ubb()}
function Gdb(){Gdb=HE}
function dc(a,b){a.a=b}
function Zm(a,b){a.a=b}
function Vm(a,b){a.f=b}
function $m(a,b){a.b=b}
function PE(a,b){a.b=b}
function OE(a,b){a.a=b}
function VI(a,b){a.a=b}
function TR(a,b){a.a=b}
function aR(a,b){a.e=b}
function kR(a,b){a.b=b}
function UR(a,b){a.b=b}
function MQ(a,b){a.b=b}
function _Q(a,b){a.c=b}
function BO(a,b){a.c=b}
function hG(a,b){a.d=b}
function kN(a,b){a.r=b}
function SN(a,b){a.o=b}
function S_(a,b){a.j=b}
function Q_(a,b){a.g=b}
function R_(a,b){a.i=b}
function W_(a,b){a.q=b}
function X_(a,b){a.r=b}
function oR(a,b){a.k=b}
function o0(a,b){a.L=b}
function b0(a,b){a.A=b}
function s0(a,b){a.M=b}
function u0(a,b){a.O=b}
function v0(a,b){a.R=b}
function A0(a,b){a.W=b}
function B0(a,b){a.Z=b}
function C0(a,b){a.$=b}
function YW(a,b){a.G=b}
function ZW(a,b){a.H=b}
function jZ(a,b){a.e=b}
function kZ(a,b){a.f=b}
function Xsb(a,b){a.a=b}
function pe(a,b){a.Yc=b}
function wh(b,a){b.id=a}
function bb(a){this.a=a}
function jb(a){this.a=a}
function Fb(a){this.a=a}
function Lb(a){this.a=a}
function Bg(a){this.a=a}
function Dg(a){this.a=a}
function fp(a){this.a=a}
function Tp(a){this.a=a}
function TI(a){this.a=a}
function QI(a){this.a=a}
function XE(a){this.a=a}
function bF(a){this.a=a}
function JJ(a){this.a=a}
function LJ(a){this.a=a}
function uK(a){this.a=a}
function wK(a){this.a=a}
function vM(a){this.a=a}
function rO(a){this.a=a}
function zO(a){this.a=a}
function zP(a){this.a=a}
function rP(a){this.a=a}
function tP(a){this.a=a}
function xP(a){this.a=a}
function BP(a){this.a=a}
function DP(a){this.a=a}
function FP(a){this.a=a}
function JP(a){this.a=a}
function LP(a){this.a=a}
function ZP(a){this.a=a}
function vR(a){this.a=a}
function xR(a){this.a=a}
function AS(a){this.a=a}
function CS(a){this.a=a}
function ES(a){this.a=a}
function cT(a){this.a=a}
function cU(a){this.a=a}
function aU(a){this.a=a}
function eU(a){this.a=a}
function pY(a){this.a=a}
function BY(a){this.a=a}
function HY(a){this.a=a}
function JY(a){this.a=a}
function RY(a){this.a=a}
function TY(a){this.a=a}
function YY(a){this.a=a}
function $Y(a){this.a=a}
function dZ(a){this.a=a}
function TZ(a){this.a=a}
function ZZ(a){this.a=a}
function e$(a){this.a=a}
function h$(a){this.a=a}
function $0(a){this.a=a}
function NL(a){this.c=a}
function b1(a){this.b=a}
function u2(a){this.a=a}
function h3(a){this.a=a}
function j3(a){this.a=a}
function $4(a){this.a=a}
function d5(a){this.a=a}
function f5(a){this.a=a}
function j5(a){this.a=a}
function T5(a){this.a=a}
function Z5(a){this.a=a}
function _5(a){this.a=a}
function b6(a){this.a=a}
function P6(a){this.a=a}
function F7(a){this.a=a}
function Bo(){this.a={}}
function Z1(){this.a={}}
function Rab(a,b){a.a=b}
function Sab(a,b){a.b=b}
function Tab(a,b){a.c=b}
function Uab(a,b){a.e=b}
function Vab(a,b){a.f=b}
function Wab(a,b){a.g=b}
function Xab(a,b){a.i=b}
function Zab(a,b){a.j=b}
function $ab(a,b){a.k=b}
function _ab(a,b){a.n=b}
function abb(a,b){a.o=b}
function bbb(a,b){a.p=b}
function cbb(a,b){a.q=b}
function dbb(a,b){a.r=b}
function ebb(a,b){a.s=b}
function fbb(a,b){a.t=b}
function hbb(a,b){a.u=b}
function ibb(a,b){a.v=b}
function jbb(a,b){a.w=b}
function kbb(a,b){a.A=b}
function lbb(a,b){a.d=b}
function mbb(a,b){a.B=b}
function nbb(a,b){a.C=b}
function obb(a,b){a.D=b}
function pbb(a,b){a.F=b}
function qbb(a,b){a.G=b}
function rbb(a,b){a.H=b}
function sbb(a,b){a.I=b}
function tbb(a,b){a.J=b}
function ubb(a,b){a.K=b}
function vbb(a,b){a.L=b}
function wbb(a,b){a.M=b}
function xbb(a,b){a.N=b}
function ybb(a,b){a.O=b}
function zbb(a,b){a.P=b}
function Abb(a,b){a.Q=b}
function Bbb(a,b){a.R=b}
function Cbb(a,b){a.S=b}
function Dbb(a,b){a.T=b}
function Ebb(a,b){a.U=b}
function Fbb(a,b){a.V=b}
function Vbb(a,b){a.a=b}
function Wbb(a,b){a.b=b}
function Xbb(a,b){a.c=b}
function Ybb(a,b){a.d=b}
function Zbb(a,b){a.e=b}
function $bb(a,b){a.f=b}
function _bb(a,b){a.g=b}
function acb(a,b){a.i=b}
function bcb(a,b){a.j=b}
function ccb(a,b){a.k=b}
function trb(a,b){a.b=b}
function XW(a,b){a.lb=b}
function cX(a,b){a.Qb=b}
function hX(a,b){a.mb=b}
function iX(a,b){a.ec=b}
function jX(a,b){a.fc=b}
function nX(a,b){a.Fc=b}
function slb(a){this.c=a}
function Eeb(a){this.a=a}
function bkb(a){this.a=a}
function hkb(a){this.a=a}
function mkb(a){this.a=a}
function rkb(a){this.a=a}
function btb(a){this.a=a}
function gab(a){this.a=a}
function kab(a){this.a=a}
function Qbb(a){this.a=a}
function Sbb(a){this.a=a}
function Adb(a){this.a=a}
function teb(a){this.a=a}
function Qeb(a){this.a=a}
function kjb(a){this.a=a}
function Njb(a){this.d=a}
function wmb(a){this.b=a}
function Mmb(a){this.b=a}
function Mnb(a){this.a=a}
function Qnb(a){this.a=a}
function jnb(a){this.c=a}
function Opb(a){this.a=a}
function Qpb(a){this.a=a}
function Spb(a){this.a=a}
function Upb(a){this.a=a}
function jsb(a){this.a=a}
function osb(a){this.a=a}
function Lsb(a){this.a=a}
function Qsb(a){this.a=a}
function pf(){this.a=Xf()}
function Bn(){this.c=++yn}
function sj(b,a){b.src=a}
function tj(b,a){b.value=a}
function Qm(b,a){b.value=a}
function rf(a){qf=a;hg()}
function IK(){IK=HE;MK()}
function SL(){SL=HE;RL()}
function Wkb(){Lkb(this)}
function odb(){ndb(this)}
function qob(){Yib(this)}
function LH(a,b){Qe(b,a)}
function JS(a,b){Wg(b,a.k)}
function YS(a,b){Wg(b,a.B)}
function ZQ(a,b){yh(a.a,b)}
function bP(a,b){iL(a.j,b)}
function dP(a,b){bf(a.j,b)}
function b_(a,b){bP(a.u,b)}
function F_(a,b){yW(a.V,b)}
function G_(a,b){xW(a.V,b)}
function N_(a,b){XW(a.V,b)}
function O_(a,b){YW(a.V,b)}
function P_(a,b){ZW(a.V,b)}
function Y_(a,b){$W(a.V,b)}
function Z_(a,b){_W(a.V,b)}
function e0(a,b){WW(a.V,b)}
function f0(a,b){gW(a.V,b)}
function c0(a,b){bX(a.V,b)}
function d0(a,b){cX(a.V,b)}
function h0(a,b){eX(a.V,b)}
function i0(a,b){fX(a.V,b)}
function p0(a,b){hX(a.V,b)}
function q0(a,b){iX(a.V,b)}
function r0(a,b){jX(a.V,b)}
function w0(a,b){aX(a.V,b)}
function z0(a,b){nX(a.V,b)}
function D0(a,b){oX(a.V,b)}
function v$(a,b){jU(a.V,b)}
function z$(a,b){sU(a.V,b)}
function I0(a,b){GX(a.V,b)}
function J0(a,b){HX(a.V,b)}
function P0(a,b){hY(a.V,b)}
function Q0(a,b){iY(a.V,b)}
function g0(a,b){TT(a.U,b)}
function F0(a,b){UT(a.U,b)}
function n0(a,b){eP(a.u,b)}
function lR(a,b){$Q(a.g,b)}
function Zrb(a,b){a.add(b)}
function Ao(a,b,c){a.a[b]=c}
function HR(a){a.a=0;a.b=0}
function dN(a){a.s=new Wkb}
function UI(){UI=HE;new qob}
function m2(){m2=HE;new n2}
function Gh(){Gh=HE;Fh=WD()}
function JF(){JF=HE;HF=XD()}
function FG(){FG=HE;BG=YD()}
function TJ(){TJ=HE;SJ=$D()}
function nM(){nM=HE;mM=sM()}
function o3(a){p3(a);q3(a)}
function $O(a){iL(a.j,a.c)}
function mX(a,b){lS(a.yc,b)}
function W$(a,b){Gbb(a.W,b)}
function z_(a,b){Gab(a.W,b)}
function e_(a,b){Fab(a.W,b)}
function r_(a,b){Jab(a.W,b)}
function M_(a,b){Yab(a.W,b)}
function H4(a,b){a.a[Myb]=b}
function Vf(b,a){b.length=a}
function _0(){sb.call(this)}
function eR(){dR.call(this)}
function m1(){dR.call(this)}
function Ddb(){Kf.call(this)}
function Aeb(){Kf.call(this)}
function $eb(){Kf.call(this)}
function Wfb(){Kf.call(this)}
function Kpb(){Kf.call(this)}
function qgb(a){jgb(this,a)}
function ec(a){dc(this,a.id)}
function xg(a){return a.Kd()}
function Y1(a,b){return null}
function te(a,b){De(a.nd(),b)}
function re(a,b){vh(a.nd(),b)}
function qO(a,b){z_(a.a.a,b)}
function FL(a,b){HL(a,b,a.c)}
function Ph(a,b,c){a.add(b,c)}
function dJ(a,b){jJ(a,b,b,-1)}
function Hg(a){Gg();Fg.Md(a)}
function og(){og=HE;ng=new t2}
function Pf(){Pf=HE;Of=new K}
function VE(){VE=HE;UE=new _E}
function OG(){OG=HE;NG=new Bn}
function X1(){X1=HE;W1=new Bn}
function Q2(){Q2=HE;P2=new Bn}
function kF(){this.a=new Mfb}
function mdb(){this.a=new Wkb}
function vob(){this.a=new qob}
function rrb(){this.a=new qob}
function eob(){Kf.call(this)}
function Lf(a){Jf.call(this,a)}
function Mb(a){Lb.call(this,a)}
function yc(a){Lb.call(this,a)}
function of(b,a){b.fillStyle=a}
function Uf(b,a){b[b.length]=a}
function Wf(b,a){b[b.length]=a}
function Bh(b,a){b.tabIndex=a}
function Ah(b,a){b.scrollTop=a}
function fX(a,b){a.ub=b;FX(a)}
function cK(a,b){xI(a,b);ZJ(a)}
function Dj(a){(Gh(),Fh).Xd(a)}
function ai(a){Gh();return a|0}
function zo(a,b){return a.a[b]}
function TO(a){return a.g&&a.f}
function JZ(a,b){z$(rQ(a.a),b)}
function OZ(a,b){I0(rQ(a.a),b)}
function PZ(a,b){J0(rQ(a.a),b)}
function RZ(a,b){P0(rQ(a.a),b)}
function SZ(a,b){Q0(rQ(a.a),b)}
function G$(a,b,c){PU(a.V,b,c)}
function K_(a,b,c){VW(a.V,b,c)}
function N0(a,b,c){$X(a.V,b,c)}
function R0(a){eW(a.V);YT(a.U)}
function V_(a,b){a.p=b;PR(a.Q)}
function Ubb(){Ubb=HE;icb()}
function mL(){mL=HE;af();uL()}
function BE(){zE==null&&(zE=[])}
function Wp(a){Up.call(this,a)}
function _H(a){Wp.call(this,a)}
function dF(a){cF.call(this,a)}
function v4(a){Jf.call(this,a)}
function Edb(a){Lf.call(this,a)}
function zeb(a){Lf.call(this,a)}
function Beb(a){Lf.call(this,a)}
function _eb(a){Nf.call(this,a)}
function afb(a){Lf.call(this,a)}
function Xfb(a){Lf.call(this,a)}
function Hb(){Fb.call(this,yub)}
function Ib(){Fb.call(this,zub)}
function Qb(){Fb.call(this,Aub)}
function Sb(){Fb.call(this,Bub)}
function Ub(){Fb.call(this,Cub)}
function Vb(){Fb.call(this,Dub)}
function Wb(){Fb.call(this,Eub)}
function gc(){Fb.call(this,Fub)}
function sc(){Fb.call(this,Gub)}
function tc(){Fb.call(this,Hub)}
function uc(){Fb.call(this,Iub)}
function wc(){Fb.call(this,Jub)}
function xc(){Fb.call(this,Kub)}
function Ac(){Fb.call(this,Lub)}
function Fc(){Fb.call(this,Mub)}
function Yd(){Fb.call(this,Nub)}
function bg(){bg=HE;!!(Gg(),Fg)}
function oe(a,b){pe(a,(JF(),b))}
function gI(a,b){hI((JF(),a),b)}
function o_(a,b,c){Eab(a.W,c,b)}
function S$(a,b,c){Dab(a.W,b,c)}
function A_(a,b,c){Ibb(a.W,b,c)}
function A1(a,b,c){Vib(a.c,b,c)}
function __(a,b){a.v=new Ykb(b)}
function a0(a,b){a.w=new Ykb(b)}
function bH(a,b){a.__listener=b}
function MV(a,b){return b<=a.ob}
function ie(a){return JF(),a.Yc}
function ke(a){return JF(),a.Yc}
function bJ(a){return JF(),a.Yc}
function hJ(a){return JF(),a.Yc}
function eq(a,b){return aeb(a,b)}
function VV(a){Qe(a,null);Oe(a)}
function ydb(a){Lf.call(this,a)}
function Cdb(a){Lf.call(this,a)}
function Fdb(a){Edb.call(this,a)}
function Pfb(a){Edb.call(this,a)}
function Hlb(a){ptb(a);this.a=a}
function Eh(a){a=zfb(a);return a}
function ndb(a){a.a=($cb(),Ycb)}
function Ssb(a,b,c){b.Qf(Tfb(c))}
function mqb(a,b,c){b.Qf(a.a[c])}
function ctb(a,b){Bsb(a.b,a.a,b)}
function ktb(a,b){return kq(a,b)}
function VF(a,b){JF();HF.Le(a,b)}
function WF(a,b){JF();HF.Me(a,b)}
function dG(a,b){JF();HF.Me(a,b)}
function jN(a,b){_J(a,b);hN(a,1)}
function mN(a,b){dK(a,b);hN(a,1)}
function PM(a,b){a.j=b;Wg(a.d,b)}
function HI(a,b){rI(a.a,b,false)}
function zI(a){pe(this,(JF(),a))}
function ef(a){pe(this,(JF(),a))}
function kf(a){pe(this,(JF(),a))}
function T2(a){R2(this);this.a=a}
function H$(a){return a.S[a.a-1]}
function wdb(a){return Object(a)}
function cfb(a){zeb.call(this,a)}
function Ffb(){Adb.call(this,'')}
function Mfb(){Adb.call(this,'')}
function Nfb(){Adb.call(this,'')}
function YG(){Fp.call(this,null)}
function GN(){TJ();AN.call(this)}
function Cnb(a){wmb.call(this,a)}
function Gnb(a){Cnb.call(this,a)}
function Xnb(a){Qmb.call(this,a)}
function Sob(){Sob=HE;Rob=Uob()}
function o1(){o1=HE;n1=t1();u1()}
function bmb(){throw aE(new Kpb)}
function Lmb(){throw aE(new Wfb)}
function inb(){throw aE(new Wfb)}
function amb(){amb=HE;_lb=new cmb}
function rsb(){rsb=HE;qsb=new $sb}
function ysb(a){dsb(a);return a.a}
function Sdb(a){Rdb(a);return a.k}
function Tdb(a){Rdb(a);return a.i}
function iE(a,b){return dE(a,b)>0}
function kE(a,b){return dE(a,b)<0}
function q4(a,b,c){Q4(a).If(b,c)}
function jtb(a,b,c){a.splice(b,c)}
function lX(a,b,c){a.sc=c;a.rc=b}
function se(a,b,c){Be(a.nd(),b,c)}
function Npb(a,b){while(a.mg(b));}
function uO(a){!a.b&&(a.b=$F(a))}
function Db(a,b){uh(b,'role',a.a)}
function Mj(a){(Gh(),a).opacity=0}
function Ro(){this.a=Jvb in $wnd}
function Rd(){Fb.call(this,'row')}
function kc(){Fb.call(this,'log')}
function fc(){Fb.call(this,'img')}
function $d(){Fb.call(this,'tab')}
function fk(){ak.call(this,rvb,3)}
function vl(){sl.call(this,rvb,1)}
function Bm(){xm.call(this,rvb,1)}
function EJ(){FJ.call(this,false)}
function mg(){Yf!=0&&(Yf=0);ag=-1}
function vG(){this.a=new Fp(null)}
function QH(){this.o=new KL(this)}
function w2(){w2=HE;JF();Ri($doc)}
function UF(a){JF();IF=a;HF.Ke(a)}
function bG(a){JF();IF=a;HF.Ke(a)}
function fab(a){pe(this,(JF(),a))}
function Ep(a,b){return Qp(a.a,b)}
function _U(a,b){return K$(a.a,b)}
function sZ(a,b){return z1(a.F,b)}
function gE(a,b){return dE(a,b)==0}
function lE(a,b){return dE(a,b)<=0}
function Mq(a){return a.l|a.m<<22}
function iF(b,a){return b.test(a)}
function V6(a,b){b<0&&(b=0);a.a=b}
function W6(a,b){b<0&&(b=0);a.d=b}
function Oj(a,b){this.b=a;this.c=b}
function Bb(a,b){this.b=a;this.a=b}
function Efb(a,b){a.a+=b;return a}
function Hfb(a,b){a.a+=b;return a}
function Wpb(a,b){while(a.pg(b));}
function ue(a,b){Ee((JF(),a.Yc),b)}
function we(a,b){VF((JF(),a.Yc),b)}
function df(a,b){Bh((JF(),a.Yc),b)}
function $b(){Fb.call(this,'form')}
function _b(){Fb.call(this,'grid')}
function lc(){Fb.call(this,'main')}
function nc(){Fb.call(this,'math')}
function oc(){Fb.call(this,'menu')}
function hc(){Fb.call(this,'list')}
function vc(){Fb.call(this,'note')}
function fe(){Fb.call(this,'tree')}
function vh(b,a){b.className=a||''}
function xh(b,a){b.innerHTML=a||''}
function Hj(b,a){b.selectedIndex=a}
function ak(a,b){Oj.call(this,a,b)}
function Bk(a,b){Oj.call(this,a,b)}
function sl(a,b){Oj.call(this,a,b)}
function Dl(a,b){Oj.call(this,a,b)}
function Ol(a,b){Oj.call(this,a,b)}
function Xl(a,b){Oj.call(this,a,b)}
function jm(a,b){Oj.call(this,a,b)}
function lm(){jm.call(this,'PX',0)}
function om(){jm.call(this,'EX',3)}
function nm(){jm.call(this,'EM',2)}
function pm(){jm.call(this,'PT',4)}
function qm(){jm.call(this,'PC',5)}
function rm(){jm.call(this,'IN',6)}
function sm(){jm.call(this,'CM',7)}
function tm(){jm.call(this,'MM',8)}
function xm(a,b){Oj.call(this,a,b)}
function Jm(a,b){Oj.call(this,a,b)}
function cq(a,b){Oj.call(this,a,b)}
function _I(a,b){this.a=a;this.b=b}
function fh(a,b){(Gh(),Fh).Rd(a,b)}
function yh(a,b){(Gh(),Fh).fe(a,b)}
function zh(a,b){(Gh(),Fh).ge(a,b)}
function lJ(a,b){Hj((JF(),a.Yc),b)}
function vL(a,b){Oj.call(this,a,b)}
function iO(a,b){gO.call(this,a,b)}
function xO(a,b){this.a=a;this.b=b}
function YN(a,b){this.a=a;this.b=b}
function NP(a,b){this.a=a;this.b=b}
function XQ(a,b){this.b=a;this.a=b}
function VR(a,b){this.c=b;this.d=a}
function zR(a,b){gO.call(this,a,b)}
function vY(a,b){this.a=a;this.b=b}
function xY(a,b){this.a=a;this.b=b}
function zY(a,b){this.a=a;this.b=b}
function DY(a,b){this.a=a;this.b=b}
function FY(a,b){this.a=a;this.b=b}
function aZ(a,b){this.a=a;this.b=b}
function hZ(a,b){this.b=a;this.a=b}
function oX(a,b){a.Tc=b;kS(a.yc,b)}
function Qp(a,b){return Pib(a.d,b)}
function Xg(a){return Kh((Gh(),a))}
function mh(a){return Jh((Gh(),a))}
function _F(a){return $G((JF(),a))}
function sg(a){return !!a.b||!!a.g}
function z1(a,b){return Sib(a.c,b)}
function AM(a){Rp(a.a,a.d,a.c,a.b)}
function KN(a){fN(a,false);bh(a.i)}
function KZ(a,b,c){G$(rQ(a.a),b,c)}
function VZ(a,b){this.a=a;this.b=b}
function a$(a,b){this.a=a;this.b=b}
function f$(a,b){this.a=a;this.b=b}
function f1(a,b){this.a=a;this.b=b}
function s3(a,b){this.a=a;this.b=b}
function h5(a,b){this.a=a;this.b=b}
function R6(a,b){this.a=a;this.b=b}
function T6(a,b){this.a=a;this.b=b}
function h6(a,b){this.b=a;this.a=b}
function r4(a,b){this.b=a;this.a=b}
function dtb(a,b){this.b=a;this.a=b}
function Bkb(a,b){this.a=a;this.b=b}
function Vsb(a,b){this.a=a;this.b=b}
function Ceb(a,b){Mf.call(this,a,b)}
function Wrb(a,b){Oj.call(this,a,b)}
function Gb(){Fb.call(this,'alert')}
function ce(){Fb.call(this,'timer')}
function bc(){Fb.call(this,'group')}
function Ec(){Fb.call(this,'radio')}
function Kj(a){return (Gh(),a)[ovb]}
function Ij(a){return (Gh(),a)[nvb]}
function Lj(a){return (Gh(),a)[pvb]}
function Jj(a){return (Gh(),a)[Qub]}
function Cj(a){return (Gh(),a).type}
function Zib(a){return a.a.c+a.b.c}
function rG(a){qG();return uG(oG,a)}
function ub(a){$wnd.clearTimeout(a)}
function lg(a){$wnd.clearTimeout(a)}
function jL(a){af();ef.call(this,a)}
function mm(){jm.call(this,'PCT',1)}
function Nm(){Jm.call(this,'PRE',2)}
function ME(){XF();VE();WE();new x1}
function Znb(){Znb=HE;Ynb=new _nb}
function yH(){yH=HE;hH();eH[Yvb]=lH}
function KG(){if(!EG){GH();EG=true}}
function JG(){if(!zG){FH();zG=true}}
function zU(a){EU(a);AU(a);a.v=true}
function p$(a){V5(a.e);AJ(a.e,null)}
function n_(a,b){RV(a.V)||TX(a.V,b)}
function Ke(a,b){!!a.Wc&&Dp(a.Wc,b)}
function K0(a,b,c,d){Kbb(a.W,b,c,d)}
function T4(a,b,c,d){a[b][c].type=d}
function Mbb(a,b,c){a[b.a]=wdb(c.a)}
function Nbb(a,b,c){a[b.a]=wdb(c.a)}
function htb(a,b,c){a.splice(b,0,c)}
function jcb(a,b){Oj.call(this,a,b)}
function Fcb(a,b){Oj.call(this,a,b)}
function Ocb(a,b){Oj.call(this,a,b)}
function Wcb(a,b){Oj.call(this,a,b)}
function _cb(a,b){Oj.call(this,a,b)}
function l5(){Q2();T2.call(this,{})}
function iab(a,b){this.a=a;this.b=b}
function Gsb(a,b){this.a=a;this.b=b}
function Yob(a,b){return a.a.get(b)}
function vfb(a,b){return a.substr(b)}
function tob(a,b){return Pib(a.a,b)}
function J(a,b){return dr(a)===dr(b)}
function $q(a){return typeof a===Ltb}
function _q(a){return typeof a===Mtb}
function jE(a){return typeof a===Mtb}
function cr(a){return typeof a===Ntb}
function dr(a){return a==null?null:a}
function p4(a){return a.b.a+'.'+a.a}
function tb(a){$wnd.clearInterval(a)}
function gqb(a,b){Ypb.call(this,a,b)}
function Pb(){Fb.call(this,'banner')}
function Xd(){Fb.call(this,'slider')}
function Vd(){Fb.call(this,'search')}
function Zd(){Fb.call(this,'status')}
function Xb(){Fb.call(this,'dialog')}
function Gc(){Fb.call(this,'region')}
function ck(){ak.call(this,'NONE',0)}
function Dk(){Bk.call(this,'NONE',0)}
function Zl(){Xl.call(this,'CLIP',0)}
function xl(){sl.call(this,'AUTO',3)}
function Sl(){Ol.call(this,'LEFT',2)}
function Fp(a){Gp.call(this,a,false)}
function PJ(a,b){QJ.call(this,a.a,b)}
function zL(){vL.call(this,'LEFT',2)}
function CK(a){V.call(this);this.a=a}
function Yg(a){return !!Kh((Gh(),a))}
function Kb(a,b,c){uh(b,a.a,Jb(a,c))}
function $X(a,b,c){JT(Sib(a.Bc,b),c)}
function TH(a,b){OH(a,b,(JF(),a.Yc))}
function tI(a,b){OH(a,b,(JF(),a.Yc))}
function bf(a,b){(JF(),a.Yc)[Xub]=!b}
function cG(a,b){JF();a.__listener=b}
function aqb(a,b){Ypb.call(this,a,b)}
function dqb(a,b){Ypb.call(this,a,b)}
function Y6(a){this.a=a;V.call(this)}
function T1(a){this.a=U1(a);this.b=a}
function mH(a){(Gh(),Fh).Xd(a);nH(a)}
function Idb(a){Gdb();return ptb(a),a}
function Wob(){Sob();return new Rob}
function lfb(a,b){return a.indexOf(b)}
function Mpb(a){return a!=null?Q(a):0}
function Cib(a){return !a?null:a.hg()}
function zb(a){this.a=a;sb.call(this)}
function Ob(){Fb.call(this,'article')}
function mc(){Fb.call(this,'marquee')}
function qc(){Fb.call(this,'menubar')}
function de(){Fb.call(this,'toolbar')}
function ee(){Fb.call(this,'tooltip')}
function _d(){Fb.call(this,'tablist')}
function cc(){Fb.call(this,'heading')}
function ic(){Fb.call(this,'listbox')}
function be(){Fb.call(this,'textbox')}
function nh(a){return (Gh(),Fh).be(a)}
function qh(a){return (Gh(),Fh).ce(a)}
function rh(a){return (Gh(),Fh).he(a)}
function hh(a){return (Gh(),Fh).Yd(a)}
function jh(a){return (Gh(),Fh).Zd(a)}
function jj(a){return (Gh(),Fh)._d(a)}
function ij(a){return (Gh(),Fh).$d(a)}
function uj(a){return (Gh(),Fh).Sd(a)}
function wj(a){return (Gh(),Fh).Td(a)}
function xj(a){return (Gh(),Fh).Ud(a)}
function yj(a){return (Gh(),Fh).Wd(a)}
function oj(a){return Lh((Gh(),Fh),a)}
function pj(a){return Mh((Gh(),Fh),a)}
function af(){af=HE;_e=(VL(),VL(),UL)}
function gk(){ak.call(this,'SOLID',4)}
function Vk(){Bk.call(this,'FLEX',17)}
function Zk(){Bk.call(this,'BLOCK',1)}
function jl(){Bk.call(this,'TABLE',7)}
function Il(){Dl.call(this,'FIXED',3)}
function Tl(){Ol.call(this,'RIGHT',3)}
function Tm(a){return (Gh(),a).target}
function eL(a){return xM((JF(),a.Yc))}
function fL(a){return yM((JF(),a.Yc))}
function WL(a){return (Gh(),Fh).de(a)}
function nq(a){return oq(a.l,a.m,a.h)}
function MF(a){JF();return HF.Fe(a,0)}
function Ifb(a,b){a.a+=''+b;return a}
function Jfb(a,b){a.a+=''+b;return a}
function Kfb(a,b){a.a+=''+b;return a}
function r$(a,b,c,d){a.a=b;q$(a,c,d)}
function jR(a,b){Ukb(a.f,b);CL(a.i,b)}
function LM(a,b){a.o=b;SM(a);a.g=true}
function L$(a,b,c){return Y0(a.I,b,c)}
function M$(a,b,c){return Z0(a.I,b,c)}
function f_(a,b,c){!!a.R&&XZ(a.R,b,c)}
function QV(a){return EV(a,a.rc,a.sc)}
function GK(a){this.a=a;sb.call(this)}
function vP(a){this.a=a;sb.call(this)}
function HP(a){this.a=a;sb.call(this)}
function yS(a){this.a=a;sb.call(this)}
function LY(a){this.a=a;sb.call(this)}
function PY(a){this.a=a;sb.call(this)}
function h1(a){this.a=a;sb.call(this)}
function a5(a){this.a=a;sb.call(this)}
function i6(a){this.a=a;sb.call(this)}
function p6(a){this.a=a;sb.call(this)}
function K6(a){this.a=a;sb.call(this)}
function Job(a){this.a=Wob();this.b=a}
function _ob(a){this.a=Wob();this.b=a}
function j_(a){y$(a,a.b,true);SU(a.V)}
function FI(){FI=HE;EI=(VL(),VL(),TL)}
function AL(){vL.call(this,'RIGHT',3)}
function Rb(){Fb.call(this,'checkbox')}
function Tb(){Fb.call(this,'combobox')}
function Zb(){Fb.call(this,'document')}
function ac(){Fb.call(this,'gridcell')}
function ae(){Fb.call(this,'tabpanel')}
function he(){Fb.call(this,'treeitem')}
function ge(){Fb.call(this,'treegrid')}
function Sd(){Fb.call(this,'rowgroup')}
function jc(){Fb.call(this,'listitem')}
function rc(){Fb.call(this,'menuitem')}
function dk(){ak.call(this,'DOTTED',1)}
function ek(){ak.call(this,'DASHED',2)}
function _k(){Bk.call(this,'INLINE',2)}
function hl(){Bk.call(this,'RUN_IN',6)}
function Fl(){Dl.call(this,'STATIC',0)}
function Ql(){Ol.call(this,'CENTER',0)}
function wl(){sl.call(this,'SCROLL',2)}
function Lm(){Jm.call(this,'NORMAL',0)}
function Mm(){Jm.call(this,'NOWRAP',1)}
function sf(a){a.i=gq(MB,Jtb,74,0,0,1)}
function Lkb(a){a.a=gq(KB,Jtb,1,0,5,1)}
function wg(a,b){a.d=yg(a.d,[b,false])}
function s_(a,b,c){!!a.R&&YZ(a.R,b,c)}
function Si(a,b){return Hh((Gh(),a),b)}
function Bj(a){return (Gh(),a).touches}
function Gj(a){return (Gh(),a).options}
function HO(a){s2((og(),ng),new LP(a))}
function KO(a){s2((og(),ng),new DP(a))}
function oP(a){s2((og(),ng),new xP(a))}
function OT(a){s2((og(),ng),new eU(a))}
function SU(a){s2((og(),ng),new BY(a))}
function C_(a){nW(a.V);pW(a.V);qW(a.V)}
function oW(a){xU(a);LX(a);OX(a);QU(a)}
function DN(a,b){TJ();Qd();Eb(CN(a),b)}
function _2(a,b){a.d=1;r3(new s3(a,b))}
function uh(c,a,b){c.setAttribute(a,b)}
function PP(a,b,c){UM.call(this,a,b,c)}
function ehb(a){Igb();fhb.call(this,a)}
function Sp(a){this.d=new qob;this.c=a}
function w1(){w1=HE;new opb;v1=new Wkb}
function _G(a){if(!ZG){a.He();ZG=true}}
function Krb(a,b){if(xrb){return}a.b=b}
function isb(a,b){return a[a.length]=b}
function nsb(a,b){return a[a.length]=b}
function plb(a){return a.a<a.c.a.length}
function kob(a){return a<10?'0'+a:''+a}
function Feb(a,b){return a<b?-1:a>b?1:0}
function atb(a,b,c){return Asb(a.a,b,c)}
function QZ(a,b,c,d){L0(rQ(a.a),b,c,d)}
function fI(a,b){(JF(),a)['align']=b.a}
function aE(a){return a.backingJsObject}
function xL(){vL.call(this,'CENTER',0)}
function ul(){sl.call(this,'VISIBLE',0)}
function zm(){xm.call(this,'VISIBLE',0)}
function Rl(){Ol.call(this,'JUSTIFY',1)}
function Yb(){Fb.call(this,'directory')}
function Wd(){Fb.call(this,'separator')}
function Td(){Fb.call(this,'rowheader')}
function Ud(){Fb.call(this,'scrollbar')}
function yL(){vL.call(this,'JUSTIFY',1)}
function NF(a){JF();return Jh((Gh(),a))}
function OF(a){JF();return Kh((Gh(),a))}
function O$(a,b){return tob(a.G,Neb(b))}
function R$(a,b){return tob(a.H,Neb(b))}
function Zq(a,b){return a!=null&&Xq(a,b)}
function Wg(b,a){return b.appendChild(a)}
function ah(b,a){return b.removeChild(a)}
function oh(b,a){return parseInt(b[a])|0}
function oq(a,b,c){return {l:a,m:b,h:c}}
function oV(a){return Ewb+a.rc+Fwb+a.sc}
function Tfb(a){return a.backingJsObject}
function zj(a){return (Gh(),a).keyCode|0}
function Fj(a){(Gh(),a).options.length=0}
function RM(a){IM(a);KM(a);JM(a);a.hf()}
function M4(a,b,c){a.f[(new A4(b)).a]=c}
function y4(a,b){x4.call(this,null,a,b)}
function Tk(){Bk.call(this,'INITIAL',16)}
function Gl(){Dl.call(this,'RELATIVE',1)}
function Hl(){Dl.call(this,'ABSOLUTE',2)}
function $l(){Xl.call(this,'ELLIPSIS',1)}
function Om(){Jm.call(this,'PRE_LINE',3)}
function Pm(){Jm.call(this,'PRE_WRAP',4)}
function wm(){wm=HE;vm=new zm;um=new Bm}
function Wl(){Wl=HE;Ul=new Zl;Vl=new $l}
function ZH(){ZH=HE;XH=new bI;YH=new dI}
function IM(a){if(a.a){bh(a.a);a.a=null}}
function JM(a){if(a.e){bh(a.e);a.e=null}}
function KM(a){if(a.j){bh(a.j);a.j=null}}
function ZO(a){iL(a.a,a.b);cf(a.a,false)}
function LO(a){iL(a.j,'');fP(a,'');NO(a)}
function QS(a){bh(a.B);dG(a.B,-15736909)}
function fP(a,b){a.b=b;iL(a.a,b);lP(a,b)}
function jP(a){a.v=false;a.u=false;iP(a)}
function PT(a){return QT(a,a.u.length-1)}
function Hcb(a){return !!a&&!a.isEmpty()}
function w$(a){return (!a.T||!a.D)&&!a.Z}
function x$(a){return (!a.T||!a.F)&&!a.Z}
function d$(a,b){return Tib(dQ(a.a).c,b)}
function hF(c,a,b){return a.replace(c,b)}
function mfb(a,b,c){return a.indexOf(b,c)}
function ofb(a,b){return a.lastIndexOf(b)}
function nfb(a){return jfb(Ntb,typeof(a))}
function reb(a){return jfb(Mtb,typeof(a))}
function xtb(a){return a.$H||(a.$H=++wtb)}
function Mrb(a){if(xrb){return}a.e=false}
function Ldb(a){Gdb();return a?true:false}
function ogb(a){fgb();pgb.call(this,a,0)}
function Kf(){sf(this);uf(this);this.Id()}
function Yrb(a,b){this.a=a;Rlb();this.b=b}
function kX(a,b,c){zh(a.zc,b);Ah(a.zc,c)}
function PF(a,b,c){JF();HF.Ie(a,TF(b),c)}
function qe(a,b){(JF(),a.Yc).style[Qub]=b}
function ve(a,b){(JF(),a.Yc).style[Rub]=b}
function je(a){return oh((JF(),a.Yc),Oub)}
function Aj(a){return !!(Gh(),a).shiftKey}
function $g(a,b){return (Gh(),Fh).ee(a,b)}
function ntb(a){if(!a){throw aE(new Kpb)}}
function ttb(a){if(!a){throw aE(new Aeb)}}
function to(){to=HE;so=new Cn(Gvb,new uo)}
function fo(){fo=HE;eo=new Cn(lvb,new go)}
function Eo(){Eo=HE;Do=new Cn(Ivb,new Fo)}
function $o(){$o=HE;Zo=new Cn(Kvb,new _o)}
function sn(){sn=HE;rn=new Cn(mvb,new tn)}
function Btb(){Btb=HE;ytb=new K;Atb=new K}
function fl(){Bk.call(this,'LIST_ITEM',5)}
function NQ(){LQ.call(this);this.a=new WQ}
function oL(){mL();pL.call(this,fj($doc))}
function aL(a){this.c=a;this.a=!!this.c.O}
function Ej(a){(Gh(),a).stopPropagation()}
function IN(a){(JF(),a.Yc).style[pvb]='1'}
function MN(a){(JF(),a.Yc).style[pvb]='0'}
function LS(a){(JF(),a.Yc).style[pvb]='2'}
function _S(a){(JF(),a.Yc).style[pvb]='2'}
function kJ(a){(JF(),a.Yc).multiple=false}
function xq(a){return a.l+a.m*Rtb+a.h*Qtb}
function qU(a,b){return !!a.r&&Tib(a.r,b)}
function U_(a,b){Yib(a.n);!!b&&vib(a.n,b)}
function Ofb(a){Adb.call(this,(ptb(a),a))}
function Qmb(a){wmb.call(this,a);this.a=a}
function cnb(a){Mmb.call(this,a);this.a=a}
function W(a){this.j=new bb(this);this.s=a}
function Gp(a,b){this.a=new Sp(b);this.b=a}
function Hh(a,b){return a.createElement(b)}
function wfb(a,b,c){return a.substr(b,c-b)}
function uob(a,b){return Wib(a.a,b)!=null}
function WJ(a){return oh((JF(),a.Yc),Oub)}
function VJ(a){return oh((JF(),a.Yc),qwb)}
function Nj(a){return a.b!=null?a.b:''+a.c}
function KV(a,b,c){return b<=a.ob&&c<=a.Tc}
function Kg(a){Gg();return parseInt(a)||-1}
function vJ(a){if(zJ(a)){return}a.i&&BJ(a)}
function sO(a){if(a.b){AM(a.b.a);a.b=null}}
function gR(a){if(a.c){jR(a,a.c);a.c=null}}
function BW(a){u_(a.a,a.db,a.zb,a.bb,a.xb)}
function hR(a,b){b?eh(a.j,gxb):th(a.j,gxb)}
function y0(a,b){vT(a.V.Ec);!!b&&lU(a.V,b)}
function M0(a,b){s2((og(),ng),new f1(a,b))}
function JO(a,b){s2((og(),ng),new NP(a,b))}
function cob(a,b){b.$modCount=a.$modCount}
function Deb(a,b){return Zq(b,91)&&b.a==a.a}
function gj(b,a){return b.createTextNode(a)}
function mj(b,a){return b.getElementById(a)}
function JK(b,a){IK();b.__gwt_resolve=KK(a)}
function Rdb(a){if(a.k!=null){return}eeb(a)}
function xJ(a){if(zJ(a)){return}!a.i&&BJ(a)}
function RO(a){return !a.v||a.v&&!a.u&&!a.f}
function a2(b,a){return b.hasOwnProperty(a)}
function x6(a){return !a.D&&(a.D=qQ(a)),a.D}
function w_(a,b,c){Pab(a.W,c,b);qb(a.s,200)}
function bW(a,b){a.Gb=b;a.Hb=kY(b);g6(a.Ib)}
function Drb(a,b){if(xrb){return}Nkb(a.a,b)}
function hsb(a,b){fsb.call(this,a);this.a=b}
function msb(a,b){fsb.call(this,a);this.a=b}
function Asb(a,b,c){rsb();Zrb(b,c);return b}
function jF(a){Kfb(a.a,wF('Fill'));return a}
function D_(a){a.d?(a.d=false):rW(a.V,true)}
function gob(a){this.a=new $wnd.Date(vE(a))}
function yb(a,b){return $wnd.setTimeout(a,b)}
function Isb(a,b){return a.a.mg(new Lsb(b))}
function Nsb(a,b){return a.a.mg(new Qsb(b))}
function cg(a,b,c){return a.apply(b,c);var d}
function br(a,b){return a&&b&&a instanceof b}
function Jdb(a,b){Gdb();return a==b?0:a?1:-1}
function yf(a,b){a.backingJsObject=b;vf(a,b)}
function vg(a,b){a.b=yg(a.b,[b,false]);tg(a)}
function ZF(a){return JF(),$G((Gh(),a).type)}
function nJ(a,b){return tJ(a,b,a.b.a.length)}
function Rk(){Bk.call(this,'TABLE_ROW',15)}
function Nk(){Bk.call(this,'TABLE_CELL',13)}
function Xk(){Bk.call(this,'INLINE_FLEX',18)}
function bl(){Bk.call(this,'INLINE_BLOCK',3)}
function dl(){Bk.call(this,'INLINE_TABLE',4)}
function yI(){zI.call(this,(JF(),Ri($doc)))}
function $K(){RK.call(this,(QK(),$doc.body))}
function dQ(a){!a.L&&(a.L=a.sf());return a.L}
function rQ(a){!a.D&&(a.D=a.xf());return a.D}
function pS(a){a.k=0;a.n=0;pb(a.M);a.N=false}
function nS(a){if(!a.N){a.N=true;rb(a.M,50)}}
function O5(a,b){eh(b,Pzb);a.b&&sob(a.a.p,b)}
function Pob(a,b){var c;c=a[DAb];c.call(a,b)}
function Qob(a,b){var c;c=a[DAb];c.call(a,b)}
function qeb(a,b){return ptb(a),dr(a)===dr(b)}
function jfb(a,b){return ptb(a),dr(a)===dr(b)}
function lkb(a,b){return a.a.containsValue(b)}
function xb(a,b){return $wnd.setInterval(a,b)}
function pfb(a,b,c){return a.lastIndexOf(b,c)}
function Zg(c,a,b){return c.insertBefore(a,b)}
function Ih(a,b){return a.getAttribute(b)||''}
function nN(a,b){(JF(),a.Yc).style[pvb]=b+''}
function Bsb(a,b,c){rsb();Xsb(a,atb(b,a.a,c))}
function fW(a){return jfb(a,Cwb)||jfb(a,Dwb)}
function Rm(a){return ai((Gh(),a).clientX||0)}
function Sm(a){return ai((Gh(),a).clientY||0)}
function vj(a){return (Gh(),a).changedTouches}
function wT(a){return String.fromCharCode(a)}
function np(a){var b;if(kp){b=new lp;a.ud(b)}}
function tp(a){var b;if(qp){b=new rp;Dp(a,b)}}
function qG(){qG=HE;new xG;oG=new vG;pG=sG()}
function x1(){w1();new qob;new qob;new qob}
function Zn(){Zn=HE;Yn=new Cn('keyup',new $n)}
function dn(){dn=HE;cn=new Cn('blur',new en)}
function ln(){ln=HE;kn=new Cn('click',new mn)}
function En(){En=HE;Dn=new Cn('focus',new Fn)}
function Cp(a,b,c){return new Tp(Jp(a.a,b,c))}
function VY(a,b,c){this.a=a;this.b=b;this.c=c}
function d1(a,b,c){this.a=a;this.b=b;this.c=c}
function j1(a,b,c){this.a=a;this.c=b;this.b=c}
function B4(a,b){this.a=a;this.b=C4(this.a,b)}
function GX(a,b){JX(a,a.db,a.zb,1,a.ob,a.d,b)}
function mR(a,b){b!=null?jN(a.e,b):jN(a.e,'')}
function nR(a,b){b!=null?mN(a.e,b):mN(a.e,'')}
function Lrb(a,b){if(xrb){return}!!b&&(a.d=b)}
function ltb(a,b){if(!a){throw aE(new zeb(b))}}
function tcb(a,b){a.b=b;a.c=0;a.d=a.b+'.'+a.c}
function HS(a,b,c,d,e){a.i=b;a.f=c;a.g=d;a.e=e}
function VS(a,b,c,d,e){a.t=b;a.r=c;a.s=d;a.q=e}
function K4(a,b,c,d){T4(a.c,(Rdb(b),b.k),c,d)}
function XU(a,b,c){return Sib(a.e,Ewb+b+Fwb+c)}
function iY(a,b){JX(a,1,a.Tc,a.bb,a.xb,a.Rc,b)}
function iL(a,b){(JF(),a.Yc)[xwb]=b!=null?b:''}
function V5(a){(VL(),VL(),TL).bf((JF(),a.Yc))}
function A4(a){B4.call(this,(Rdb(a),a.k),null)}
function Pk(){Bk.call(this,'TABLE_COLUMN',14)}
function ll(){Bk.call(this,'TABLE_CAPTION',8)}
function hdb(a,b,c){Oj.call(this,a,b);this.a=c}
function mab(a,b,c){this.a=a;this.b=b;this.c=c}
function gpb(a,b,c){this.a=a;this.b=b;this.c=c}
function tpb(a,b,c){this.d=a;this.b=c;this.a=b}
function rqb(a){this.b=(ptb(a),a);this.a=16464}
function AX(a){a.X=0;a.Y=0;pb(a.mc);a.nc=false}
function aW(a){!a.o&&a.k!=-1&&a.n!=-1&&g6(a.p)}
function gb(a){$wnd.cancelAnimationFrame(a.id)}
function udb(a){if(a==null){return 0}return +a}
function Nkb(a,b){a.a[a.a.length]=b;return true}
function hI(a,b){a.style['verticalAlign']=b.a}
function qE(a,b){return eE(Hq(jE(a)?uE(a):a,b))}
function rE(a,b){return eE(Iq(jE(a)?uE(a):a,b))}
function sE(a,b){return eE(Jq(jE(a)?uE(a):a,b))}
function Csb(a){return rsb(),gq(KB,Jtb,1,a,5,1)}
function Peb(){Peb=HE;Oeb=gq(EB,Jtb,91,256,0,1)}
function Epb(){Epb=HE;Cpb=new Fpb;Dpb=new Hpb}
function Ln(){Ln=HE;Kn=new Cn('keydown',new Mn)}
function nb(){this.a=new Wkb;this.b=new zb(this)}
function rgb(a,b){this.e=b;lgb(this,(ptb(a),a))}
function vb(a,b){return Ftb(function(){a.kd(b)})}
function Hdb(a){Gdb();return jfb(Ltb,typeof(a))}
function wE(a){if(jE(a)){return a|0}return Mq(a)}
function wX(a){if(!a.nc){a.nc=true;rb(a.mc,50)}}
function zp(a){var b;if(wp){b=new xp;Dp(a.a,b)}}
function Ip(a,b){!a.a&&(a.a=new Wkb);Nkb(a.a,b)}
function C$(a,b){HR(a.Q);LO(a.u);B$(a);wU(a.V,b)}
function dW(a){g6(a.lc);YV(a);YX(a);nW(a);qW(a)}
function NO(a){a.k=-1;a.n=-1;a.o=-1;a.p=-1;MO(a)}
function RW(a){NW(a,a.yc.e,a.yc.f,a.yc.K,a.yc.L)}
function K1(a){if(!a.a.s){return -1}return a.a.a}
function g6(a){!a.c&&(a.c=new i6(a));qb(a.c,a.b)}
function Kp(a,b,c,d){var e;e=Np(a,b,c);e.add(d)}
function itb(a,b,c){gtb(c,0,a,b,c.length,false)}
function rU(a,b){return !!a.tb&&a.tb.contains(b)}
function g2(a,b){return a[0]!==b[0]||a[2]!==b[2]}
function h2(a,b){return a[1]!==b[1]||a[3]!==b[3]}
function iq(a){return Array.isArray(a)&&a.sg===LE}
function xE(a){if(jE(a)){return ''+a}return Nq(a)}
function Sn(){Sn=HE;Rn=new Cn('keypress',new Tn)}
function Lo(){Lo=HE;Ko=new Cn('touchend',new Mo)}
function Zeb(){Zeb=HE;Yeb=gq(GB,Jtb,104,256,0,1)}
function mo(){mo=HE;lo=new Cn('mousedown',new no)}
function To(){To=HE;So=new Cn('touchmove',new Uo)}
function Vi(a){return (Gh(),a).createElement(Fub)}
function Yi(a){return (Gh(),a).createElement(Jub)}
function lh(b,a){return b.getElementsByTagName(a)}
function Clb(a,b){mtb(b,a.length);Alb(a,0,b,null)}
function Qkb(a,b){otb(b,a.a.length);return a.a[b]}
function zsb(a,b){rsb();fsb.call(this,a);this.a=b}
function U2(a,b){Q2();R2(this);this.c=a;this.b=b}
function xcb(a,b){this.a=a;this.b=b;this.c='poll'}
function dR(){this.rb=new qob;this.hb=(Ncb(),Lcb)}
function GO(a){var b;a.c=(b=gL(a.j),b==null?'':b)}
function yW(a,b){var c;c=Xib(a.Bc,b);!!c&&sW(a,c)}
function Zdb(a,b){var c;c=Wdb(a,b);c.f=2;return c}
function $nb(a,b){return ptb(a),Kdb(a,(ptb(b),b))}
function prb(a,b){Vib(a.a,(Arb(),xrb)?null:b.c,b)}
function HX(a,b){JX(a,a.db,a.zb,a.bb,a.xb,a.kc,b)}
function Mkb(a,b,c){rtb(b,a.a.length);htb(a.a,b,c)}
function yg(a,b){!a&&(a=[]);a[a.length]=b;return a}
function pQ(a){J2(ie(a.zf()),true);!!a.r&&pb(a.r)}
function cj(a){return (Gh(),a).createElement('tr')}
function bj(a){return (Gh(),a).createElement('td')}
function Icb(a){return a.kb==null||a.kb.length==0}
function Jcb(a){return a.ob==null||a.ob.length==0}
function Yq(a){return !Array.isArray(a)&&a.sg===LE}
function NE(a){if(a.b){return a.b}return Kqb(),Bqb}
function hg(){bg();if(Zf){return}Zf=true;ig(false)}
function OJ(a){se(a,ye((JF(),a.Yc))+'-'+owb,false)}
function NV(a){return !jfb((Ak(),Pub),Ij(a.style))}
function N$(a,b){return a.M.length>=b?a.M[b-1]:a.r}
function HV(a,b,c){return c>a.Tc&&c<=a.zb&&b<=a.ob}
function LV(a,b,c){return b>a.ob&&b<=a.xb&&c<=a.Tc}
function qR(a,b,c){a.n=c;a.d=b;kN(a.e,b);aR(a.g,b)}
function I_(a,b,c,d,e,f,g,h){RR(a.Q,b,c,d,e,f,g,h)}
function KL(a){this.b=a;this.a=gq(lw,Jtb,13,4,0,1)}
function Lk(){Bk.call(this,'TABLE_ROW_GROUP',12)}
function Fk(){Bk.call(this,'TABLE_COLUMN_GROUP',9)}
function fF(a){dF.call(this,new cF(null));this.a=a}
function XI(a){UI();WI.call(this,(BF(),new xF(a)))}
function SK(a){QK();try{a.yd()}finally{uob(PK,a)}}
function P1(){F1();return $wnd.navigator.userAgent}
function Xob(a,b){return !(a.a.get(b)===undefined)}
function Wib(a,b){return cr(b)?Xib(a,b):Iob(a.a,b)}
function n5(a,b){j2(b,ie(x6(a.c)));cQ(a.a,fB).tg()}
function Jf(a){sf(this);this.f=a;uf(this);this.Id()}
function Ykb(a){Lkb(this);itb(this.a,0,a.toArray())}
function Ypb(a,b){this.d=a;this.c=(b&64)!=0?b|Ztb:b}
function qtb(a,b){if(a==null){throw aE(new afb(b))}}
function $D(){if(VD==2){return new hM}return new oM}
function ZD(){if(VD==2){return new eM}return new bM}
function YD(){if(VD==2){return new HH}return new JH}
function XD(){if(VD==2){return new DH}return new AH}
function WD(){if(VD==2){return new Ni}return new ti}
function a3(a){var b;a.d=2;return b=a.a,a.a=null,b}
function Aq(a,b){return oq(a.l&b.l,a.m&b.m,a.h&b.h)}
function Gq(a,b){return oq(a.l|b.l,a.m|b.m,a.h|b.h)}
function Oq(a,b){return oq(a.l^b.l,a.m^b.m,a.h^b.h)}
function Ri(a){return (Gh(),a).createElement('div')}
function Ui(a){return (Gh(),a).createElement('img')}
function N2(){N2=HE;M2=Qrb('spreadsheet RpcProxy')}
function QK(){QK=HE;NK=new WK;OK=new qob;PK=new vob}
function VL(){VL=HE;TL=ZD();UL=Zq(TL,149)?new XL:TL}
function uI(){QH.call(this);oe(this,Si($doc,'div'))}
function hK(){gK.call(this);this.u=true;this.v=true}
function Hk(){Bk.call(this,'TABLE_HEADER_GROUP',10)}
function Jk(){Bk.call(this,'TABLE_FOOTER_GROUP',11)}
function YE(a){a.a=Qrb('');Mrb(a.a);$E(a.a);ZE(a.a)}
function pW(a){a.k!=-1&&a.n!=-1&&a.j!=null&&NN(a.q)}
function SF(a){JF();!!IF&&a==IF&&(IF=null);HF.Je(a)}
function aG(a){JF();!!IF&&a==IF&&(IF=null);HF.Je(a)}
function yJ(a){if(zJ(a)){return}a.i?undefined:CJ(a)}
function wJ(a){if(zJ(a)){return}a.i?CJ(a):undefined}
function fR(a,b){return Je(a.e,b,kp?kp:(kp=new Bn))}
function Reb(a,b){return dE(a,b)<0?-1:dE(a,b)>0?1:0}
function Q1(a,b){var c,d;d=S1(a,b);c=V1(d);return c}
function qab(a,b){rab(a.c,jq(eq(KB,1),Jtb,1,5,[b]))}
function oab(a,b){rab(a.a,jq(eq(KB,1),Jtb,1,5,[b]))}
function pab(a,b){rab(a.b,jq(eq(KB,1),Jtb,1,5,[b]))}
function Gab(a,b){rab(a.B,jq(eq(KB,1),Jtb,1,5,[b]))}
function Gbb(a,b){rab(a.R,jq(eq(KB,1),Jtb,1,5,[b]))}
function lb(a,b){Ukb(a.a,b);a.a.a.length==0&&pb(a.b)}
function rlb(a){ttb(a.b!=-1);Tkb(a.c,a.a=a.b);a.b=-1}
function Elb(a){return new zsb(null,Dlb(a,a.length))}
function ph(b,a){return b[a]==null?null:String(b[a])}
function $i(a){return (Gh(),a).createElement('span')}
function kh(a){return (Gh(),a).getAttribute($ub)||''}
function le(a){return (JF(),a.Yc).style.display!=Pub}
function Fq(a){return oq(~a.l&Ovb,~a.m&Ovb,~a.h&Stb)}
function u_(a,b,c,d,e){Kab(a.W,b,d,c,e);qb(a.s,200)}
function u$(a,b,c,d){var e;e=new KT(c,d);kU(a.V,b,e)}
function J4(a,b,c,d){a.b[p4(new r4(new A4(b),c))]=d}
function L4(a,b,c,d){a.e[p4(new r4(new A4(b),c))]=d}
function Q$(a,b){return !!a.w&&Rkb(a.w,Neb(b),0)!=-1}
function P$(a,b){return !!a.v&&Rkb(a.v,Neb(b),0)!=-1}
function L1(a){return a.a.t==5&&(a.a.u==3||a.a.u==4)}
function I5(a,b){a.a.e=a.c+(a.b-a.c)*b;C5(a.a,a.a.e)}
function aP(a,b){b.length==0?iL(a.j,b):iL(a.j,'='+b)}
function Pib(a,b){return cr(b)?Tib(a,b):!!Gob(a.a,b)}
function iQ(a,b){if(a.I==b){return}a.I=b;Rlb();amb()}
function IT(a,b){(JF(),a.Yc).style[Rub]=b+(im(),rwb)}
function _J(a,b){a.w=b;ZJ(a);b.length==0&&(a.w=null)}
function dK(a,b){a.A=b;ZJ(a);b.length==0&&(a.A=null)}
function Yib(a){a.a=new Job(a);a.b=new _ob(a);dob(a)}
function wob(a){this.a=new rob(a.size());cib(this,a)}
function ftb(a,b){var c;c=a.slice(0,b);return kq(c,a)}
function Dlb(a,b){return Xpb(b,a.length),new nqb(a,b)}
function HG(a,b){return Cp((!AG&&(AG=new YG),AG),a,b)}
function r3(a){n3(a);g3((!b3&&(b3=new l3),b3),a.a.c)}
function jg(a){$wnd.setTimeout(function(){throw a},0)}
function kI(a){if(a.bb){return a.bb.vd()}return false}
function Wi(a){return (Gh(),Fh).Pd(a,lvb,false,false)}
function _i(a){return (Gh(),a).createElement('style')}
function dj(a){return (Gh(),a).createElement('table')}
function aj(a){return (Gh(),a).createElement('tbody')}
function YJ(a){return !jfb(Bvb,Kj((JF(),a.Yc).style))}
function n$(a){!jfb(Bvb,Kj((JF(),a.Yc).style))&&oN(a)}
function FT(a,b){(JF(),a.Yc).style[Qub]=b+(im(),'pt')}
function sX(a,b,c){var d,e;d=b+10;e=c-25;lN(a.Xb,d,e)}
function sob(a,b){var c;c=Uib(a.a,b,a);return c==null}
function Wdb(a,b){var c;c=new Udb;c.g=a;c.d=b;return c}
function Xdb(a,b,c){var d;d=Wdb(a,b);ieb(c,d);return d}
function Pbb(a){var b;b=[];Jpb(a,new Qbb(b));return b}
function ptb(a){if(a==null){throw aE(new $eb)}return a}
function Rfb(){Rfb=HE;Qfb=new dF(null);new dF(null)}
function EN(){TJ();gK.call(this);dN(this);nN(this,_M)}
function FN(){TJ();hK.call(this);dN(this);nN(this,_M)}
function KI(){JI.call(this);rI(this.a,'\u25BC',true)}
function fQ(a){if(!a.uf().pb){return false}return true}
function fJ(a,b){eJ(a,b);return gJ(Gj((JF(),a.Yc))[b])}
function NU(a,b){var c;c=nV(a);if(!c){return}OU(a,b,c)}
function tU(a){var b;b=nU(a);IU(a,new Wkb,a.bb,a.xb,b)}
function T$(a){a.C?C$(a,false):(a.C=true);V$(a,a.a-1)}
function iP(a){a.f=false;a.e=null;a.q=-1;a.s=-1;NO(a)}
function Gfb(a,b){a.a+=String.fromCharCode(b);return a}
function bhb(a,b,c){Igb();this.e=a;this.d=b;this.a=c}
function hqb(a,b){ptb(b);while(a.c<a.d){mqb(a,b,a.c++)}}
function dsb(a){if(!a.b){esb(a);a.c=true}else{dsb(a.b)}}
function Qi(a){return (Gh(),a).createElement('canvas')}
function Ti(a){return (Gh(),a).createElement('iframe')}
function Zi(a){return (Gh(),a).createElement('select')}
function uG(a,b){return Cp(a.a,(!wp&&(wp=new Bn),wp),b)}
function Lpb(a,b){return dr(a)===dr(b)||a!=null&&M(a,b)}
function RV(a){return !!a.T&&Tib(a.T,Ewb+a.rc+Fwb+a.sc)}
function Yl(){Wl();return jq(eq(Ot,1),Jtb,95,0,[Ul,Vl])}
function ym(){wm();return jq(eq(_t,1),Jtb,96,0,[vm,um])}
function GG(a){FG();JG();return HG(kp?kp:(kp=new Bn),a)}
function I4(a,b,c){a.b[p4(new r4(new A4(b),'!new'))]=c}
function yZ(a,b,c){a.f=b;d0((!a.D&&(a.D=new S0),a.D),c)}
function NZ(a,b,c,d,e,f,g,h){I_(rQ(a.a),b,c,d,e,f,g,h)}
function J_(a,b,c,d,e,f,g,h,i){SR(a.Q,b,c,d,e,f,g,h,i)}
function Rib(a,b){return cr(b)?Sib(a,b):Cib(Gob(a.a,b))}
function pob(a,b){return dr(a)===dr(b)||a!=null&&M(a,b)}
function J$(a,b){return b>0&&a.g.length>=b?a.g[b-1]:a.q}
function $1(b,a){return Object.hasOwnProperty.call(b,a)}
function Afb(a){return String.fromCharCode.apply(null,a)}
function ej(a){return (Gh(),a).createElement('textarea')}
function GI(a){pe(this,(JF(),a));this.a=new sI(this.Yc)}
function opb(){this.a=new Bpb;this.c=new Bpb;npb(this)}
function m3(a){this.a=new Wkb;this.c='__eager';this.b=a}
function EM(a,b,c,d){this.a=a;this.d=b;this.c=c;this.b=d}
function YM(a,b,c,d){this.d=a;this.a=b;this.c=c;this.b=d}
function rY(a,b,c,d){this.a=a;this.d=b;this.b=c;this.c=d}
function tY(a,b,c,d){this.a=a;this.d=b;this.b=c;this.c=d}
function RK(a){QH.call(this);pe(this,(JF(),a));Le(this)}
function M6(a,b,c,d){this.a=a;this.b=b;this.c=c;this.d=d}
function $db(a,b){var c;c=Wdb('',a);c.j=b;c.f=1;return c}
function F5(a,b){q5();var c;c=new R5;P5(c,a,b);return c}
function w6(){var a;a=null;a+=(wcb(),'?v='+vcb);return a}
function e3(a){var b;b=a.a['__eager'];b.d==0&&_2(b,a.c)}
function cf(a,b){b?_e.bf((JF(),a.Yc)):_e._e((JF(),a.Yc))}
function R5(){this.b=N1((F1(),!E1&&(E1=new O1),F1(),E1))}
function QE(){OE(this,new bF(true));PE(this,(Kqb(),Bqb))}
function zab(a,b){rab(a.p,jq(eq(KB,1),Jtb,1,5,[Neb(b)]))}
function Fab(a,b){rab(a.A,jq(eq(KB,1),Jtb,1,5,[Neb(b)]))}
function Jab(a,b){rab(a.D,jq(eq(KB,1),Jtb,1,5,[Neb(b)]))}
function Mab(a,b){rab(a.L,jq(eq(KB,1),Jtb,1,5,[Neb(b)]))}
function Yab(a,b){rab(a.Q,jq(eq(KB,1),Jtb,1,5,[Pbb(b)]))}
function usb(a,b){esb(a);return new zsb(a,new Tsb(b,a.a))}
function vsb(a,b){esb(a);return new hsb(a,new Jsb(b,a.a))}
function wsb(a,b){esb(a);return new msb(a,new Osb(b,a.a))}
function Uib(a,b,c){return cr(b)?Vib(a,b,c):Hob(a.a,b,c)}
function pdb(a,b,c){ndb(this);this.c=a;this.b=b;this.a=c}
function nqb(a,b){this.c=0;this.d=b;this.b=17488;this.a=a}
function BM(a,b,c){this.a=a;this.d=b;this.c=null;this.b=c}
function CM(a,b,c){this.a=a;this.d=b;this.c=null;this.b=c}
function fsb(a){if(!a){this.b=null;new Wkb}else{this.b=a}}
function XJ(a){if(!a.M){return}BK(a.L,false,false);np(a)}
function hfb(a,b){vtb(b,a.length);return a.charCodeAt(b)}
function Ugb(a){var b;b=a.a[0];return a.e>0||b==Ytb?b:-b}
function bh(a){var b;b=Kh((Gh(),a));!!b&&b.removeChild(a)}
function ZE(a){var b,c;b=new QE;Drb(a,b);c=new SE;Drb(a,c)}
function Rlb(){Rlb=HE;Olb=new Vlb;Plb=new kmb;Qlb=new smb}
function Etb(){if(ztb==256){ytb=Atb;Atb=new K;ztb=0}++ztb}
function Gg(){Gg=HE;var a,b;b=!Mg();a=new Ug;Fg=b?new Ng:a}
function oH(a){var b;b=(Gh(),Fh).Ud(a);b[hwb]=a.type;nH(a)}
function Eb(a,b){Kb((Dc(),Cc),a,jq(eq(QB,1),_tb,2,6,[b]))}
function R7(a,b,c){A1(a.e.F,c,Ih((Gh(),b),'resource-'+c))}
function LZ(a,b,c,d,e,f,g,h,i){J_(rQ(a.a),b,c,d,e,f,g,h,i)}
function Xib(a,b){return b==null?Iob(a.a,null):$ob(a.b,b)}
function TV(a,b){return b==a.c||b==a.Oc||b==a.Qc||b==a.zc}
function yG(a){return $wnd.decodeURI(a.replace('%23','#'))}
function cE(a,b){return eE(Aq(jE(a)?uE(a):a,jE(b)?uE(b):b))}
function pE(a,b){return eE(Gq(jE(a)?uE(a):a,jE(b)?uE(b):b))}
function yE(a,b){return eE(Oq(jE(a)?uE(a):a,jE(b)?uE(b):b))}
function SE(){OE(this,new bF(false));PE(this,(Kqb(),Bqb))}
function V(){W.call(this,(!db&&(db=eb()?new fb:new nb),db))}
function q5(){q5=HE;o5=L1((F1(),!E1&&(E1=new O1),F1(),E1))}
function Cl(){Cl=HE;Bl=new Fl;Al=new Gl;yl=new Hl;zl=new Il}
function rl(){rl=HE;ql=new ul;ol=new vl;pl=new wl;nl=new xl}
function Nl(){Nl=HE;Jl=new Ql;Kl=new Rl;Ll=new Sl;Ml=new Tl}
function M1(a){if(a.a.b==8){return a.a.c>=0}return a.a.b>8}
function q2(a){if(!a){return Xtb}return r2(a)+' ('+a.H+')'}
function mT(a,b){if(!a.a.f){aX(a.b,true);_$(a.b.a)}Ej(b.a)}
function npb(a){a.a.a=a.c;a.c.b=a.a;a.a.b=a.c.a=null;a.b=0}
function dfb(a,b,c){this.a=Ktb;this.d=a;this.b=b;this.c=c}
function J5(a,b,c){this.a=a;this.c=b;this.b=c;V.call(this)}
function x4(a,b,c){this.b=b;this.c=Ulb(new Hlb(c));this.a=a}
function $6(a,b){var c;c=a.Pf();Object.assign(c,b);return c}
function z2(a){w2();var b,c;b=H2(a);c=I2(a);return y2(b,c)}
function tG(){qG();var a;a=sG();if(!jfb(a,pG)){pG=a;zp(oG)}}
function TK(){QK();try{aI(PK,NK)}finally{Yib(PK.a);Yib(OK)}}
function dq(){bq();return jq(eq(Nu,1),Jtb,110,0,[aq,_p,$p])}
function pc(a,b){Kb((Dc(),Bc),a,jq(eq(Or,1),Jtb,177,0,[b]))}
function Orb(a,b){if(!zrb){return}Jrb(a,(Kqb(),Jqb),b,null)}
function Hrb(a,b){if(!wrb){return}Jrb(a,(Kqb(),Gqb),b,null)}
function Nrb(a,b){if(!yrb){return}Jrb(a,(Kqb(),Iqb),b,null)}
function zN(a){if(a.r){p2(a.r);return a.q}else{return null}}
function fO(a,b){if(a.b){a.qf(0);a.f||a.nf(b)}else{a.qf(b)}}
function qqb(a){if(!a.d){a.d=new slb(a.b);a.c=a.b.a.length}}
function Tib(a,b){return b==null?!!Gob(a.a,null):Xob(a.b,b)}
function Ulb(a){Rlb();return Zq(a,180)?new Xnb(a):new Qmb(a)}
function ih(a){return (Gh(),Fh).Yd(a)+((a.offsetWidth||0)|0)}
function eX(a,b){EX(a,UU(a),b);VX(a,b);a.tb=hW(b,a.tb);FX(a)}
function UH(a,b){var c;c=PH(a,b);c&&VH((JF(),b.Yc));return c}
function zf(a,b){var c;c=Sdb(a.qg);return b==null?c:c+': '+b}
function dob(a){var b,c;c=a;b=c.$modCount|0;c.$modCount=b+1}
function pO(a){a.a.C&&rab(a.a.a.W.n,jq(eq(KB,1),Jtb,1,5,[]))}
function Ibb(a,b,c){rab(a.T,jq(eq(KB,1),Jtb,1,5,[Neb(b),c]))}
function adb(){$cb();return jq(eq(cB,1),Jtb,143,0,[Ycb,Zcb])}
function Rp(a,b,c,d){a.b>0?Ip(a,new EM(a,b,c,d)):Mp(a,b,c,d)}
function X6(a,b,c,d){this.b=a;this.c=b;W6(this,c);V6(this,d)}
function zM(b,c,d){try{b.setSelectionRange(c,c+d)}catch(a){}}
function gP(b,c,d){try{b.setSelectionRange(c,c+d)}catch(a){}}
function JV(a,b,c){return b>=a.bb&&b<=a.xb&&c>=a.db&&c<=a.zb}
function Je(a,b,c){return Cp(!a.Wc?(a.Wc=new Fp(a)):a.Wc,c,b)}
function wib(a,b){return b===a?'(this Map)':b==null?Xtb:KE(b)}
function tdb(a,b){if(a==null){return b==null}return jfb(a,b)}
function ceb(a){if(a.Wf()){return null}var b=a.j;return EE[b]}
function IE(a){function b(){}
;b.prototype=a||{};return new b}
function uL(){uL=HE;qL=new xL;rL=new yL;sL=new zL;tL=new AL}
function wL(){uL();return jq(eq(gw,1),Jtb,65,0,[qL,rL,sL,tL])}
function tl(){rl();return jq(eq(Bt,1),Jtb,62,0,[ql,ol,pl,nl])}
function El(){Cl();return jq(eq(Gt,1),Jtb,63,0,[Bl,Al,yl,zl])}
function Pl(){Nl();return jq(eq(Lt,1),Jtb,64,0,[Jl,Kl,Ll,Ml])}
function xM(b){try{return b.selectionStart}catch(a){return 0}}
function iW(a,b){if(b){Yib(b);!!a&&vib(b,a)}else{b=a}return b}
function WT(a,b){if(!a)return;(Gh(),Fh).fe(a,b);a.title=b||''}
function gh(a){return (Gh(),Fh).Zd(a)+((a.offsetHeight||0)|0)}
function er(a){return Math.max(Math.min(a,Otb),-2147483648)|0}
function _g(a){while(a.lastChild){a.removeChild(a.lastChild)}}
function AN(){hK.call(this);this.I=false;dN(this);nN(this,_M)}
function Mf(a,b){sf(this);this.e=b;this.f=a;uf(this);this.Id()}
function xU(a){vT(a.Dc);if(a.pb){vT(a.pb);bh(a.pb);a.pb=null}}
function hp(a,b){var c;if(ep){c=new fp(b);!!a.Wc&&Dp(a.Wc,c)}}
function Fnb(a,b){var c;for(c=0;c<b;++c){a[c]=new Qnb(a[c])}}
function tsb(a,b){var c;return xsb(a,new Wkb,(c=new btb(b),c))}
function Xpb(a,b){if(0>a||a>b){throw aE(new Fdb(iub+a+jub+b))}}
function rtb(a,b){if(a<0||a>b){throw aE(new Edb(kub+a+lub+b))}}
function bQ(a,b){if(!a.J){return Rlb(),Rlb(),Olb}return a.J[b]}
function y1(a){if(!a.b){a.b=new s$;kN(a.b,rQ(a.d))}return a.b}
function L_(a,b){if(!a.f){a.f=b}else{Yib(a.f);!!b&&vib(a.f,b)}}
function T_(a,b){if(!a.k){a.k=b}else{Yib(a.k);!!b&&vib(a.k,b)}}
function t0(a,b){if(!a.N){a.N=b}else{Yib(a.N);!!b&&vib(a.N,b)}}
function tX(a,b,c,d){a.Zb=false;s2((og(),ng),new tY(a,b,c,d))}
function vX(a,b,c,d){a.Zb=false;s2((og(),ng),new rY(a,b,c,d))}
function _O(a){a.t.Z?qb(new HP(a),100):s2((og(),ng),new JP(a))}
function qfb(a){return (new RegExp('^([^A-z0-9:!])$')).test(a)}
function Sib(a,b){return b==null?Cib(Gob(a.a,null)):Yob(a.b,b)}
function R4(a){return (!b3&&(b3=new l3),b3).c.d[(new A4(a)).b]}
function TF(a){JF();return a.__gwt_resolve?a.__gwt_resolve():a}
function Xrb(){Vrb();return jq(eq(xD,1),Jtb,87,0,[Srb,Trb,Urb])}
function Dh(a){if(dh(a)){return !!a&&a.nodeType==1}return false}
function jjb(a,b){if(Zq(b,101)){return tib(a.a,b)}return false}
function otb(a,b){if(a<0||a>=b){throw aE(new Edb(kub+a+lub+b))}}
function vtb(a,b){if(a<0||a>=b){throw aE(new Pfb(kub+a+lub+b))}}
function kpb(a,b){ptb(b);while(a.a<a.c.a.length){ctb(b,qlb(a))}}
function qI(a){var b;b=a.c?mh(a.a):a.a;return (Gh(),Fh).be(b)}
function aeb(a,b){var c=a.a=a.a||[];return c[b]||(c[b]=a.Rf(b))}
function Fob(a,b){var c;c=a.a.get(b);return c==null?new Array:c}
function aH(a){var b=a.__listener;return !ar(b)&&Zq(b,9)?b:null}
function sI(a){this.a=a;this.c=false;this.b=Yp(a);this.d=this.b}
function Rf(a){Pf();Nf.call(this,a);this.a='';this.b=a;this.a=''}
function VH(a){a.style[jwb]='';a.style[kwb]='';a.style[gvb]=''}
function B5(a,b){a.a=b;if(o5){b+=a.n;C5(a,-b)}else{C5(a,-a.a)}}
function OH(a,b,c){Oe(b);FL(a.o,b);JF();Wg(c,TF(b.Yc));Qe(b,a)}
function MM(a,b,c,d){if(!jfb(a.b,c)){a.b=c;a.hf()}a.f=d;LM(a,b)}
function Lfb(a,b,c){a.a=wfb(a.a,0,b)+(''+c)+vfb(a.a,b);return a}
function D$(a,b){var c,d;c=a>0?I$(a):'';d=b>0?''+b:'';return c+d}
function d2(a,b){if(a.b!=b){a.b=b;return true}else{return false}}
function e2(a,b){if(a.e!=b){a.e=b;return true}else{return false}}
function l0(a,b){if(!a.G){a.G=b}else{Yib(a.G.a);!!b&&cib(a.G,b)}}
function m0(a,b){if(!a.H){a.H=b}else{Yib(a.H.a);!!b&&cib(a.H,b)}}
function Vib(a,b,c){return b==null?Hob(a.a,null,c):Zob(a.b,b,c)}
function lV(a,b){return Q$(a.a,b)?0:b>=a.W.length?bV(a):a.W[b-1]}
function nf(a,b){return !!a&&!!a.equals?a.equals(b):dr(a)===dr(b)}
function EH(a,b){for(var c in a){a.hasOwnProperty(c)&&b(c,a[c])}}
function z5(a,b){var c;if(!p5&&Bj(b.a).length==1){c=b.a;u5(a,c)}}
function vE(a){var b;if(jE(a)){b=a;return b==-0.?0:b}return Lq(a)}
function IG(a){FG();JG();KG();return HG((!qp&&(qp=new Bn),qp),a)}
function eK(a){if(a.M){return}else a.Uc&&Oe(a);BK(a.L,true,false)}
function bk(){_j();return jq(eq(ct,1),Jtb,55,0,[Zj,Xj,Wj,Yj,$j])}
function Km(){Im();return jq(eq(fu,1),Jtb,56,0,[Dm,Em,Fm,Gm,Hm])}
function kcb(){icb();return jq(eq(SA,1),Jtb,122,0,[fcb,hcb,gcb])}
function Pcb(){Ncb();return jq(eq($A,1),Jtb,112,0,[Mcb,Lcb,Kcb])}
function Gcb(){Ecb();return jq(eq(WA,1),Jtb,117,0,[Ccb,Dcb,Bcb])}
function BF(){BF=HE;new RegExp('%5B','g');new RegExp('%5D','g')}
function kP(a){var b;W$(a.t,sfb((b=gL(a.a),b==null?'':b),' ',''))}
function DU(a){var b;b=WU(a,a.rc,a.sc);a.nb=null;!!b&&th(b.d,Hxb)}
function SV(a){var b;b=XU(a,a.rc,a.sc);return !!b&&b.isPercentage}
function qlb(a){ntb(a.a<a.c.a.length);a.b=a.a++;return a.c.a[a.b]}
function B$(a){if(a.J){while(0<a.J.a.length){wW(a.V,Tkb(a.J,0))}}}
function XZ(a,b,c){if(dQ(a.a).u){a.a.i=b;a.a.g=null;zab(a.a.j,c)}}
function YZ(a,b,c){if(dQ(a.a).u){a.a.i=b;a.a.g=null;Mab(a.a.j,c)}}
function k_(a,b){a.t&&(a.c=false,s2((og(),ng),new j1(a,b,false)))}
function sab(a,b,c){rab(a.e,jq(eq(KB,1),Jtb,1,5,[Neb(b),Neb(c)]))}
function yab(a,b,c){rab(a.o,jq(eq(KB,1),Jtb,1,5,[Neb(b),Neb(c)]))}
function Bab(a,b,c){rab(a.r,jq(eq(KB,1),Jtb,1,5,[Neb(b),Neb(c)]))}
function Cab(a,b,c){rab(a.s,jq(eq(KB,1),Jtb,1,5,[Neb(b),Neb(c)]))}
function Eab(a,b,c){rab(a.w,jq(eq(KB,1),Jtb,1,5,[Neb(b),Neb(c)]))}
function Hab(a,b,c){rab(a.H,jq(eq(KB,1),Jtb,1,5,[Neb(b),Neb(c)]))}
function Iab(a,b,c){rab(a.I,jq(eq(KB,1),Jtb,1,5,[Neb(b),Neb(c)]))}
function Lab(a,b,c){rab(a.K,jq(eq(KB,1),Jtb,1,5,[Neb(b),Neb(c)]))}
function Nab(a,b,c){rab(a.M,jq(eq(KB,1),Jtb,1,5,[Neb(b),Neb(c)]))}
function Pab(a,b,c){rab(a.O,jq(eq(KB,1),Jtb,1,5,[Neb(b),Neb(c)]))}
function Hbb(a,b,c){rab(a.S,jq(eq(KB,1),Jtb,1,5,[Neb(b),Neb(c)]))}
function Xjb(a,b,c){stb(b,c,a.size());this.c=a;this.a=b;this.b=c-b}
function urb(a,b){this.a=a;this.d=b;this.c=(Rfb(),hE(Date.now()))}
function Jsb(a,b){aqb.call(this,b.kg(),b.jg()&-6);ptb(a);this.a=b}
function Osb(a,b){dqb.call(this,b.kg(),b.jg()&-6);ptb(a);this.a=b}
function TS(a,b){a.k.style[Qub]=b+(im(),rwb);a.u.style[Qub]=b+rwb}
function UT(a,b){a.j=b;a.a.style[nvb]=(b?(Ak(),pk):(Ak(),lk)).ke()}
function s2(a,b){++a.a;a.b=yg(a.b,[b,false]);tg(a);vg(a,new u2(a))}
function bob(a,b){if(b.$modCount!=a.$modCount){throw aE(new eob)}}
function Bf(b){if(!('stack' in b)){try{throw b}catch(a){}}return b}
function dh(b){try{return !!b&&!!b.nodeType}catch(a){return false}}
function sh(a,b){var c;b=Eh(b);c=Ch(a.className||'',b);return c!=-1}
function Ydb(a,b,c,d){var e;e=Wdb(a,b);ieb(c,e);e.f=d?8:0;return e}
function Lh(a,b){var c;return qh((c=a.ae(b),c?c:b.documentElement))}
function sfb(a,b,c){c=Cfb(c);return a.replace(new RegExp(b,'g'),c)}
function $W(a,b){b?th(a.Gc,'nogrid'):eh(a.Gc,'nogrid');a.Db&&fY(a)}
function TU(a,b){b?s2((og(),ng),new BY(a)):(a.zc.focus(),undefined)}
function oN(a){aN=a;gN(a);a.F?T(new Y6(a),200,Xf()):hN(a,1);aN=null}
function Mjb(a){ttb(a.c!=-1);a.d.removeAtIndex(a.c);a.b=a.c;a.c=-1}
function Lgb(a){while(a.d>0&&a.a[--a.d]==0);a.a[a.d++]==0&&(a.e=0)}
function NM(a){if(!a.a){a.a=Ri($doc);a.a.className=Cwb;Wg(a.d,a.a)}}
function OM(a){if(!a.e){a.e=Ri($doc);a.e.className=Dwb;Wg(a.d,a.e)}}
function ZV(a,b){!!a.gb&&MN(a.gb);(JF(),b.Yc).style[pvb]='1';a.gb=b}
function hW(a,b){if(b){b.clear();!!a&&b.addAll(a)}else{b=a}return b}
function FU(a,b,c,d){var e;e=Ewb+c+Fwb+d;Vib(a.r,e,b);K0(a.a,b,c,d)}
function Gob(a,b){var c;return Eob(b,Fob(a,b==null?0:(c=Q(b),c|0)))}
function idb(){gdb();return jq(eq(eB,1),Jtb,99,0,[edb,fdb,ddb,cdb])}
function rV(a){return jq(eq(ir,1),Rxb,17,15,[a.db,a.bb,a.zb,a.xb])}
function rj(a){return jfb(a.compatMode,avb)?a.documentElement:a.body}
function xF(a){if(a==null){throw aE(new afb('uri is null'))}this.a=a}
function Rjb(a,b){this.a=a;Njb.call(this,a);rtb(b,a.size());this.b=b}
function nZ(a,b,c,d,e){this.g=a;this.b=b;this.c=d;this.d=e;this.a=c}
function Lob(a){this.e=a;this.b=this.e.a.entries();this.a=new Array}
function hP(a){a.v=true;JO(a,a.w);nP(a,true);s2((og(),ng),new DP(a))}
function VW(a,b,c){aY(a,UU(a),b);UX(a,b);a.r=iW(b,a.r);a.i=iW(c,a.i)}
function ab(a,b){U(a.a,b)?(a.a.q=a.a.s.hd(a.a.j,a.a.n)):(a.a.q=null)}
function pb(a){if(!a.j){return}++a.g;a.i?tb(a.j.a):ub(a.j.a);a.j=null}
function gg(a){a&&qg((og(),ng));--Yf;if(a){if(ag!=-1){lg(ag);ag=-1}}}
function JL(a,b){var c;c=GL(a,b);if(c==-1){throw aE(new Kpb)}IL(a,c)}
function ZU(a,b,c){var d;d=Sib(a.e,Ewb+b+Fwb+c);return !d?'':d.value}
function Arb(){Arb=HE;xrb=false;vrb=true;wrb=true;zrb=true;yrb=true}
function $cb(){$cb=HE;Ycb=new _cb('ALERT',0);Zcb=new _cb('STATUS',1)}
function _j(){_j=HE;Zj=new ck;Xj=new dk;Wj=new ek;Yj=new fk;$j=new gk}
function Im(){Im=HE;Dm=new Lm;Em=new Mm;Fm=new Nm;Gm=new Om;Hm=new Pm}
function SI(){SI=HE;new TI('bottom');new TI('middle');RI=new TI(kwb)}
function Flb(a,b){return new zsb(null,(Xpb(b,a.length),new nqb(a,b)))}
function yT(a,b){return a.sheet.insertRule(b,a.sheet.cssRules.length)}
function KK(a){return function(){this.__gwt_resolve=LK;return a.od()}}
function pL(a){nL.call(this,a);(JF(),this.Yc).className='gwt-TextBox'}
function Esb(a,b){gqb.call(this,b.kg(),b.jg()&-16449);ptb(a);this.b=b}
function A6(){LQ.call(this);this.a=null;new J6(this,this);this.b=null}
function J6(a,b){this.a=a;this.f=new $4(this);this.c=b;this.b='click'}
function UM(a,b,c){this.n=a;this.c=b;this.k=c;this.d=Ri($doc);RM(this)}
function WZ(a,b,c,d){if(dQ(a.a).u){a.a.g=b;a.a.i=null;Cab(a.a.j,d,c)}}
function Kbb(a,b,c,d){rab(a.V,jq(eq(KB,1),Jtb,1,5,[b,Neb(c),Neb(d)]))}
function wab(a,b,c,d){rab(a.j,jq(eq(KB,1),Jtb,1,5,[Neb(b),Neb(c),d]))}
function N4(a,b,c){var d;d=!c?null:(Rdb(c),c.k);V4(a.c,(Rdb(b),b.k),d)}
function $Q(a,b){(JF(),a.Yc).style[nvb]=(b?(Ak(),pk):(Ak(),hk)).ke()}
function aX(a,b){b?Be((JF(),a.Yc),ayb,false):Be((JF(),a.Yc),ayb,true)}
function eJ(a,b){if(b<0||b>=Gj((JF(),a.Yc)).length){throw aE(new Ddb)}}
function k2(a,b){return a-(Gh(),Fh).Yd(b)+Fh.ce(b)+oj(b.ownerDocument)}
function ifb(a,b){var c;c=b.length;return jfb(a.substr(a.length-c,c),b)}
function II(a){var b;GI.call(this,(b=a,kfb('span',(Gh(),a).tagName),b))}
function pV(a){var b;b=Sib(a.e,Ewb+a.rc+Fwb+a.sc);return !b?'':b.value}
function V1(a){var b=parseInt(a,10);if(isNaN(b))return 0;else return b}
function I1(){var a=$wnd.document.documentMode;if(!a)return -1;return a}
function LK(){throw 'A PotentialElement cannot be resolved twice.'}
function lF(a){if(a==null){throw aE(new afb('html is null'))}this.a=a}
function Qrb(a){Arb();if(xrb){return new Prb(null)}return qrb(srb(),a)}
function D5(a,b,c,d){if(b>0){a.r=true;a.i=new J5(a,c,d);T(a.i,b,Xf())}}
function lpb(a,b,c,d){var e;e=new Bpb;e.c=b;e.b=c;e.a=d;d.b=c.a=e;++a.b}
function tfb(a,b,c){var d;c=Cfb(c);d=new RegExp(b);return a.replace(d,c)}
function Skb(a,b,c){for(;c>=0;--c){if(Lpb(b,a.a[c])){return c}}return -1}
function Pgb(a,b){var c;for(c=a.d-1;c>=0&&a.a[c]===b[c];c--);return c<0}
function uf(a){if(a.k){a.backingJsObject!==Ttb&&a.Id();a.i=null}return a}
function Kh(a){var b=a.parentNode;(!b||b.nodeType!=1)&&(b=null);return b}
function Xf(){if(Date.now){return Date.now()}return (new Date).getTime()}
function eg(b){bg();return function(){return fg(b,this,arguments);var a}}
function Xcb(){Vcb();return jq(eq(aB,1),Jtb,77,0,[Scb,Ucb,Rcb,Qcb,Tcb])}
function ssb(a,b){return (esb(a),ysb(new zsb(a,new Esb(b,a.a)))).mg(qsb)}
function me(a,b){se(a,ye((TJ(),SJ).ef((JF(),JF(),mh(a.Yc))))+'-'+b,false)}
function JI(){II.call(this,Ri($doc));(JF(),this.Yc).className='gwt-HTML'}
function bpb(a){this.d=a;this.b=this.d.a.entries();this.a=this.b.next()}
function Prb(a){Arb();if(xrb){return}this.c=a;this.e=true;this.a=new Wkb}
function gG(a){a.e=false;a.f=null;a.a=false;a.b=false;a.c=true;a.d=null}
function GM(a){!!a.a&&Wg(a.d,a.a);!!a.e&&Wg(a.d,a.e);!!a.j&&Wg(a.d,a.j)}
function qS(a){a.N&&pS(a);h_(a.Q.a,a.Q.rc,a.U,a.Q.sc,a.V);a.o=false;mS(a)}
function H_(a){lX(a.V,1,1);v_(a,a.i,a.O);h_(a,1,a.i,1,a.O);O0(a,1,1,null)}
function Z$(a,b){a.B&&!a.u.f&&(a.c=false,s2((og(),ng),new j1(a,b,true)))}
function hj(a){!a.gwt_uid&&(a.gwt_uid=1);return 'gwt-uid-'+a.gwt_uid++}
function Ygb(a,b){if(b==0||a.e==0){return a}return b>0?qhb(a,b):thb(a,-b)}
function Zgb(a,b){if(b==0||a.e==0){return a}return b>0?thb(a,b):qhb(a,-b)}
function xqb(a,b){this.b=', ';this.d=a;this.e=b;this.c=this.d+(''+this.e)}
function O4(){this.a={};this.f={};this.d={};this.e={};this.b={};this.c={}}
function Tsb(a,b){gqb.call(this,b.kg(),b.jg()&-6);ptb(a);this.a=a;this.b=b}
function Tkb(a,b){var c;c=(otb(b,a.a.length),a.a[b]);jtb(a.a,b,1);return c}
function nU(a){var b,c;c=0;for(b=1;b<a.bb-a.ob;b++){c+=J$(a.a,b)}return c}
function oU(a){var b,c;c=0;for(b=1;b<a.db-a.Tc;b++){c+=a.W[b-1]}return c}
function mq(a){var b,c,d;b=a&Ovb;c=a>>22&Ovb;d=a<0?Stb:0;return oq(b,c,d)}
function UV(a){var b;b=new Wkb;Nkb(b,a.sb);Okb(b,aV(a));return new slb(b)}
function WE(){var a;YE(UE);if(!qf){a=Qrb((Rdb(Su),Su.k));rf(new XE(a))}}
function xG(){var a;a=Ftb(tG);$wnd.addEventListener('hashchange',a,false)}
function F1(){F1=HE;var a;a=H1((!E1&&(E1=new O1),E1));QK();se(UK(),a,true)}
function Phb(a,b,c,d){var e;e=gq(ir,Rxb,17,b,15,1);Qhb(e,a,b,c,d);return e}
function Jbb(a,b,c,d){rab(a.U,jq(eq(KB,1),Jtb,1,5,[Neb(b),Neb(c),Neb(d)]))}
function Rrb(a,b,c,d){ptb(a);ptb(b);ptb(c);ptb(d);return new Yrb(b,new zqb)}
function utb(a,b,c){if(a<0||b>c||b<a){throw aE(new Pfb(mub+a+nub+b+jub+c))}}
function c_(a,b,c,d){if(a.R){(c!=a.V.rc||d!=a.V.sc)&&F$(a);WZ(a.R,b,c,d)}}
function p_(a){var b,c;b=qh(a.V.zc);c=(a.V.zc.scrollTop||0)|0;Hbb(a.W,b,c)}
function nH(a){var b;b=rH(a);if(!b){return}LF(a,b.nodeType!=1?null:b,aH(b))}
function ZJ(a){var b;b=a.O;if(b){a.w!=null&&b.pd(a.w);a.A!=null&&b.rd(a.A)}}
function wqb(a,b){!a.a?(a.a=new Ofb(a.d)):Kfb(a.a,a.b);Ifb(a.a,b);return a}
function GL(a,b){var c;for(c=0;c<a.c;++c){if(a.a[c]==b){return c}}return -1}
function iqb(a,b){ptb(b);if(a.c<a.d){mqb(a,b,a.c++);return true}return false}
function YU(a,b,c){var d;d=Sib(a.e,Ewb+b+Fwb+c);return !d?'':d.formulaValue}
function gq(a,b,c,d,e,f){var g;g=hq(e,d);e!=10&&jq(eq(a,f),b,c,e,g);return g}
function ahb(a,b){Igb();this.e=a;this.d=1;this.a=jq(eq(ir,1),Rxb,17,15,[b])}
function Xkb(a){Lkb(this);ltb(a>=0,'Initial capacity must not be negative')}
function WI(a){VI(this,new cJ(this,a));(JF(),this.Yc).className='gwt-Image'}
function LN(a){qI(a.a).length==0?Ee((JF(),a.Yc),false):Ee((JF(),a.Yc),true)}
function Re(a,b){a.Vc==-1?WF((JF(),a.Yc),b|(a.Yc.__eventBits||0)):(a.Vc|=b)}
function wV(a){EV(a,a.rc,a.sc)||QW(a,a.rc,a.sc);s2((og(),ng),new xY(a,true))}
function tW(a){if(a.R&&a.Fc){a.R=false;uW(a,Ewb+a.rc+Fwb+a.sc,a.S);a.S=null}}
function i_(a,b){wab(a.W,a.V.sc,a.V.rc,b);y$(a,b,true);SU(a.V);LR(a.Q,false)}
function pU(a,b,c){var d,e,f;f=0;for(e=b;e<=c;e++){d=J$(a.a,e);f+=d}return f}
function Khb(a,b,c,d){var e;e=gq(ir,Rxb,17,b+1,15,1);Lhb(e,a,b,c,d);return e}
function kq(a,b){fq(b)!=10&&jq(O(b),b.rg,b.__elementTypeId$,fq(b),a);return a}
function QF(b){JF();try{return !!b&&!!b.__gwt_resolve}catch(a){return false}}
function yM(b){try{return b.selectionEnd-b.selectionStart}catch(a){return 0}}
function x2(){w2();$wnd.getSelection&&$wnd.getSelection().removeAllRanges()}
function km(){im();return jq(eq(Yt,1),Jtb,34,0,[hm,fm,am,bm,gm,em,cm,_l,dm])}
function fj(a){var b;return b=(Gh(),a).createElement('INPUT'),b.type='text',b}
function XG(a){var b;WG();b=UG.get(a);return !b?null:b.getAtIndex(b.size()-1)}
function oE(a){var b;if(jE(a)){b=0-a;if(!isNaN(b)){return b}}return eE(Eq(a))}
function eQ(a,b){var c;c=(!a.L&&(a.L=_P(a)),a.L).qb;return !!c&&c.contains(b)}
function gV(a,b,c){var d;d=Sib(a.e,Ewb+b+Fwb+c);return !d?'':d.originalValue}
function Alb(a,b,c,d){var e;d=(Znb(),!d?Ynb:d);e=a.slice(b,c);Blb(e,a,b,c,-b)}
function KF(a,b){JF();var c;c=aH(b);if(!c){return false}LF(a,b,c);return true}
function Sgb(a){var b;if(a.e==0){return -1}b=Rgb(a);return (b<<5)+Leb(a.a[b])}
function Q7(a){var b;b=sfb((Rdb(cy),cy.k),$tb,'.');return bQ(a.e,b).Oe().Ze()}
function rS(a){var b;b=_R(a);a.v=(Gh(),Fh).Yd(b);a.w=Fh.Zd(b);a.O=a.e;a.P=a.K}
function nL(a){var b;jL.call(this,(b=a,!EF&&(EF=new FF),!CF&&(CF=new DF),b))}
function FJ(a){var b;this.b=new Wkb;this.f=new Wkb;sJ(this,(b=a,NJ(),SL(),b))}
function iI(a){if(!a.bb){throw aE(new Beb('initWidget() is not called yet'))}}
function _K(a){if(!a.a||!a.c.O){throw aE(new Kpb)}a.a=false;return a.b=a.c.O}
function ML(a){if(a.b>=a.c.c){throw aE(new Kpb)}a.a=a.c.a[a.b];++a.b;return a.a}
function nP(a,b){if(b){s2((og(),ng),new tP(a))}else if(a.f){a.q=eL(a.e);IO(a)}}
function k0(a,b){a.F=b;a.F?Be((JF(),a.Yc),xyb,true):Be((JF(),a.Yc),xyb,false)}
function HT(a,b,c){(JF(),a.Yc).style[Axb]=b+(im(),rwb);a.Yc.style[Bxb]=c+'pt'}
function Dab(a,b,c){rab(a.v,jq(eq(KB,1),Jtb,1,5,[(Gdb(),b?true:false),Neb(c)]))}
function Brb(a,b,c,d){var e;e=new urb(b,c);e.e=d;trb(e,xrb?null:a.c);Crb(a,e)}
function xsb(a,b,c){var d;dsb(a);d=new Ysb;d.a=b;a.a.lg(new dtb(d,c));return d.a}
function Rkb(a,b,c){for(;c<a.a.length;++c){if(Lpb(b,a.a[c])){return c}}return -1}
function LG(){FG();var a;if(zG){a=new PG;!!AG&&Dp(AG,a);return null}return null}
function G1(){try{document.createEvent(Hvb);return true}catch(a){return false}}
function eb(){return !!$wnd.requestAnimationFrame&&!!$wnd.cancelAnimationFrame}
function neb(a){return jfb(Mtb,typeof(a))||br(a,$wnd.java.lang.Number$impl)}
function fq(a){return a.__elementTypeCategory$==null?10:a.__elementTypeCategory$}
function rjb(a){var b;bob(a.e,a);ntb(a.b);a.c=a.a;b=a.a.Ze();a.b=qjb(a);return b}
function gsb(a){var b;dsb(a);b=gq(gr,Jtb,17,0,15,1);Wpb(a.a,new jsb(b));return b}
function lsb(a){var b;dsb(a);b=gq(ir,Rxb,17,0,15,1);Wpb(a.a,new osb(b));return b}
function Jh(a){var b=a.firstChild;while(b&&b.nodeType!=1)b=b.nextSibling;return b}
function Mh(a,b){var c;return ((c=a.ae(b),c?c:b.documentElement).scrollTop||0)|0}
function fpb(a){if(a.a.d!=a.c){return Yob(a.a,a.b.value[0])}return a.b.value[1]}
function Lq(a){if(Bq(a,(Tq(),Sq))<0){return -xq(Eq(a))}return a.l+a.m*Rtb+a.h*Qtb}
function rH(a){var b;b=(Gh(),Fh).Ud(a);while(!!b&&!aH(b)){b=b.parentNode}return b}
function NT(a){var b;b=Ri($doc);WT(b,a);b.className='sheet-tabsheet-tab';return b}
function mJ(){af();ef.call(this,Zi($doc));(JF(),this.Yc).className='gwt-ListBox'}
function l_(a){a.t=true;a.c=true;a.B?(a.B=false):RV(a.V)?(a.b=''):(a.b=pV(a.V))}
function T(a,b,c){S(a);a.o=true;a.p=false;a.k=b;a.t=c;a.n=null;++a.r;ab(a.j,Xf())}
function L0(a,b,c,d){if(a.V.rc==c&&a.V.sc==d){O0(a,c,d,null);b!=null&&fP(a.u,b)}}
function m_(a,b,c){wab(a.W,a.V.sc,a.V.rc,b);y$(a,b,c);if(c){SU(a.V);NR(a.Q,false)}}
function JT(a,b){GT(a,b.col,b.row);FT(a,b.height);IT(a,b.width);HT(a,b.dx,b.dy)}
function pgb(a,b){this.e=b;this.a=tgb(a);this.a<54?(this.f=vE(a)):(this.c=nhb(a))}
function B1(){this.c=new qob;this.a=new Z1;e3((!b3&&(b3=new l3),b3));this.d=new A6}
function bq(){bq=HE;aq=new cq('RTL',0);_p=new cq('LTR',1);$p=new cq('DEFAULT',2)}
function Tq(){Tq=HE;Pq=oq(Ovb,Ovb,524287);Qq=oq(0,0,Pvb);Rq=mq(1);mq(2);Sq=mq(0)}
function uab(a,b,c,d,e){rab(a.g,jq(eq(KB,1),Jtb,1,5,[Neb(b),Neb(c),Neb(d),Neb(e)]))}
function xab(a,b,c,d,e){rab(a.k,jq(eq(KB,1),Jtb,1,5,[Neb(b),Neb(c),Neb(d),Neb(e)]))}
function Kab(a,b,c,d,e){rab(a.F,jq(eq(KB,1),Jtb,1,5,[Neb(b),Neb(c),Neb(d),Neb(e)]))}
function Qab(a,b,c,d,e){rab(a.P,jq(eq(KB,1),Jtb,1,5,[Neb(b),Neb(c),Neb(d),Neb(e)]))}
function S7(a,b,c){var d;d=sfb((Rdb(cy),cy.k),$tb,'.');KZ(bQ(a.e,d).Oe().Ze(),b,c)}
function g3(a,b){var c,d;d=a3(a.a[b]);for(c=new slb(d);c.a<c.c.a.length;){qlb(c)}}
function gX(a,b,c,d,e){var f;f=fV(a,b);if(f){MM(f,c,d,e);return true}return false}
function Vgb(a,b){if(b.e==0){return Hgb}if(a.e==0){return Hgb}return Uhb(),Vhb(a,b)}
function Xp(a){if(null==a){throw aE(new afb('encodedURLComponent cannot be null'))}}
function lj(a){return (jfb(a.compatMode,avb)?a.documentElement:a.body).clientWidth|0}
function xfb(a,b){return b==(Epb(),Epb(),Dpb)?a.toLocaleLowerCase():a.toLowerCase()}
function yfb(a,b){return b==(Epb(),Epb(),Dpb)?a.toLocaleUpperCase():a.toUpperCase()}
function l2(a,b){return a-(Gh(),Fh).Zd(b)+((b.scrollTop||0)|0)+pj(b.ownerDocument)}
function gZ(a,b,c){var d;c.a?a.appendChild(b):(d=Kh((Gh(),b)),!!d&&d.removeChild(b))}
function rI(a,b,c){a.c=false;c?xh(a.a,b):yh(a.a,b);if(a.d!=a.b){a.d=a.b;Zp(a.a,a.b)}}
function uQ(a,b){J2(ie(a.zf()),true);!!a.r&&pb(a.r);if(a.s){!!b.a&&Dj(b.a);a.s=false}}
function bK(a,b){a.jf(false);oN(a);b.Xe(oh((JF(),a.Yc),Oub),oh(a.Yc,qwb));a.jf(true)}
function CL(a,b){var c,d;d=OF((JF(),b.Yc));c=PH(a,b);c&&ah(a.c,Kh((Gh(),d)));return c}
function Pkb(a,b){var c,d,e,f;ptb(b);for(d=a.a,e=0,f=d.length;e<f;++e){c=d[e];b.Qf(c)}}
function ne(a,b){var c=a.parentNode;if(!c){return}c.insertBefore(b,a);c.removeChild(a)}
function qg(a){var b,c;if(a.d){c=null;do{b=a.d;a.d=null;c=zg(b,c)}while(a.d);a.d=c}}
function pg(a){var b,c;if(a.c){c=null;do{b=a.c;a.c=null;c=zg(b,c)}while(a.c);a.c=c}}
function WG(){var a;a=(FG(),$wnd.location.search);if(!UG||!jfb(TG,a)){UG=VG(a);TG=a}}
function r2(a){var b;if(!a){return '(null)'}b=Sdb(a.qg);return vfb(b,ofb(b,Bfb(46))+1)}
function ieb(a,b){var c;if(!a){return}b.j=a;var d=ceb(b);if(!d){EE[a]=[b];return}d.qg=b}
function mpb(a,b){var c;c=b.c;b.a.b=b.b;b.b.a=b.a;b.a=b.b=null;b.c=null;--a.b;return c}
function cQ(a,b){var c;c=(Rdb(b),b.k);$1(a.K,c)||(a.K[c]=O2(b),undefined);return a.K[c]}
function scb(a,b,c){b<0&&(b=0);(c<0||c>a.length)&&(c=a.length);return a.substr(b,c-b)}
function uJ(a,b,c){if(!!b&&!b.b){return}AJ(a,b);c&&a.e&&a.Te();!!b&&a.c&&qJ(a,b,false)}
function EV(a,b,c){return (b<=a.ob||b>=eV(a)&&b<=jV(a))&&(c<=a.Tc||c<=sV(a)&&c>=VU(a))}
function K$(a,b){return !!a.v&&Rkb(a.v,Neb(b),0)!=-1?0:b>0&&a.g.length>=b?a.g[b-1]:a.q}
function KW(a,b){var c;vT(a);for(c=0;c<b.a.length;c++){yT(a,(otb(c,b.a.length),b.a[c]))}}
function AE(){BE();var a=zE;for(var b=0;b<arguments.length;b++){a.push(arguments[b])}}
function kj(a){return (jfb(a.compatMode,avb)?a.documentElement:a.body).clientHeight|0}
function ar(a){return a!=null&&(typeof a===Gtb||typeof a==='function')&&!(a.sg===LE)}
function DE(a,b){typeof window===Gtb&&typeof window['$gwt']===Gtb&&(window['$gwt'][a]=b)}
function GS(a,b){a.k.style[Qub]=b+(im(),rwb);a.d.style[Qub]=b+rwb;a.j.style[Qub]=b+rwb}
function RT(a,b){var c,d,e;e=a.u[b];d=E2(e);c=new T1(e);d+=R1(c)[1];d+=R1(c)[3];return d}
function UJ(a,b){var c;c=(Gh(),Fh).Wd(b);if(Dh(c)){return $g((JF(),a.Yc),c)}return false}
function eO(a,b){a.f=b;b?Be((JF(),a.Yc),'inversed',true):Be((JF(),a.Yc),'inversed',false)}
function vab(a,b,c,d){rab(a.i,jq(eq(KB,1),Jtb,1,5,[Neb(b),Neb(c),(Gdb(),d?true:false)]))}
function NW(a,b,c,d,e){var f;f=OW(a,b,c,true);PW(a,d,e,true)&&(f=true);if(f){cW(a);YV(a)}}
function AW(a,b,c,d,e){var f,g;EU(a);for(g=b;g<=c;g++){TW(a,g)}for(f=d;f<=e;f++){SW(a,f)}}
function vib(a,b){var c,d;ptb(b);for(d=b.$f().Oe();d.Ye();){c=d.Ze();a.put(c.gg(),c.hg())}}
function FV(a,b,c){var d;d=Sib(a.e,Ewb+b+Fwb+c);return !d?O$(a.a,b)&&R$(a.a,c):d.locked}
function nV(a){var b;b=Ewb+a.rc+Fwb+a.sc;if(PV(a,b)){return fV(a,b)}return WU(a,a.rc,a.sc)}
function rg(a){var b;if(a.b){b=a.b;a.b=null;!a.g&&(a.g=[]);zg(b,a.g)}!!a.g&&(a.g=ug(a.g))}
function mgb(a){if(a.a<54){return a.f<0?-1:a.f>0?1:0}return (!a.c&&(a.c=mhb(a.f)),a.c).e}
function Leb(a){var b,c;if(a==0){return 32}else{c=0;for(b=1;(b&a)==0;b<<=1){++c}return c}}
function _1(c){var a=[];for(var b in c){Object.hasOwnProperty.call(c,b)&&a.push(b)}return a}
function $E(a){var b,c;c=XG('logLevel');b=c==null?null:Nqb(c);b?Krb(a,b):Krb(a,(Kqb(),Gqb))}
function $I(a,b){var c;c=ph((JF(),b.Yc),hwb);jfb(lvb,c)&&(a.a=new _I(a,b),s2((og(),ng),a.a))}
function gL(a){var b,c;c=ph((JF(),a.Yc),xwb);b=(ptb(c),c);if(jfb('',c)){return null}return b}
function Kgb(a){var b;b=gq(ir,Rxb,17,a.d,15,1);Sfb(a.a,0,b,0,a.d);return new bhb(a.e,a.d,b)}
function Jg(a){var b=/function(?:\s+([\w$]+))?\s*\(/;var c=b.exec(a);return c&&c[1]||Htb}
function GH(){var b=$wnd.onresize;$wnd.onresize=Ftb(function(a){try{MG()}finally{b&&b(a)}})}
function Nf(a){sf(this);uf(this);this.backingJsObject=a;vf(this,a);this.f=a==null?Xtb:KE(a)}
function l$(a,b,c,d,e,f){this.e=a;this.f=b;this.a=c;this.b=d;this.d=e;this.c=f;sb.call(this)}
function LQ(){this.K={};this.t=[];Cp((!this.G&&(this.G=new Fp(this)),this.G),(X1(),W1),this)}
function BZ(){LQ.call(this);this.a=new TZ(this);this.k=new Wkb;this.c=new vob;this.e=null}
function icb(){icb=HE;fcb=new jcb('LEFT',0);hcb=new jcb('RIGHT',1);gcb=new jcb('MIDDLE',2)}
function Oab(a,b,c,d,e,f){rab(a.N,jq(eq(KB,1),Jtb,1,5,[Pbb(b),Neb(c),Neb(d),Neb(e),Neb(f)]))}
function GT(a,b,c){var d;a.a=b;a.b=c;(JF(),a.Yc).className=zxb;d=Ewb+b+Fwb+c;Be(a.Yc,d,true)}
function shb(a,b,c){var d,e,f;d=0;for(e=0;e<c;e++){f=b[e];a[e]=f<<1|d;d=f>>>31}d!=0&&(a[c]=d)}
function LF(a,b,c){JF();var d;d=GF;GF=a;b==IF&&$G((Gh(),a).type)==8192&&(IF=null);c.xd(a);GF=d}
function RF(a){JF();var b;b=kG(YF,a);if(!b&&!!a){(Gh(),a).stopPropagation();Fh.Xd(a)}return b}
function kY(a){if(!a.target||a.target.shadowRoot){return a.composedPath()[0]}return a.target}
function Ei(a){return a.ownerDocument.defaultView.getComputedStyle(a,'').direction=='rtl'}
function qj(a){return ((jfb(a.compatMode,avb)?a.documentElement:a.body).scrollWidth||0)|0}
function nj(a){return ((jfb(a.compatMode,avb)?a.documentElement:a.body).scrollHeight||0)|0}
function Erb(a){if(!vrb){return}Jrb(a,(Kqb(),Dqb),'invalid column index, halting parse',null)}
function gbb(a,b,c,d){rab(a.u,jq(eq(KB,1),Jtb,1,5,[(Gdb(),b?true:false),Neb(c),d?true:false]))}
function a_(a,b,c){wab(a.W,a.V.sc,a.V.rc,b);y$(a,b,true);SU(a.V);c?MR(a.Q,false):NR(a.Q,false)}
function $$(a,b,c){wab(a.W,a.V.sc,a.V.rc,b);y$(a,b,true);SU(a.V);c?OR(a.Q,false):LR(a.Q,false)}
function Ee(a,b){a.style.display=b?'':Pub;b?a.removeAttribute(Uub):a.setAttribute(Uub,'true')}
function iJ(a){var b;b=(JF(),a.Yc).selectedIndex;return b==-1?null:(eJ(a,b),Gj(a.Yc)[b].value)}
function sG(){var a;a=(FG(),BG).Ne();if(a==null||a.length==0){return ''}return yG(a.substr(1))}
function Agb(a){if(a==0){return Yfb[0]}if(a>=0&&a<dgb.length){return dgb[a]}return new pgb(0,a)}
function hV(a,b,c){if(b<=a.ob){return c<=a.Tc?a.Oc:a.c}else if(c<=a.Tc){return a.Qc}return a.zc}
function hE(a){if(Ptb<a&&a<Qtb){return a<0?$wnd.Math.ceil(a):$wnd.Math.floor(a)}return eE(Cq(a))}
function eE(a){var b;b=a.h;if(b==0){return a.l+a.m*Rtb}if(b==Stb){return a.l+a.m*Rtb-Qtb}return a}
function ye(a){var b,c;b=a.className||'';c=lfb(b,Bfb(32));if(c>=0){return b.substr(0,c)}return b}
function srb(){var a;if(!orb){orb=new rrb;a=new Prb('');Krb(a,(Kqb(),Gqb));prb(orb,a)}return orb}
function Rgb(a){var b;if(a.b==-2){if(a.e==0){b=-1}else{for(b=0;a.a[b]==0;b++);}a.b=b}return a.b}
function cib(a,b){var c,d,e;ptb(b);c=false;for(e=b.Oe();e.Ye();){d=e.Ze();c=c|a.add(d)}return c}
function sQ(a,b){var c;j2(b.a,ie(a.zf()));!!b.a&&Dj(b.a);Ej(b.a);(c=a,yj(b.a),c).tf(RA).tg();x2()}
function zQ(a){if(a.A){AM(a.A.a);a.A=null}if(a.w){AM(a.w.a);a.w=null}if(a.v){AM(a.v.a);a.v=null}}
function v5(a){if(o5){if(a.q[Ozb]||null){return oh(a.q,Ozb)}return 0}return (a.q.scrollTop||0)|0}
function fhb(a){ptb(a);if(a.length==0){throw aE(new cfb('Zero length BigInteger'))}khb(this,a)}
function Ncb(){Ncb=HE;Mcb=new Ocb('TEXT',0);Lcb=new Ocb('PREFORMATTED',1);Kcb=new Ocb('HTML',2)}
function BU(a){var b,c;for(c=new slb(a);c.a<c.c.a.length;){b=qlb(c);bh(b.d)}a.a=gq(KB,Jtb,1,0,5,1)}
function Jpb(a,b){var c,d;ptb(b);for(d=new sjb((new kjb(a)).a);d.b;){c=rjb(d);b.Df(c.gg(),c.hg())}}
function uE(a){var b,c,d,e;e=a;d=0;if(e<0){e+=Qtb;d=Stb}c=er(e/Rtb);b=er(e-c*Rtb);return oq(b,c,d)}
function K2(a){w2();var b,c;c=a.getElementsByTagName('img');for(b=0;b<c.length;b++){WF(c[b],Xvb)}}
function tg(a){if(!a.j){a.j=true;!a.f&&(a.f=new Bg(a));Ag(a.f,1);!a.i&&(a.i=new Dg(a));Ag(a.i,50)}}
function S(a){if(!a.o){return}a.u=a.p;a.n=null;a.o=false;a.p=false;if(a.q){a.q.jd();a.q=null}a.cd()}
function H2(a){w2();return (Gh(),a).type.indexOf(kxb)!=-1?Rm(a.changedTouches[0]):ai(a.clientX||0)}
function I2(a){w2();return (Gh(),a).type.indexOf(kxb)!=-1?Sm(a.changedTouches[0]):ai(a.clientY||0)}
function Xi(a,b,c,d,e,f){return (Gh(),Fh).Qd(a,mvb,true,true,0,b,c,d,e,false,false,false,false,2,f)}
function eY(a,b,c,d,e){var f;if(PV(a,Ewb+c+Fwb+e)){f=M$(a.a,c,e);c=f.col2;e=f.row2}iS(a.yc,b,c,d,e)}
function b2(a,b){gib(new Hlb(jq(eq(ir,2),Jtb,22,0,[a])));gib(new Hlb(jq(eq(ir,2),Jtb,22,0,[b])))}
function f2(){this.d=gq(ir,Rxb,17,4,15,1);this.a=gq(ir,Rxb,17,4,15,1);this.c=gq(ir,Rxb,17,4,15,1)}
function iR(a){var b;b=Xg(a.j);b?bK(a.e,a.a):s2((og(),ng),new xR(a));!!a.d&&ZQ(a.g,ZU(a.d,a.b,a.k))}
function I$(a){var b;b='';while(a>0){b=String.fromCharCode(65+(a-1)%26&bub)+b;a=(a-1)/26|0}return b}
function _D(a){var b;if(Zq(a,20)){return a}b=a&&a.__java$exception;if(!b){b=new Rf(a);Hg(b)}return b}
function Bgb(a){if(a==er(a)){return Agb(er(a))}if(a>=0){return new pgb(0,Otb)}return new pgb(0,Ytb)}
function Grb(a){if(xrb){return gq(jD,EAb,111,0,0,1)}return Vkb(a.a,gq(jD,EAb,111,a.a.a.length,0,1))}
function keb(a){if(a==null){return false}return a.$implements__java_lang_Cloneable||Array.isArray(a)}
function vI(a,b){if(a.O){throw aE(new Beb('SimplePanel can only contain one child widget'))}a.Se(b)}
function Ag(b,c){og();function d(){var a=Ftb(xg)(b);a&&$wnd.setTimeout(d,c)}
$wnd.setTimeout(d,c)}
function A2(){w2();if($wnd.document.activeElement){return $wnd.document.activeElement}return null}
function si(a){var b=a.ownerDocument.defaultView.getComputedStyle(a,null);return b.direction=='rtl'}
function Qib(a,b){var c,d;for(d=b.Oe();d.Ye();){c=d.Ze();if(pob(a,c.hg())){return true}}return false}
function wjb(a,b){var c,d;for(c=0,d=a.size();c<d;++c){if(Lpb(b,a.getAtIndex(c))){return c}}return -1}
function lf(){af();var a;!jf&&(jf=new mf);a=Qi($doc);if(!a.getContext){return null}return new kf(a)}
function De(a,b){if(!a){throw aE(new Lf(Sub))}b=zfb(b);if(b.length==0){throw aE(new zeb(Tub))}He(a,b)}
function $ob(a,b){var c;c=a.a.get(b);if(c===undefined){++a.d}else{Qob(a.a,b);--a.c;dob(a.b)}return c}
function jq(a,b,c,d,e){e.qg=a;e.rg=b;e.sg=LE;e.__elementTypeId$=c;e.__elementTypeCategory$=d;return e}
function IV(a,b,c){return c<=a.Tc&&(b>=a.bb&&b<=a.xb||b<=a.ob)||b<=a.ob&&(c>=a.db&&c<=a.zb||c<=a.Tc)}
function bS(a){return (JF(),a.Yc).style.display!=Pub||!!a.a&&le(a.a)||!!a.X&&le(a.X)||!!a.W&&le(a.W)}
function _W(a,b){a.Z=b;b?th(a.Gc,'noheaders'):eh(a.Gc,'noheaders');if(a.Db){YV(a);fY(a);NX(a);bY(a)}}
function W5(a){this.a=a;FJ.call(this,true);Ie(this,this,(fo(),fo(),eo));Ie(this,this,(Zn(),Zn(),Yn))}
function lH(a){hH();var b;b=!RF(a);if(b||!dH){return}KF(a,dH)&&((Gh(),a).stopPropagation(),undefined)}
function Ukb(a,b){var c;c=Rkb(a,b,0);if(c==-1){return false}otb(c,a.a.length);jtb(a.a,c,1);return true}
function qjb(a){if(a.a.Ye()){return true}if(a.a!=a.d){return false}a.a=new Lob(a.e.a);return a.a.Ye()}
function $F(a){JF();_G(HF);!eG&&(eG=new Bn);if(!YF){YF=new Gp(null,true);fG=new iG}return Cp(YF,eG,a)}
function Ecb(){Ecb=HE;Ccb=new Fcb('DISABLED',0);Dcb=new Fcb('MANUAL',1);Bcb=new Fcb('AUTOMATIC',2)}
function E5(a){q5();this.s=gq(ir,Rxb,17,3,15,1);this.b=gq(gr,Jtb,17,3,15,1);this.p=new wob(new Hlb(a))}
function tab(a,b,c,d,e,f,g){rab(a.f,jq(eq(KB,1),Jtb,1,5,[Neb(b),Neb(c),Neb(d),Neb(e),Neb(f),Neb(g)]))}
function LT(a,b){var c,d,e,f,g;for(e=b,f=0,g=e.length;f<g;++f){d=e[f];c=NT(d);Wg(a.c,c);Uf(a.u,c)}YT(a)}
function c3(a,b){var c,d,e,f,g,h;h=b.c;a.a[h]=b;g=b.b;for(d=g,e=0,f=d.length;e<f;++e){c=d[e];a.d[c]=h}}
function $R(a,b,c){var d,e;if(a==null||a.length<c-1){return 0}e=0;for(d=b;d<c;d++){e+=a[d-1]}return e}
function Slb(a){Rlb();var b,c,d;d=0;for(c=a.Oe();c.Ye();){b=c.Ze();d=d+(b!=null?Q(b):0);d=d|0}return d}
function xZ(a,b){var c,d;for(d=a.c.Oe();d.Ye();){c=d.Ze();b.contains(c)||F_((!a.D&&(a.D=new S0),a.D),c)}}
function Tlb(a){Rlb();var b,c,d;d=1;for(c=a.Oe();c.Ye();){b=c.Ze();d=31*d+(b!=null?Q(b):0);d=d|0}return d}
function Eq(a){var b,c,d;b=~a.l+1&Ovb;c=~a.m+(b==0?1:0)&Ovb;d=~a.h+(b==0&&c==0?1:0)&Stb;return oq(b,c,d)}
function uq(a){var b,c,d;b=~a.l+1&Ovb;c=~a.m+(b==0?1:0)&Ovb;d=~a.h+(b==0&&c==0?1:0)&Stb;a.l=b;a.m=c;a.h=d}
function zq(a,b){var c,d,e;c=a.l+b.l;d=a.m+b.m+(c>>22);e=a.h+b.h+(d>>22);return oq(c&Ovb,d&Ovb,e&Stb)}
function Kq(a,b){var c,d,e;c=a.l-b.l;d=a.m-b.m+(c>>22);e=a.h-b.h+(d>>22);return oq(c&Ovb,d&Ovb,e&Stb)}
function Okb(a,b){var c,d;c=b.toArray();d=c.length;if(d==0){return false}itb(a.a,a.a.length,c);return true}
function gJ(a){var b;b=a.text;(Gh(),a).hasAttribute(nwb)&&b.length>1&&(b=wfb(b,1,b.length-1));return b}
function rq(a,b,c,d,e){var f;f=Iq(a,b);c&&uq(f);if(e){a=tq(a,b);d?(lq=Eq(a)):(lq=oq(a.l,a.m,a.h))}return f}
function vq(a){var b,c;c=Keb(a.h);if(c==32){b=Keb(a.m);return b==32?Keb(a.l)+32:b+20-10}else{return c-12}}
function mZ(a){a.c==0?pab(a.b,a.a):a.c==1?qab(a.b,a.a):oab(a.b,a.a);fN(y1(a.g.a.a.F),false);TU(a.d.V,true)}
function O(a){return cr(a)?QB:_q(a)?xB:$q(a)?vB:Yq(a)?a.qg:iq(a)?a.qg:a.qg||Array.isArray(a)&&eq(Js,1)||Js}
function SS(a,b){a.i.style[nvb]=(b?(Ak(),pk):(Ak(),hk)).ke();a.g.style[nvb]=(b?(Ak(),pk):(Ak(),hk)).ke()}
function KS(a,b){Ee((JF(),a.Yc),b);b?(a.Yc.style[uwb]='',undefined):(a.Yc.style[uwb]=(rl(),Bvb),undefined)}
function wI(a,b){if(a.O!=b){return false}try{Qe(b,null)}finally{ah(a.Re(),(JF(),b.Yc));a.O=null}return true}
function Mdb(a){if(jfb(typeof(a),Ntb)){return true}return a!=null&&a.$implements__java_lang_CharSequence}
function Udb(){++Qdb;this.k=null;this.i=null;this.g=null;this.d=null;this.b=null;this.j=null;this.a=null}
function l3(){this.a={};this.d={};this.c=new O4;this.b=new Wkb;c3(this,new m3(jq(eq(QB,1),_tb,2,6,[Myb])))}
function sjb(a){this.e=a;this.d=new bpb(this.e.b);this.a=this.d;this.b=qjb(this);this.$modCount=a.$modCount}
function rob(a){ltb(a>=0,'Negative initial capacity');ltb(true,'Non-positive load factor');Yib(this)}
function aM(){return function(a){var b=this.parentNode;b.onfocus&&$wnd.setTimeout(function(){b.focus()},0)}}
function dE(a,b){var c;if(jE(a)&&jE(b)){c=a-b;if(!isNaN(c)){return c}}return Bq(jE(a)?uE(a):a,jE(b)?uE(b):b)}
function Mhb(a,b,c){var d;for(d=c-1;d>=0&&a[d]===b[d];d--);return d<0?0:kE(cE(a[d],yAb),cE(b[d],yAb))?-1:1}
function Eob(a,b){var c,d,e,f;for(d=b,e=0,f=d.length;e<f;++e){c=d[e];if(pob(a,c.gg())){return c}}return null}
function eib(a,b){var c,d;ptb(b);for(d=b.Oe();d.Ye();){c=d.Ze();if(!a.contains(c)){return false}}return true}
function iN(a,b){var c,d;XJ(a);for(d=new slb(a.s);d.a<d.c.a.length;){c=qlb(d);mZ(c)}a.s.a=gq(KB,Jtb,1,0,5,1)}
function Pp(a){var b,c;if(a.a){try{for(c=new slb(a.a);c.a<c.c.a.length;){b=qlb(c);b.Ld()}}finally{a.a=null}}}
function P5(a,b,c){if(a.b){a.a=new E5(jq(eq(Js,1),Jtb,0,2,[]));Ie(b,a,($o(),$o(),Zo))}else{a.a=null}Q5(a,c)}
function Ck(){Ak();return jq(eq(wt,1),Jtb,23,0,[pk,hk,kk,lk,nk,ok,qk,rk,sk,vk,xk,wk,zk,tk,uk,yk,jk,ik,mk])}
function OI(){OI=HE;new QI((Nl(),'center'));new QI('justify');MI=new QI(jwb);new QI('right');NI=MI;LI=NI}
function im(){im=HE;hm=new lm;fm=new mm;am=new nm;bm=new om;gm=new pm;em=new qm;cm=new rm;_l=new sm;dm=new tm}
function Vrb(){Vrb=HE;Srb=new Wrb('CONCURRENT',0);Trb=new Wrb('IDENTITY_FINISH',1);Urb=new Wrb('UNORDERED',2)}
function rb(a,b){if(b<=0){throw aE(new zeb('must be positive'))}!!a.j&&pb(a);a.i=true;a.j=Neb(xb(vb(a,a.g),b))}
function Pe(a,b){a.Uc&&(JF(),a.Yc.__listener=null,undefined);!!a.Yc&&ne(a.Yc,b);a.Yc=b;a.Uc&&(JF(),bH(a.Yc,a))}
function V4(a,b,c){var d=a[c];if(d!==undefined){var e=function(){};e.prototype=d;a[b]=new e}else{a[b]={}}}
function ib(b,c){var d=Ftb(function(){var a=Xf();b.gd(a)});var e=$wnd.requestAnimationFrame(d,c);return {id:e}}
function XV(a,b,c){var d;Wg(a.zc,a.hb);vh(a.hb,'cell '+b);yh(a.hb,c);d=a.hb.clientWidth|0;bh(a.hb);return d}
function B_(a,b){var c,d;if(a.u.f){jP(a.u);yX(a.V,false)}c=qh(a.V.zc);d=(a.V.zc.scrollTop||0)|0;Jbb(a.W,b,c,d)}
function OV(a,b){var c,d;if(a.S){d=kY(b);c=ie(a.S);return (Gh(),Fh).ee(c,d)||!!Kh(c)&&$g(Kh(c),d)}return false}
function RS(a,b){a.b=b;a.a.style[ovb]=(b?(wm(),um):(wm(),vm)).ke();a.d.style[ovb]=(b?(wm(),um):(wm(),vm)).ke()}
function US(a,b){a.n=b;a.k.style[ovb]=(b?(wm(),um):(wm(),vm)).ke();a.p.style[ovb]=(b?(wm(),um):(wm(),vm)).ke()}
function XS(a,b){a.v=b;a.u.style[ovb]=(b?(wm(),um):(wm(),vm)).ke();a.A.style[ovb]=(b?(wm(),um):(wm(),vm)).ke()}
function $S(a,b){a.H=b;a.G.style[ovb]=(b?(wm(),um):(wm(),vm)).ke();a.J.style[ovb]=(b?(wm(),um):(wm(),vm)).ke()}
function pN(a,b){a.style[jwb]=b.b+(im(),rwb);a.style[kwb]=b.c+rwb;a.style[Rub]=b.d+rwb;a.style[Qub]=b.a+rwb}
function IL(a,b){var c;if(b<0||b>=a.c){throw aE(new Ddb)}--a.c;for(c=b;c<a.c;++c){a.a[c]=a.a[c+1]}a.a[a.c]=null}
function lP(a,b){var c,d;d=Gj(hJ(a.B)).length;for(c=0;c<d;c++){if(jfb(fJ(a.B,c),b)){lJ(a.B,c);return}}lJ(a.B,0)}
function LU(a){var b,c,d;for(d=1;d<=a.Tc;d++){for(c=1;c<=a.ob;c++){b=new UM(a,c,d);Wg(a.Oc,b.d);Nkb(a.Nc,b)}}}
function YX(a){var b,c;for(c=new sjb((new kjb(a.Kb)).a);c.b;){b=rjb(c);lW(a,b.gg(),b.hg());mW(a,b.gg(),b.hg())}}
function tgb(a){var b;dE(a,0)<0&&(a=eE(Fq(jE(a)?uE(a):a)));return b=wE(rE(a,32)),64-(b!=0?Keb(b):Keb(wE(a))+32)}
function Hf(a){var b;if(a!=null){b=a.__java$exception;if(b){return b}}return br(a,TypeError)?new _eb(a):new Nf(a)}
function igb(a){var b,c;b=mgb(a);c=a.a-a.e/xAb;c<-149||b==0?(b*=0):c>129?(b*=Infinity):(b=oeb(ngb(a)));return b}
function mU(a,b,c){var d,e,f,g;g=0;for(d=b;d<=c;d++){e=N$(a.a,d);f=kgb(zgb(e*a.Lb/72));g+=f;a.W[d-1]=f}return g}
function oJ(a,b,c){var d;if(a.i){d=(JF(),cj($doc));PF(a.d,d,b);Wg(d,TF(c))}else{d=MF(a.d);JF();HF.Ie(d,TF(c),b)}}
function MG(){FG();var a,b;if(EG){b=lj($doc);a=kj($doc);if(DG!=b||CG!=a){DG=b;CG=a;tp((!AG&&(AG=new YG),AG))}}}
function TX(a,b){if(a.R);else{iL(a.sb,b);a._&&(EV(a,a.rc,a.sc)||QW(a,a.rc,a.sc),s2((og(),ng),new xY(a,false)))}}
function xI(a,b){if(b==a.O){return}!!b&&Oe(b);!!a.O&&wI(a,a.O);a.O=b;if(b){JF();Wg(a.Re(),TF(ie(a.O)));Qe(b,a)}}
function fK(a){if(a.J){AM(a.J.a);a.J=null}if(a.D){AM(a.D.a);a.D=null}if(a.M){a.J=$F(new uK(a));a.D=rG(new wK(a))}}
function Be(a,b,c){if(!a){throw aE(new Lf(Sub))}b=zfb(b);if(b.length==0){throw aE(new zeb(Tub))}c?eh(a,b):th(a,b)}
function mtb(a,b){if(0>a){throw aE(new zeb('fromIndex: 0 > toIndex: '+a))}if(a>b){throw aE(new Fdb(iub+a+jub+b))}}
function qb(a,b){if(b<0){throw aE(new zeb('must be non-negative'))}!!a.j&&pb(a);a.i=false;a.j=Neb(yb(vb(a,a.g),b))}
function mhb(a){Igb();if(a<0){if(a!=-1){return new _gb(-1,-a)}return Cgb}else return a<=10?Egb[er(a)]:new _gb(1,a)}
function bE(a,b){var c;if(jE(a)&&jE(b)){c=a+b;if(Ptb<c&&c<Qtb){return c}}return eE(zq(jE(a)?uE(a):a,jE(b)?uE(b):b))}
function nE(a,b){var c;if(jE(a)&&jE(b)){c=a*b;if(Ptb<c&&c<Qtb){return c}}return eE(Dq(jE(a)?uE(a):a,jE(b)?uE(b):b))}
function tE(a,b){var c;if(jE(a)&&jE(b)){c=a-b;if(Ptb<c&&c<Qtb){return c}}return eE(Kq(jE(a)?uE(a):a,jE(b)?uE(b):b))}
function M(a,b){return cr(a)?jfb(a,b):_q(a)?qeb(a,b):$q(a)?(ptb(a),dr(a)===dr(b)):Yq(a)?a.Zc(b):iq(a)?J(a,b):nf(a,b)}
function xW(a,b){var c,d;c=b.b;d=b.k;Xib(a.Cc,Ewb+c+Fwb+d);sW(a,b);c>=a.bb&&c<=a.xb&&d>=a.db&&d<=a.zb&&KM(WU(a,c,d))}
function y2(a,b){w2();var c=$wnd.document.elementFromPoint(a,b);c!=null&&c.nodeType==3&&(c=c.parentNode);return c}
function G5(a){var b,c,d,e;b=a.childNodes;e=new Wkb;for(c=0;c<b.length;c++){d=b[c];d.nodeType==1&&Nkb(e,d)}return e}
function Tgb(a){var b;if(a.c!=0){return a.c}for(b=0;b<a.a.length;b++){a.c=a.c*33+(a.a[b]&-1)}a.c=a.c*a.e;return a.c}
function rJ(a,b){var c,d;for(d=new slb(a.f);d.a<d.c.a.length;){c=qlb(d);if($g((JF(),c.Yc),b)){return c}}return null}
function xdb(c){var a=[];for(var b in c){Object.prototype.hasOwnProperty.call(c,b)&&b!='$H'&&a.push(b)}return a}
function Np(a,b,c){var d,e;e=Rib(a.d,b);if(!e){e=new qob;Uib(a.d,b,e)}d=e.get(c);if(!d){d=new Wkb;e.put(c,d)}return d}
function b7(a){var b,c;if(a==null||a.length==0||jfb(Xtb,a)){return null}c=vdb(a);b=new eR;Object.assign(b,c);return b}
function z4(a){var b,c;b=P4(a);c=b.If(null,jq(eq(KB,1),Jtb,1,5,[]));Zq(c,100)&&d3((!b3&&(b3=new l3),b3),a.a);return c}
function D2(a){w2();var b,c;c=B2(a);if((F1(),!E1&&(E1=new O1),F1(),E1).a.j){b=C2(a);if(b>c&&b<=c+1){return b}}return c}
function G2(a){w2();var b,c;c=E2(a);if((F1(),!E1&&(E1=new O1),F1(),E1).a.j){b=F2(a);if(b>c&&b<=c+1){return b}}return c}
function Yp(a){var b;b=ph(a,'dir');if(kfb('rtl',b)){return bq(),aq}else if(kfb('ltr',b)){return bq(),_p}return bq(),$p}
function Neb(a){var b,c;if(a>-129&&a<128){b=a+128;c=(Peb(),Oeb)[b];!c&&(c=Oeb[b]=new Eeb(a));return c}return new Eeb(a)}
function zgb(a){fgb();if(!isNaN(a)&&!isFinite(a)||isNaN(a)){throw aE(new cfb('Infinite or NaN'))}return new qgb(''+a)}
function Jb(a,b){var c,d,e,f,g;c=new Mfb;for(e=b,f=0,g=e.length;f<g;++f){d=e[f];Kfb(Kfb(c,a.md(d)),' ')}return zfb(c.a)}
function BL(a,b){var c,d,e;d=(JF(),cj($doc));c=(e=bj($doc),fI(e,a.a),gI(e,a.b),e);Wg(d,TF(c));Wg(a.c,TF(d));OH(a,b,c)}
function aK(a,b,c){var d;a.H=b;a.N=c;b-=ij($doc);c-=jj($doc);d=(JF(),a.Yc);d.style[jwb]=b+(im(),rwb);d.style[kwb]=c+rwb}
function Op(a,b,c){var d,e;e=Rib(a.d,b);if(!e){return Rlb(),Rlb(),Olb}d=e.get(c);if(!d){return Rlb(),Rlb(),Olb}return d}
function Aab(a,b,c,d,e,f){var g;rab(a.q,jq(eq(KB,1),Jtb,1,5,[(g=[],Jpb(b,new Sbb(g)),g),Neb(c),Neb(d),Neb(e),Neb(f)]))}
function zlb(a,b,c,d,e,f,g){var h;h=c;while(f<g){h>=d||b<c&&$nb(a[b],a[h])<=0?(e[f++]=a[b++]):(e[f++]=a[h++])}}
function ylb(a,b,c){var d,e,f;for(d=b+1;d<c;++d){for(e=d;e>b&&$nb(a[e-1],a[e])>0;--e){f=a[e];a[e]=a[e-1];a[e-1]=f}}}
function Whb(a,b,c,d,e){if(b==0||d==0){return}b==1?(e[d]=Yhb(e,c,d,a[0])):d==1?(e[b]=Yhb(e,a,b,c[0])):Xhb(a,c,e,b,d)}
function T0(a){var b;b=oj($doc);return w2(),(Gh(),a).type.indexOf(kxb)!=-1?Rm(a.changedTouches[0])+b:ai(a.clientX||0)+b}
function U0(a){var b;b=pj($doc);return w2(),(Gh(),a).type.indexOf(kxb)!=-1?Sm(a.changedTouches[0])+b:ai(a.clientY||0)+b}
function j0(a,b){a.D=b;a.D?((JF(),a.Yc).className||'').indexOf(wyb)!=-1||Be(a.Yc,wyb,true):Be((JF(),a.Yc),wyb,false)}
function E0(a,b){b!=null&&b.length!=0?((JF(),a.Yc).style[Rub]=b,undefined):((JF(),a.Yc).style[Rub]='500.0px',undefined)}
function $_(a,b){b!=null&&b.length!=0?((JF(),a.Yc).style[Qub]=b,undefined):((JF(),a.Yc).style[Qub]='400.0px',undefined)}
function XP(a){mL();oL.call(this);this.a=a;this.Vc==-1?WF((JF(),this.Yc),Pvb|(this.Yc.__eventBits||0)):(this.Vc|=Pvb)}
function Cn(a,b){var c;Bn.call(this);this.a=b;!Ym&&(Ym=new Bo);c=zo(Ym,a);if(!c){c=new Wkb;Ao(Ym,a,c)}c.add(this);this.b=a}
function Me(a,b){var c;switch(JF(),$G((Gh(),b).type)){case 16:case 32:c=Fh.Vd(b);if(!!c&&$g(a.Yc,c)){return}}_m(b,a,a.Yc)}
function Ogb(a,b){var c;if(dr(a)===dr(b)){return true}if(Zq(b,11)){c=b;return a.e==c.e&&a.d==c.d&&Pgb(a,c.a)}return false}
function seb(a,b){if(a<b){return -1}if(a>b){return 1}if(a==b){return a==0?seb(1/a,1/b):0}return isNaN(a)?isNaN(b)?0:1:-1}
function esb(a){if(a.b){esb(a.b)}else if(a.c){throw aE(new Beb("Stream already terminated, can't be modified or used"))}}
function xgb(a){if(a<Ytb){throw aE(new Cdb('Overflow'))}else if(a>Otb){throw aE(new Cdb('Underflow'))}else{return er(a)}}
function wZ(a){wQ(a);C$((!a.D&&(a.D=new S0),a.D),true);!!a.c&&a.c.clear();a.k.a=gq(KB,Jtb,1,0,5,1);!!a.b&&AM(a.b.a)}
function U$(a){var b;if(!RV(a.V)&&!a.e&&!!a.p&&d$(a.p,oV(a.V))){b=c$(a.p,oV(a.V));if(b){a.o=true;dP(a.u,false);NU(a.V,b)}}}
function KE(a){var b;if(Array.isArray(a)&&a.sg===LE){return Sdb(O(a))+'@'+(b=Q(a)>>>0,b.toString(16))}return a.toString()}
function UK(){QK();var a;a=Rib(OK,null);if(a){return a}Zib(OK)==0&&GG(new YK);a=new $K;Uib(OK,null,a);sob(PK,a);return a}
function Zob(a,b,c){var d;d=a.a.get(b);a.a.set(b,c===undefined?null:c);if(d===undefined){++a.c;dob(a.b)}else{++a.d}return d}
function Nhb(a,b,c){var d,e;d=cE(c,yAb);for(e=0;dE(d,0)!=0&&e<b;e++){d=bE(d,cE(a[e],yAb));a[e]=wE(d);d=rE(d,32)}return wE(d)}
function Ihb(a,b,c){var d,e,f,g;f=0;for(d=b-1;d>=0;d--){g=bE(qE(f,32),cE(a[d],yAb));e=Ehb(g,c);f=wE(rE(e,32))}return wE(f)}
function NY(a,b,c,d,e){var f,g,h;for(g=b;g<=c;g++){for(h=d;h<=e;h++){f=WU(a.a,h,g);!!f&&f.o!=null&&f.o.length!=0&&f.g&&HM(f)}}}
function OY(a,b,c,d,e){var f,g,h;for(g=b;g<=c;g++){for(h=d;h<=e;h++){f=WU(a.a,h,g);!!f&&f.o!=null&&f.o.length!=0&&f.g&&TM(f)}}}
function x_(a,b,c,d,e){var f;f=TP(a.I,d,e,b,c);if(f.col1==b&&f.col2==c&&f.row1==d&&f.row2==e){Qab(a.W,d,b,e,c);qb(a.s,200)}}
function hQ(a,b,c){var d;d=sfb((Rdb(b),b.k),$tb,'.');!a.J&&(a.J={});null==a.J[d]&&(a.J[d]=new Wkb,undefined);a.J[d].add(c)}
function QM(a,b,c,d){a.c=b;a.k=c;a.b=!d?'cs0':d.cellStyle;a.o=!d?null:d.value;a.f=!!d&&d.needsMeasure;SM(a);RM(a);a.g=true}
function tZ(a,b,c,d){var e,f;e=ET(c,ph(a.f,'appId'));f=new fZ('custom-component-'+c,e,a.f);d==null?Hob(b.a,null,f):Zob(b.b,d,f)}
function A$(a){var b,c;if(a.e){if(!a.K){return}c=new h1(a);qb(c,evb);a.K=false;p2(a);b=O2(py);rab(b.J,jq(eq(KB,1),Jtb,1,5,[]))}}
function E_(a,b){var c,d;if(!b||b.a.c+b.b.c==0){return}for(d=new sjb((new kjb(b)).a);d.b;){c=rjb(d);!!c&&uW(a.V,c.gg(),c.hg())}}
function mE(a,b){var c;if(jE(a)&&jE(b)){c=a%b;if(Ptb<c&&c<Qtb){return c}}return eE((pq(jE(a)?uE(a):a,jE(b)?uE(b):b,true),lq))}
function lgb(a,b){var c;a.c=b;a.a=ohb(b);a.a<54&&(a.f=(c=b.d>1?pE(qE(b.a[1],32),cE(b.a[0],yAb)):cE(b.a[0],yAb),vE(nE(b.e,c))))}
function C5(a,b){var c,d,e;for(d=new slb(a.g);d.a<d.c.a.length;){c=qlb(d);e=c.style;e[Rwb]='translate3d(0px,'+b+'px,0px)'}}
function zJ(a){var b,c;if(!a.g){for(c=new slb(a.f);c.a<c.c.a.length;){b=qlb(c);if(b.b){AJ(a,b);break}}return true}return false}
function dib(a,b,c){var d,e;for(e=a.Oe();e.Ye();){d=e.Ze();if(dr(b)===dr(d)||b!=null&&M(b,d)){c&&e.$e();return true}}return false}
function C4(a,b){var c,d;d=a;if(b!=null&&b.length!=0){d+='<';for(c=0;c<b.length;c++){c!=0&&(d+=',');d+=''+b[c]}d+='>'}return d}
function BT(a){var b=a.length;var c=0;var d=0;var e=0;while(c<b){d=a.charCodeAt(c);d>47&&d<58&&(e=e*10+d-48);c++}return e}
function Frb(a){var b,c;if(a.b){return a.b}c=xrb?null:a.d;while(c){b=xrb?null:c.b;if(b){return b}c=xrb?null:c.d}return Kqb(),Gqb}
function E2(a){w2();if(a.getBoundingClientRect){var b=a.getBoundingClientRect();return b.right-b.left}else{return a.offsetWidth}}
function qdb(){this.b=(Ecb(),Ccb);this.c=new qob;this.c.put('transport',(gdb(),edb).a);this.c.put('fallbackTransport',cdb.a)}
function qq(a,b){if(a.h==Pvb&&a.m==0&&a.l==0){b&&(lq=oq(0,0,0));return nq((Tq(),Rq))}b&&(lq=oq(a.l,a.m,a.h));return oq(0,0,0)}
function vW(a){var b,c,d;for(c=new slb(a);c.a<c.c.a.length;){b=qlb(c);d=Kh((Gh(),b));!!d&&d.removeChild(b)}a.a=gq(KB,Jtb,1,0,5,1)}
function ihb(a){var b,c,d;if(a<Ggb.length){return Ggb[a]}c=a>>5;b=a&31;d=gq(ir,Rxb,17,c+1,15,1);d[c]=1<<b;return new bhb(1,c+1,d)}
function ohb(a){var b,c,d;if(a.e==0){return 0}b=a.d<<5;c=a.a[a.d-1];if(a.e<0){d=Rgb(a);if(d==a.d-1){--c;c=c|0}}b-=Keb(c);return b}
function aT(a){var b,c;c=K$(a.F.q,a.e);for(b=a.e+1;b<=a.f;b++){c+=K$(a.F.q,b)}a.G.style[Rub]=c+1+(im(),rwb);a.a.style[Rub]=c+1+rwb}
function yK(a){if(!a.i){xK(a);a.c||UH((QK(),UK()),a.a)}(TJ(),SJ).ff(ie(a.a),'rect(auto, auto, auto, auto)');ie(a.a).style[uwb]=Avb}
function stb(a,b,c){if(a<0||b>c){throw aE(new Edb(mub+a+nub+b+', size: '+c))}if(a>b){throw aE(new zeb(mub+a+' > toIndex: '+b))}}
function gT(a,b){switch(JF(),$G((Gh(),b).type)){case Rtb:case bwb:_V(a.c,b);case 8:case 8192:BX(a.c,b);break;case 64:$V(a.c,b);}}
function GR(a,b,c){var d;d=L$(a.d,b,c);if(d){a.a=b;a.b=c;b=d.col1;c=d.row1}else{a.a=0;a.b=0}QW(a.c,b,c);QR(a,b,c,(ZU(a.c,b,c),d))}
function MZ(a,b){var c,d;if(a.a.g){c=(w2(),H2(a.a.g));d=I2(a.a.g)}else{c=(w2(),H2(a.a.i));d=I2(a.a.i)}r$(y1(a.a.F),new VZ(a,b),c,d)}
function qJ(a,b,c){var d;if(!b.b){return}AJ(a,b);if(c&&!!b.a){AJ(a,null);(FI(),EI)._e((JF(),a.Yc));d=b.a;wg((og(),ng),new JJ(d))}}
function PH(a,b){var c;if(b.Xc!=a){return false}try{Qe(b,null)}finally{c=(JF(),b.Yc);ah((null,Kh((Gh(),c))),c);JL(a.o,b)}return true}
function xlb(a){var b,c,d,e,f;if(a==null){return 0}f=1;for(c=a,d=0,e=c.length;d<e;++d){b=c[d];f=31*f+(b!=null?Q(b):0);f=f|0}return f}
function Oh(a){var b=0;var c=a;while(c.offsetParent){b-=c.scrollTop;c=c.parentNode}while(a){b+=a.offsetTop;a=a.offsetParent}return b}
function NJ(){NJ=HE;BF();new xF('data:image/gif;base64,R0lGODlhBQAJAIAAAAAAAAAAACH5BAEAAAEALAAAAAAFAAkAAAIMRB5gp9v2YlJsJRQKADs=')}
function _$(a){var b;if(!a.B&&!a.u.f){a.B=true;a.c=true;if(a.t){a.t=false}else{uX(a.V,false,(b=gL(a.u.j),b==null?'':b));hP(a.u)}}}
function Kqb(){Kqb=HE;Bqb=new Pqb;Cqb=new Sqb;Dqb=new Vqb;Eqb=new Yqb;Fqb=new _qb;Gqb=new crb;Hqb=new frb;Iqb=new irb;Jqb=new lrb}
function KT(a,b){yI.call(this);a.rd('100%');a.pd('100%');(JF(),this.Yc).style[Gwb]=Pub;a.Yc.style[Gwb]='all';vI(this,a);JT(this,b)}
function d6(){JI.call(this);(JF(),this.Yc).className='v-label';this.Vc==-1?WF(this.Yc,241|(this.Yc.__eventBits||0)):(this.Vc|=241)}
function GV(a,b,c){return b>=a.bb&&b<=a.xb&&c>=a.db&&c<=a.zb||b<=a.ob&&c<=a.Tc||b>a.ob&&b<=a.xb&&c<=a.Tc||c>a.Tc&&c<=a.zb&&b<=a.ob}
function bV(a){if(a.V==-1){if(a.Lb==0){Yg(a.Mb)&&(a.Lb=(a.Mb.offsetWidth||0)|0);a.Lb==0&&(a.Lb=96)}a.V=er(a.a.r*a.Lb/72)}return a.V}
function _R(a){if(LV(a.Q,a.e,a.K)){return ie(a.X)}if(HV(a.Q,a.e,a.K)){return ie(a.a)}if(KV(a.Q,a.e,a.K)){return ie(a.W)}return ie(a.b)}
function Z0(a,b,c){var d,e;if(a.a.J){for(e=new slb(a.a.J);e.a<e.c.a.length;){d=qlb(e);if(d.col1==b&&d.row1==c){return d}}}return null}
function ZX(a,b){var c,d;if(b){WV(a.kc);WV(a.Rc);WV(a.d);for(d=new slb(a.Nc);d.a<d.c.a.length;){c=qlb(d);!!c&&(c.g=true)}}qb(a.Jb,20)}
function WV(a){var b,c,d,e;for(e=new slb(a);e.a<e.c.a.length;){d=qlb(e);for(c=new slb(d);c.a<c.c.a.length;){b=qlb(c);!!b&&(b.g=true)}}}
function qhb(a,b){var c,d,e,f;c=b>>5;b&=31;e=a.d+c+(b==0?0:1);d=gq(ir,Rxb,17,e,15,1);rhb(d,a.a,c,b);f=new bhb(a.e,e,d);Lgb(f);return f}
function heb(a,b){var c=0;while(!b[c]||b[c]==''){c++}var d=b[c++];for(;c<b.length;c++){if(!b[c]||b[c]==''){continue}d+=a+b[c]}return d}
function Nh(a){var b=0;var c=a;while(c.offsetParent){b-=c.scrollLeft;c=c.parentNode}while(a){b+=a.offsetLeft;a=a.offsetParent}return b}
function B2(a){var b;if(a.getBoundingClientRect!=null){var c=a.getBoundingClientRect();b=c.bottom-c.top}else{b=a.offsetHeight}return b}
function E$(a,b,c,d){var e;e=new Ffb;Efb(e,$wnd.Math.abs(d-c)+1);e.a+='R';e.a+=' x ';Efb(e,$wnd.Math.abs(b-a)+1);e.a+='C';return e.a}
function Dfb(a,b,c){var d,e,f,g;f=b+c;utb(b,f,a.length);g='';for(e=b;e<f;){d=$wnd.Math.min(e+10000,f);g+=Afb(a.slice(e,d));e=d}return g}
function Mp(a,b,c,d){var e,f,g;e=Op(a,b,c);f=e.remove(d);f&&e.isEmpty()&&(g=Rib(a.d,b),g.remove(c),g.isEmpty()&&Wib(a.d,b),undefined)}
function Mg(){if(Error.stackTraceLimit>0){$wnd.Error.stackTraceLimit=Error.stackTraceLimit=64;return true}return 'stack' in new Error}
function Jgb(a,b){if(a.e>b.e){return 1}if(a.e<b.e){return -1}if(a.d>b.d){return a.e}if(a.d<b.d){return -b.e}return a.e*Mhb(a.a,b.a,a.d)}
function nhb(a){Igb();if(dE(a,0)<0){if(dE(a,-1)!=0){return new chb(-1,oE(a))}return Cgb}else return dE(a,10)<=0?Egb[wE(a)]:new chb(1,a)}
function Xeb(a){var b,c;if(dE(a,-129)>0&&dE(a,128)<0){b=wE(a)+128;c=(Zeb(),Yeb)[b];!c&&(c=Yeb[b]=new Qeb(a));return c}return new Qeb(a)}
function dg(){var a;if(Yf!=0){a=Xf();if(a-_f>2000){_f=a;ag=$wnd.setTimeout(mg,10)}}if(Yf++==0){pg((og(),ng));return true}return false}
function aV(a){var b;b=new Wkb;!!a.S&&Nkb(b,a.S);Okb(b,new mkb(a.Bc));!!a.T&&Okb(b,new mkb(a.T));!!a.Cc&&Okb(b,new mkb(a.Cc));return b}
function CU(a){var b,c,d,e;vT(a.Fb);for(d=(e=(new mkb(a.Eb)).a.$f().Oe(),new rkb(e));d.a.Ye();){c=(b=d.a.Ze(),b.hg());bh(c.d)}Yib(a.Eb)}
function VU(a){var b,c,d;d=a.zb;b=gh(a.zc);for(c=a.kc.a.length-1;c>0;c--){if(gh(Qkb(Qkb(a.kc,c),0).d)<=b){return d}else{--d}}return a.zb}
function cV(a,b){var c,d;d=0;for(c=new slb(b);c.a<c.c.a.length;){qlb(c);if(!P$(a.a,d+1)){return otb(d,b.a.length),b.a[d]}++d}return null}
function Vkb(a,b){var c,d;d=a.a.length;b.length<d&&(b=ktb(new Array(d),b));for(c=0;c<d;++c){b[c]=a.a[c]}b.length>d&&(b[d]=null);return b}
function Glb(a,b){var c,d;d=a.a.length;b.length<d&&(b=ktb(new Array(d),b));for(c=0;c<d;++c){b[c]=a.a[c]}b.length>d&&(b[d]=null);return b}
function R1(a){var b;b=jq(eq(ir,1),Rxb,17,15,[0,0,0,0]);b[0]=Q1(a,Lwb);b[1]=Q1(a,Cxb);b[2]=Q1(a,'marginBottom');b[3]=Q1(a,Kwb);return b}
function xeb(a){var b;b=oeb(a);if(b>3.4028234663852886E38){return Infinity}else if(b<-3.4028234663852886E38){return -Infinity}return b}
function Pdb(a){if(a>=48&&a<48+$wnd.Math.min(10,10)){return a-48}if(a>=97&&a<97){return a-97+10}if(a>=65&&a<65){return a-65+10}return -1}
function Jrb(a,b,c,d){(vrb?b.Xf()>=Frb(a).Xf():wrb?b.Xf()>=(Kqb(),800):zrb?b.Xf()>=(Kqb(),900):yrb&&b.Xf()>=(Kqb(),evb))&&Brb(a,b,c,d)}
function Irb(a,b,c){(vrb?b.Xf()>=Frb(a).Xf():wrb?b.Xf()>=(Kqb(),800):zrb?b.Xf()>=(Kqb(),900):yrb&&b.Xf()>=(Kqb(),evb))&&Brb(a,b,c,null)}
function xf(a,b,c){var d,e,f,g,h;for(e=(a.i==null&&(a.i=(Gg(),h=Fg.Nd(a),Ig(h))),a.i),f=0,g=e.length;f<g;++f){d=e[f];b.De(c+'\tat '+d)}}
function aS(a,b,c){var d,e,f,g;g=y2(b,c);if(g){d=(Gh(),g).getAttribute($ub)||'';AT(a.Q.wb,d);e=a.Q.wb.a;f=a.Q.wb.b;e!=0&&f!=0&&tS(a,e,f)}}
function Vcb(){Vcb=HE;Scb=new Wcb('INFO',0);Ucb=new Wcb(tAb,1);Rcb=new Wcb('ERROR',2);Qcb=new Wcb('CRITICAL',3);Tcb=new Wcb('SYSTEM',4)}
function leb(a){var b;b=typeof(a);if(jfb(b,Ltb)||jfb(b,Mtb)||jfb(b,Ntb)){return true}return a!=null&&a.$implements__java_lang_Comparable}
function kfb(a,b){ptb(a);if(b==null){return false}if(jfb(a,b)){return true}return a.length==b.length&&jfb(a.toLowerCase(),b.toLowerCase())}
function ET(a,b){return $wnd.Vaadin&&$wnd.Vaadin.Flow&&$wnd.Vaadin.Flow.clients[b]&&$wnd.Vaadin.Flow.clients[b].getByNodeId(parseInt(a))}
function pR(a,b,c){var d;d=a.e.r;!!d&&Zq(d,160)&&_X(d,a,a.k,a.b,b,c);th((JF(),a.Yc),'c'+a.b+'r'+a.k);a.b=c;a.k=b;eh(a.Yc,'c'+a.b+'r'+a.k)}
function wcb(){wcb=HE;var b;vcb='8.27.7';b=ufb(vcb,'[-.]',4);peb(b[0]);peb(b[1]);try{peb(b[2])}catch(a){a=_D(a);if(!Zq(a,49))throw aE(a)}}
function qZ(a,b){var c,d;for(d=b.keySet().Oe();d.Ye();){c=d.Ze();a.c.contains(c)?N0((!a.D&&(a.D=new S0),a.D),c,b.get(c)):rZ(a,c,b.get(c))}}
function V$(a,b){var c,d;a.A.length>b?(c=a.A[b]):(c=0);a.$.length>b?(d=a.$[b]):(d=0);EW(a.V,c,d);(c!=0||d!=0)&&s2((og(),ng),new d1(a,c,d))}
function Yhb(a,b,c,d){Uhb();var e,f;e=0;for(f=0;f<c;f++){e=bE(nE(cE(b[f],yAb),cE(d,yAb)),cE(wE(e),yAb));a[f]=wE(e);e=sE(e,32)}return wE(e)}
function gS(a,b,c,d,e){a.G=b;a.I=d;a.H=c;a.J=e;IS(a.B,b,c,d,e);a.ab>0&a.r>0&&IS(a.D,b,c,d,e);a.ab>0&&IS(a.F,b,c,d,e);a.r>0&&IS(a.A,b,c,d,e)}
function yX(a,b){a._=false;a.ab=false;CT(a.$,Oxb,0);cf(a.sb,false);iL(a.sb,'');ve(a.sb,'0');qe(a.sb,'');re(a.sb,'');b&&s2((og(),ng),new BY(a))}
function iT(a,b,c,d,e){JF();HF.Me(b,yxb);b.__listener=a;HF.Me(c,yxb);c.__listener=a;HF.Me(d,yxb);d.__listener=a;HF.Me(e,yxb);e.__listener=a}
function aY(a,b,c){var d,e,f;for(e=new slb(b);e.a<e.c.a.length;){d=qlb(e);f=Ewb+d.c+Fwb+d.k;!!c&&Xob(c.b,f)?NM(d):!!a.r&&Tib(a.r,f)&&IM(d)}}
function AU(a){var b,c,d,e;for(c=(e=(new bkb(a.t.a)).a.$f().Oe(),new hkb(e));c.a.Ye();){b=(d=c.a.Ze(),d.gg());th(b.d,Gxb)}Yib(a.t.a);Yib(a.u.a)}
function XY(a,b){var c;c=Kh((Gh(),b))?zT(kh(Kh(b))):0;if(a.a.bc||c==1){return x$(a.a.a)}else if(a.a.ac||c==2){return w$(a.a.a)}return false}
function P4(a){var b;b=(!b3&&(b3=new l3),b3).c.b[p4(new r4(a,'!new'))];if(!b){throw aE(new v4('There is no constructor for '+a.b))}return b}
function Q4(a){var b;b=(!b3&&(b3=new l3),b3).c.b[a.b.a+'.'+a.a];if(!b){throw aE(new v4('There is no invoker for '+(a.b.b+'.'+a.a)))}return b}
function S4(a){var b;b=(!b3&&(b3=new l3),b3).c.e[a.b.a+'.'+a.a];if(!b){throw aE(new v4('There is no return type for '+(a.b.b+'.'+a.a)))}return b}
function vdb(b){var c;try{return c=$wnd.JSON.parse(b),c}catch(a){a=_D(a);if(Zq(a,21)){throw aE(new ydb("Can't parse "+b))}else throw aE(a)}}
function _P(b){var c,d,e;try{e=mQ(b);d=z4(e);return d}catch(a){a=_D(a);if(Zq(a,80)){c=a;throw aE(new Ceb(dxb+Tdb(b.qg)+exb,c))}else throw aE(a)}}
function dhb(a){Igb();if(a.length==0){this.e=0;this.d=1;this.a=jq(eq(ir,1),Rxb,17,15,[0])}else{this.e=1;this.d=a.length;this.a=a;Lgb(this)}}
function _gb(a,b){this.e=a;if(b<AAb){this.d=1;this.a=jq(eq(ir,1),Rxb,17,15,[b|0])}else{this.d=2;this.a=jq(eq(ir,1),Rxb,17,15,[b%AAb|0,b/AAb|0])}}
function RU(a){var b,c,d;d=WU(a,a.rc,a.sc);c=mh(d.d).assignedElements();if(c!=null&&c.length==1){b=c[0];b.nodeType==1&&(b.focus(),undefined)}}
function k6(){yI.call(this);new h6(200,new r6);(JF(),this.Yc).tabIndex=-1;!this.b&&(this.b=F5(this,jq(eq(Js,1),Jtb,0,2,[])));O5(this.b,this.Yc)}
function Zp(a,b){switch(b.c){case 0:{a['dir']='rtl';break}case 1:{a['dir']='ltr';break}case 2:{Yp(a)!=(bq(),$p)&&(a['dir']='',undefined);break}}}
function xK(a){if(a.i){if(a.a.G){Wg($doc.body,a.a.B);a.f=IG(a.a.C);rK();a.b=true}}else if(a.b){ah($doc.body,a.a.B);AM(a.f.a);a.f=null;a.b=false}}
function G0(a,b,c,d){if(a.C){if(a.a!=c){XT(a.U,b,d);VT(a.U,c)}else (a.S==null||!wlb(a.S,b))&&XT(a.U,b,false)}else{LT(a.U,b);VT(a.U,c)}a.S=b;a.a=c}
function RL(){var a,b;RL=HE;BF();new xF((bg(),a='__gwtDevModeHook:'+$moduleName+':moduleBase',b=$wnd||self,b[a]||$moduleBase)+'clear.cache.gif')}
function XO(a,b){var c,d,e,f,g;g=new Wkb;for(d=ufb(b,'[^A-z0-9:!]+',0),e=0,f=d.length;e<f;++e){c=d[e];SO(a,c)&&(g.a[g.a.length]=c,true)}return g}
function Q(a){return cr(a)?Dtb(a):_q(a)?er((ptb(a),a)):$q(a)?(ptb(a),a)?1231:1237:Yq(a)?a._c():iq(a)?xtb(a):!!a&&!!a.hashCode?a.hashCode():xtb(a)}
function Xq(a,b){if(cr(a)){return !!Wq[b]}else if(a.rg){return !!a.rg[b]}else if(_q(a)){return !!Vq[b]}else if($q(a)){return !!Uq[b]}return false}
function mV(a){var b=a.sheet.cssRules?a.sheet.cssRules:a.sheet.rules;var c=[];for(var d=0;d<b.length;d++){c.push(b[d].cssText)}return c.join(' ')}
function vT(a){var b=a.sheet.cssRules?a.sheet.cssRules:a.sheet.rules;while(b.length>0){a.sheet.deleteRule?a.sheet.deleteRule(0):a.sheet.removeRule(0)}}
function bX(a,b){a.ob=b;fS(a.yc,b);b>0?CT(a.$,'.'+a.Ac+' .top-left-pane .cell.col'+b+', .'+a.Ac+' .bottom-left-pane .cell.col'+b,1):CT(a.$,Oxb,1)}
function KX(a,b,c,d,e){var f,g,h;if(b==16){HI(a.rb,e);g=Ewb+c+Fwb+d;h=fV(a,g);h?(f=h.d):(f=WU(a,c,d).d);bK(a.qb,new vY(a,f))}else{fN(a.qb,false)}}
function Dsb(a,b,c){if(c.nodeType==1&&(Gh(),c).tagName=='SLOT'&&jfb(((Gh(),c).getAttribute(lyb)||'').substr(0,uyb.length),uyb)){a.a=true;b.Qf(c)}}
function EX(a,b,c){var d,e,f;for(e=new slb(b);e.a<e.c.a.length;){d=qlb(e);f=Ewb+d.c+Fwb+d.k;!!c&&Tib(c.a,f)?OM(d):!!a.tb&&a.tb.contains(f)&&JM(d)}}
function Dtb(a){Btb();var b,c,d;c=':'+a;d=Atb[c];if(d!=null){return er((ptb(d),d))}d=ytb[c];b=d==null?Ctb(a):er((ptb(d),d));Etb();Atb[c]=b;return b}
function EW(a,b,c){a.Db=false;Yib(a.e);Yib(a.qc);Yg(a.Mb)&&(a.Lb=(a.Mb.offsetWidth||0)|0);tW(a);iS(a.yc,1,1,1,1);a.V=-1;s2((og(),ng),new VY(a,b,c))}
function kg(a,b){bg();var c;c=qf;if(c){if(c==$f){return}Jrb(c.a,(Kqb(),Iqb),a.Hd(),a);return}if(b){jg(Zq(a,82)?a.Jd():a)}else{Rfb();wf(a,Qfb,'','')}}
function Up(a){var b,c,d;Mf.call(this,Vp(a),a.isEmpty()?null:a.Oe().Ze());this.a=a;d=0;for(c=a.Oe();c.Ye();){b=c.Ze();if(d++==0){continue}tf(this,b)}}
function GU(a){var b,c,d,e;for(e=a.Tc>0?a.Tc+1:1;e<=a.zb;e++){d=new Wkb;for(c=1;c<=a.ob;c++){b=new UM(a,c,e);Wg(a.c,b.d);d.a[d.a.length]=b}Nkb(a.d,d)}}
function MU(a){var b,c,d,e;for(e=1;e<=a.Tc;e++){d=new Wkb;for(c=a.ob>0?a.ob+1:1;c<=a.xb;c++){b=new UM(a,c,e);Wg(a.Qc,b.d);d.a[d.a.length]=b}Nkb(a.Rc,d)}}
function fib(a,b){var c,d,e,f;f=a.size();b.length<f&&(b=ktb(new Array(f),b));e=b;d=a.Oe();for(c=0;c<f;++c){e[c]=d.Ze()}b.length>f&&(b[f]=null);return b}
function lN(a,b,c){var d;d=(JF(),a.Yc).style;d[Kwb]=(bN==-1&&(bN=qN(jwb)),-bN+(im(),rwb));d[Lwb]=(cN==-1&&(cN=qN(kwb)),-cN+rwb);aK(a,b,c);hN(a,a.F?0:1)}
function yV(a,b,c){var d,e,f,g;g=y2(b,c);if(g){d=(Gh(),g).getAttribute($ub)||'';AT(a.wb,d);e=a.wb.a;f=a.wb.b;if(e!=0&&f!=0){v_(a.a,e,f);a.Kc=e;a.Lc=f}}}
function Ie(a,b,c){var d;d=_F(c.b);d==-1?we(a,c.b):a.Vc==-1?WF((JF(),a.Yc),d|(a.Yc.__eventBits||0)):(a.Vc|=d);return Cp(!a.Wc?(a.Wc=new Fp(a)):a.Wc,c,b)}
function tq(a,b){var c,d,e;if(b<=22){c=a.l&(1<<b)-1;d=e=0}else if(b<=44){c=a.l;d=a.m&(1<<b-22)-1;e=0}else{c=a.l;d=a.m;e=a.h&(1<<b-44)-1}return oq(c,d,e)}
function y$(a,b,c){a.B=false;jP(a.u);a.t=false;if(!RV(a.V)){b==null&&(b='');a.P=jfb(b.substr(0,1),'=')||jfb(b.substr(0,1),'+');yX(a.V,c);a.P||dY(a.V,b)}}
function a7(a){var b,c,d;b=gsb(vsb(new zsb(null,new rqb(_6(a,new x7))),new u7));d=gq(hr,Jtb,17,b.length,15,1);for(c=0;c<b.length;c++){d[c]=b[c]}return d}
function MS(a){var b,c;c=K$(a.p.q,a.b);for(b=a.b+1;b<=a.c;b++){c+=K$(a.p.q,b)}a.k.style[Rub]=c+1+(im(),rwb);a.q.style[Rub]=c+1+rwb;a.a.style[Rub]=c+1+rwb}
function hq(a,b){var c=new Array(b);var d;switch(a){case 14:case 15:d=0;break;case 16:d=false;break;default:return c;}for(var e=0;e<b;++e){c[e]=d}return c}
function rfb(a,b,c){var d,e;d=sfb(b,'([/\\\\\\.\\*\\+\\?\\|\\(\\)\\[\\]\\{\\}$^])','\\\\$1');e=sfb(sfb(c,'\\\\','\\\\\\\\'),$tb,'\\\\$');return sfb(a,d,e)}
function SX(a){var b,c;c=OF(ie(a.sb));a.sc<=a.Tc?a.rc<=a.ob?(b=a.Oc):(b=a.Qc):a.rc<=a.ob?(b=a.c):(b=a.zc);if(c!=b){ah(c,ie(a.sb));JF();Wg(b,TF(ie(a.sb)))}}
function p2(a){var b,c,d,e;e=(w1(),w1(),v1);for(c=new slb(e);c.a<c.c.a.length;){b=qlb(c);d=b.a;!a?null:(Y1(d,(JF(),a.Yc).tkPid),null);continue}return null}
function sV(a){var b,c,d,e,f;d=a.db;b=jh(a.zc);for(f=new slb(a.kc);f.a<f.c.a.length;){e=qlb(f);c=cV(a,e);if(!!c&&jh(c.d)>=b){return d}else{++d}}return a.db}
function Y0(a,b,c){var d,e;if(a.a.J){for(e=new slb(a.a.J);e.a<e.c.a.length;){d=qlb(e);if(d.col1<=b&&d.row1<=c&&d.col2>=b&&d.row2>=c){return d}}}return null}
function zdb(a){var b;if(a==null){return false}b=typeof(a);return jfb(b,Ltb)||jfb(b,Mtb)||jfb(b,Ntb)||a.$implements__java_io_Serializable||Array.isArray(a)}
function zK(a){xK(a);if(a.i){ie(a.a).style[gvb]=ivb;a.a.N!=-1&&a.a.We(a.a.H,a.a.N);TH((QK(),UK()),a.a)}else{a.c||UH((QK(),UK()),a.a)}ie(a.a).style[uwb]=Avb}
function mcb(a,b){var c,d;if(b.indexOf('android')==-1){return}c=scb(b,b.indexOf('android ')+8,b.length);c=scb(c,0,c.indexOf(';'));d=ufb(c,'\\.',0);qcb(a,d)}
function PU(a,b,c){var d;++b;++c;d=Ewb+b+Fwb+c;if(Tib(a.b,d)){a.o=true;a.Q=Sib(a.b,d);QN(a.Q,true)}else{a.o=true;a.k=b;a.n=c;pX(a,b,c);a.Q=a.q;QN(a.q,true)}}
function qW(a){var b,c,d,e;if(a.Cc){for(e=(c=(new mkb(a.Cc)).a.$f().Oe(),new rkb(c));e.a.Ye();){d=(b=e.a.Ze(),b.hg());d.e.M&&!!d.d&&GV(d.d,d.b,d.k)&&iR(d)}}}
function fV(a,b){var c,d,e,f;for(d=(f=(new mkb(a.Eb)).a.$f().Oe(),new rkb(f));d.a.Ye();){c=(e=d.a.Ze(),e.hg());if(jfb(b,Ewb+c.c+Fwb+c.k)){return c}}return null}
function _6(a,b){var c,d,e,f,g;d=new Wkb;if(a==null||a.length==0||jfb(Xtb,a)){return d}e=vdb(a);for(c=0;c<e.length;c++){f=(g=e[c],g);Nkb(d,b.Of(f))}return d}
function yq(a,b){var c,d,e;e=a.h-b.h;if(e<0){return false}c=a.l-b.l;d=a.m-b.m+(c>>22);e+=d>>22;if(e<0){return false}a.l=c&Ovb;a.m=d&Ovb;a.h=e&Stb;return true}
function zT(b){try{var c=b.charAt(0);if(c==='r'){c=b.charAt(1);if(c==='h'){return 1}}else if(c==='c'){c=b.charAt(1);if(c==='h'){return 2}}}catch(a){}return 0}
function VK(a){QK();var b;b=Rib(OK,a);if(b){if(!a||(JF(),b.Yc==a)){return b}}Zib(OK)==0&&GG(new YK);!a?(b=new $K):(b=new RK(a));Uib(OK,a,b);sob(PK,b);return b}
function mQ(b){var c;try{return S4(new r4(new A4(b.qg),'getState'))}catch(a){a=_D(a);if(Zq(a,80)){c=a;throw aE(new Ceb(dxb+Tdb(b.qg)+exb,c))}else throw aE(a)}}
function hS(a,b){if(b==(le(a.B)||!!a.A&&le(a.A)||!!a.F&&le(a.F)||!!a.D&&le(a.D))){return}KS(a.B,b);!!a.D&&KS(a.D,b);!!a.F&&KS(a.F,b);!!a.A&&KS(a.A,b);jS(a,!b)}
function Z2(a,b){var c=a.split('.');while(typeof b==Gtb){var d=c.shift();if(!(d in b)){return false}else if(c.length==0){return true}else{b=b[d]}}return false}
function oT(a,b){var c,d;c=zj(b.a);if(a.b._){if(c==13){$$(a.b.a,(d=gL(a.b.sb),d==null?'':d),Aj(b.a))}else{wV(a.b);nP(a.a,true);hP(a.a);oP(a.a);KO(a.a)}}Ej(b.a)}
function TT(a,b){if(b==null){a.f.style[nvb]=(Ak(),Pub);a.c.style[Cxb]=(im(),swb)}else{a.c.style[Cxb]=(im(),'206.0px');a.f.style[nvb]=(Ak(),'inline');yh(a.f,b)}}
function dY(a,b){var c,d,e;e=nV(a);(JV(a,a.rc,a.sc)||IV(a,a.rc,a.sc))&&!!e&&LM(e,b);d=a.Tc>0?0:a.bb;for(;d<a.rc;d++){c=WU(a,d,a.sc);!!c&&(c.g=true)}ZX(a,false)}
function fZ(a,b,c){var d;this.a=b;d=Si($doc,'slot');d.setAttribute(lyb,a);pe(this,(JF(),d));b.setAttribute('slot',a);Je(this,new hZ(c,b),(!ep&&(ep=new Bn),ep))}
function N1(a){if(!a.b){return false}if(a.a.t==5&&a.a.s&&K1(a)>=534){return false}if(a.a.t==4&&a.a.s&&a.a.u>=6){return false}if(a.a.j){return false}return true}
function fE(a,b){var c;if(jE(a)&&jE(b)){c=a/b;if(Ptb<c&&c<Qtb){return c<0?$wnd.Math.ceil(c):$wnd.Math.floor(c)}}return eE(pq(jE(a)?uE(a):a,jE(b)?uE(b):b,false))}
function pcb(a,b){var c,d;if(b.indexOf('os ')==-1||b.indexOf(' like mac')==-1){return}c=scb(b,b.indexOf('os ')+3,b.indexOf(' like mac'));d=ufb(c,'_',0);qcb(a,d)}
function VM(a,b,c,d){this.n=a;this.c=b;this.k=c;this.d=Ri($doc);if(!d){this.o=null}else{this.f=d.needsMeasure;this.o=d.value;this.b=d.cellStyle}RM(this);SM(this)}
function Kdb(a,b){var c,d;Gdb();return cr(a)?(c=(ptb(a),a),d=(ptb(b),b),c==d?0:c<d?-1:1):_q(a)?seb((ptb(a),a),(ptb(b),b)):$q(a)?Jdb((ptb(a),a),(ptb(b),b)):a.je(b)}
function HW(a){var b,c,d;d=new Wkb;JU(a,a.w,d);c=oU(a);KU(a,d,a.db,a.zb,c);b=nU(a);IU(a,d,a.bb,a.xb,b);a.ob>0&&IU(a,d,1,a.ob,0);a.Tc>0&&KU(a,d,1,a.Tc,0);KW(a.w,d)}
function tib(a,b){var c,d,e;c=b.gg();e=b.hg();d=a.get(c);if(!(dr(e)===dr(d)||e!=null&&M(e,d))){return false}if(d==null&&!a.containsKey(c)){return false}return true}
function Bq(a,b){var c,d,e,f,g,h,i,j;i=a.h>>19;j=b.h>>19;if(i!=j){return j-i}e=a.h;h=b.h;if(e!=h){return e-h}d=a.m;g=b.m;if(d!=g){return d-g}c=a.l;f=b.l;return c-f}
function PV(a,b){var c,d,e,f;for(d=(f=(new mkb(a.Eb)).a.$f().Oe(),new rkb(f));d.a.Ye();){c=(e=d.a.Ze(),e.hg());if(jfb(b,Ewb+c.c+Fwb+c.k)){return true}}return false}
function rX(a,b,c,d,e,f){var g,h,i,j,k;for(k=e;k<=f;k++){for(g=c;g<=d;g++){j=Ewb+g+Fwb+k;if(Xob(b.b,j)){PV(a,j)?(h=fV(a,j)):(h=WU(a,g,k));i=Yob(b.b,j);hU(a,h,i)}}}}
function u6(a){if(a.b){pb(a.b);a.b=null}if((!a.L&&(a.L=_P(a)),a.L).i>=0){a.b=new K6(a);rb(a.b,(!a.L&&(a.L=_P(a)),a.L).i)}else{null.tg(new xcb(a.H,(Rdb(fB),fB.k)))}}
function cJ(a,b){Pe(a,Ui($doc));dG((JF(),a.Yc),Xvb);a.Vc==-1?WF(a.Yc,133398655|(a.Yc.__eventBits||0)):(a.Vc|=133398655);!!a.a&&(a.Yc[hwb]='',undefined);sj(a.Yc,b.a)}
function PR(a){var b;if(a.d.o){a.d.o=false;tW(a.c)}if(!RV(a.c)&&!a.d.e&&!!a.d.p&&d$(a.d.p,oV(a.c))){b=c$(a.d.p,oV(a.c));if(b){a.d.o=true;dP(a.d.u,false);NU(a.c,b)}}}
function kU(a,b,c){var d,e;e=a.Tc>=c.b;d=a.ob>=c.a;e&&d?Wg(a.Oc,(JF(),c.Yc)):e?Wg(a.Qc,(JF(),c.Yc)):d?Wg(a.c,(JF(),c.Yc)):Wg(a.zc,(JF(),c.Yc));Qe(c,a);Vib(a.Bc,b,c)}
function Oe(a){if(!a.Xc){QK();tob(PK,a)&&SK(a)}else if(Zq(a.Xc,31)){a.Xc.Pe(a)}else if(a.Xc){throw aE(new Beb("This widget's parent does not implement HasWidgets"))}}
function W4(a,b){var c;if(ZF(b.d)==8){AM(a.e.a);c=z2(b.d);!!a.d&&c==a.d?(a.g=true):Hrb(Qrb((Rdb(Bz),Bz.k)),'Ignoring mouseup from '+c+' when mousedown was on '+a.d)}}
function DJ(a,b){var c,d,e,f;if(!a.i){return}d=Rkb(a.b,b,0);if(d==-1){return}c=a.i?a.d:MF(a.d);f=(JF(),HF.Fe(c,d));e=HF.Ge(f);e==2&&ah(f,HF.Fe(f,1));b.Yc['colSpan']=2}
function r5(a,b,c){var d,e,f;$wnd.Math.abs(b-c);e=350;e<=0&&(e=1);Hrb(Qrb((Rdb(Qz),Qz.k)),'Animate '+e+' '+c+' '+b);f=-b+a.n;d=-c+a.n;if(o5){d-=a.n;f-=a.n}D5(a,e,d,f)}
function chb(a,b){this.e=a;if(gE(cE(b,-4294967296),0)){this.d=1;this.a=jq(eq(ir,1),Rxb,17,15,[wE(b)])}else{this.d=2;this.a=jq(eq(ir,1),Rxb,17,15,[wE(b),wE(rE(b,32))])}}
function yhb(a){var b,c,d;if(dE(a,0)>=0){c=fE(a,Qvb);d=mE(a,Qvb)}else{b=sE(a,1);c=fE(b,500000000);d=mE(b,500000000);d=bE(qE(d,1),cE(a,1))}return pE(qE(d,32),cE(c,yAb))}
function CE(b,c,d,e){BE();var f=zE;$moduleName=c;$moduleBase=d;VD=e;function g(){for(var a=0;a<f.length;a++){f[a]()}}
if(b){try{Ftb(g)()}catch(a){b(c,a)}}else{Ftb(g)()}}
function Ig(a){var b,c,d,e;b='Hg';c='Gf';e=$wnd.Math.min(a.length,5);for(d=e-1;d>=0;d--){if(jfb(a[d].d,b)||jfb(a[d].d,c)){a.length>=d+1&&a.splice(0,d+1);break}}return a}
function _m(a,b,c){var d,e,f,g,h;if(Ym){h=zo(Ym,(Gh(),a).type);if(h){for(g=h.Oe();g.Ye();){f=g.Ze();d=f.a.a;e=f.a.b;Zm(f.a,a);$m(f.a,c);Ke(b,f.a);Zm(f.a,d);$m(f.a,e)}}}}
function ocb(b,c){b.u=-1;b.v=-1;if(c.length>2){try{b.u=peb(c[1])}catch(a){a=_D(a);if(!Zq(a,21))throw aE(a)}try{b.v=peb(c[0])}catch(a){a=_D(a);if(!Zq(a,21))throw aE(a)}}}
function uib(a,b,c){var d,e,f;for(e=a.$f().Oe();e.Ye();){d=e.Ze();f=d.gg();if(dr(b)===dr(f)||b!=null&&M(b,f)){if(c){d=new Bkb(d.gg(),d.hg());e.$e()}return d}}return null}
function YT(a){var b;if(a.s==0){eh(a.p,Bvb);eh(a.n,Bvb)}else{th(a.p,Bvb);th(a.n,Bvb)}b=QT(a,a.u.length-1);if(a.s<b){th(a.q,Bvb);th(a.o,Bvb)}else{eh(a.q,Bvb);eh(a.o,Bvb)}}
function hU(a,b,c){var d;if(!b||!c){return}LM(b,null);d=c.Xc;if(d){if(a==d){Wg(b.d,(JF(),c.Yc))}else{Oe(c);Wg(b.d,(JF(),c.Yc));Qe(c,a)}}else{Wg(b.d,(JF(),c.Yc));Qe(c,a)}}
function Lg(a){Gg();var b=a.backingJsObject;if(b&&b.stack){var c=b.stack;var d=b+'\n';c.substring(0,d.length)==d&&(c=c.substring(d.length));return c.split('\n')}return []}
function Qhb(a,b,c,d,e){var f,g;f=0;for(g=0;g<e;g++){f=bE(f,tE(cE(b[g],yAb),cE(d[g],yAb)));a[g]=wE(f);f=rE(f,32)}for(;g<c;g++){f=bE(f,cE(b[g],yAb));a[g]=wE(f);f=rE(f,32)}}
function vF(){vF=HE;new lF('');rF=new RegExp('[&<>\'"]');pF=new RegExp('&','g');qF=new RegExp('>','g');sF=new RegExp('<','g');uF=new RegExp("'",'g');tF=new RegExp('"','g')}
function Sfb(a,b,c,d,e){Rfb();var f,g;qtb(a,'src');qtb(c,'dest');O(a);O(c);g=a.length;f=c.length;if(b<0||d<0||e<0||b+e>g||d+e>f){throw aE(new Ddb)}e>0&&gtb(a,b,c,d,e,true)}
function eh(a,b){var c,d;b=Eh(b);d=a.className||'';c=Ch(d,b);if(c==-1){d.length>0?(a.className=d+' '+b||'',undefined):(a.className=b||'',undefined);return true}return false}
function BJ(a){var b,c,d;if(!a.g){return}c=Rkb(a.f,a.g,0);b=c;while(true){c=c+1;c==a.f.a.length&&(c=0);if(c==b){d=Qkb(a.f,b);break}else{d=Qkb(a.f,c);if(d.b){break}}}AJ(a,d)}
function CJ(a){var b,c,d;if(!a.g){return}c=Rkb(a.f,a.g,0);b=c;while(true){c=c-1;c<0&&(c=a.f.a.length-1);if(c==b){d=Qkb(a.f,b);break}else{d=Qkb(a.f,c);if(d.b){break}}}AJ(a,d)}
function v6(a){var b,c,d;d=lh($doc.getElementsByTagName(Iyb)[0],Fub);for(b=0;b<d.length;b++){c=d[b];if(jfb(Jyb,c.rel)&&jfb(Yxb,c.type)&&jfb(a,c.href)){return c}}return null}
function Cfb(a){var b;b=0;while(0<=(b=a.indexOf('\\',b))){vtb(b+1,a.length);a.charCodeAt(b+1)==36?(a=a.substr(0,b)+'$'+vfb(a,++b)):(a=a.substr(0,b)+(''+vfb(a,++b)))}return a}
function kG(a,b){var c,d,e,f,g;if(!!eG&&!!a&&Ep(a,eG)){c=fG.a;d=fG.b;e=fG.c;f=fG.d;gG(fG);hG(fG,b);Dp(a,fG);g=!(fG.a&&!fG.b);fG.a=c;fG.b=d;fG.c=e;fG.d=f;return g}return true}
function FR(a,b,c){var d;d=L$(a.d,b,c);if(d){a.a=b;a.b=c;b=d.col1;c=d.row1}else{a.a=0;a.b=0}DX(a.c,b,c);QW(a.c,b,c);O0(a.d,b,c,null);PR(a);vab(a.d.W,c,b,false);qb(a.d.s,200)}
function lS(a,b){if(b==((JF(),a.Yc).style.display!=Pub||!!a.a&&le(a.a)||!!a.X&&le(a.X)||!!a.W&&le(a.W))){return}Ee(a.Yc,b);!!a.W&&ue(a.W,b);!!a.X&&ue(a.X,b);!!a.a&&ue(a.a,b)}
function OU(a,b,c){var d,e;a.R=true;CT(a.$,Oxb,0);a.S=b;LM(c,null);e=b.Xc;!!e&&a!=e&&Oe(b);d=c.d;eh(d,Pxb);Wg(d,(JF(),b.Yc));(!e||!!e&&a!=e)&&Qe(b,a);s2((og(),ng),new BY(a))}
function w5(a){var b,c,d;if(o5){a.q[Ozb]=a.c}else{for(c=new slb(a.g);c.a<c.c.a.length;){b=qlb(c);d=b.style;d[Rwb]='translate3d(0,0,0)'}Ah(a.q,a.c)}p5=null;AM(a.d.a);a.d=null}
function UN(a,b,c,d){a.c=b;a.d=c;a.b=d;(JF(),a.Yc).style[ovb]=Bvb;!!a.t&&(a.t.style[ovb]=Bvb,undefined);a.i.style[ovb]=(wm(),Bvb);oN(a);a.k=oh(a.Yc,qwb);a.n=oh(a.Yc,Oub);NN(a)}
function c5(a,b){var c,d,e,f;if(!a.a.r){return false}f=vj(b.a)[0];d=ai((Gh(),f).clientX||0)-a.a.B;e=ai(f.clientY||0)-a.a.C;c=d*d+e*e;if(c>a.a.n*a.a.n){return true}return false}
function jV(a){var b,c,d,e;if(a.kc.a.length==0){return a.xb}d=a.xb;b=Qkb(a.kc,0);e=b.size();for(c=e-1;c>0;c--){if(ih(b.getAtIndex(c).d)<ih(a.zc)){return d}else{--d}}return a.xb}
function gib(a){var b,c,d;d=new xqb('[',']');for(c=a.Oe();c.Ye();){b=c.Ze();wqb(d,b===a?'(this Collection)':b==null?Xtb:KE(b))}return !d.a?d.c:d.e.length==0?d.a.a:d.a.a+(''+d.e)}
function HM(a){var b;if(a.i){SM(a);!!a.j&&Wg(a.d,a.j)}b=Rib(a.n.qc,new YM(a.o,a.b,a.k,a.c));if(!b){b=Neb((a.d.scrollWidth||0)|0);Uib(a.n.qc,new YM(a.o,a.b,a.k,a.c),b)}return b.a}
function iH(){fH=Ftb(nH);gH=Ftb(oH);var c=EH;var d=cH;c(d,function(a,b){d[a]=Ftb(b)});var e=eH;c(e,function(a,b){e[a]=Ftb(b)});c(e,function(a,b){$wnd.addEventListener(a,b,true)})}
function QJ(a,b){pe(this,(JF(),bj($doc)));se(this,ye(this.Yc)+'-'+owb,false);xh(this.Yc,a);this.Yc.className='gwt-MenuItem';uh(this.Yc,'id',hj($doc));Qd();Db(ld,this.Yc);this.a=b}
function UX(a,b){var c,d,e,f,g;for(f=(g=(new mkb(a.Eb)).a.$f().Oe(),new rkb(g));f.a.Ye();){e=(c=f.a.Ze(),c.hg());d=Ewb+e.c+Fwb+e.k;!!b&&Xob(b.b,d)?NM(e):!!a.r&&Tib(a.r,d)&&IM(e)}}
function $U(a){var b,c;if(a.K.a.length==0){return 0}b=0;while(P$(a.a,b+1)){++b}c=new f2;!!a.ib&&a.ib.a.length>0&&b<=a.ib.a.length?c2(c,Qkb(a.ib,b)):c2(c,Qkb(a.K,b));return er(c.b)}
function kV(a){var b,c;if(a.ic.a.length==0){return 0}b=0;while(Q$(a.a,b+1)){++b}c=new f2;!!a.jb&&a.jb.a.length>0&&b<=a.jb.a.length?c2(c,Qkb(a.jb,b)):c2(c,Qkb(a.ic,b));return er(c.e)}
function F$(a){var b,c;if(a.u.f);else if(a.B||a.t){a.c=true;b=(c=gL(a.u.j),c==null?'':c);wab(a.W,a.V.sc,a.V.rc,b);y$(a,b,true)}else if(a.o){a.o=false;a.V.Fc&&tW(a.V);dP(a.u,true)}}
function SW(a,b){var c,d,e;if(!!a.ib&&a.ib.a.length>b-1){sob(a.uc,Neb(b));d=Qkb(a.ib,b-1);eh(d,Jxb)}else{sob(a.tc,Neb(b));e=b-a.bb;if(e>=0&&a.K.a.length>e){c=Qkb(a.K,e);eh(c,Jxb)}}}
function TW(a,b){var c,d;if(!!a.jb&&a.jb.a.length>b-1){sob(a.vc,Neb(b));c=Qkb(a.jb,b-1);eh(c,Ixb)}else{sob(a.wc,Neb(b));d=b-a.db;if(d>=0&&a.ic.a.length>d){c=Qkb(a.ic,d);eh(c,Ixb)}}}
function _hb(a,b){Uhb();var c,d;d=(Igb(),Dgb);c=a;for(;b>1;b>>=1){(b&1)!=0&&(d=Vgb(d,c));c.d==1?(c=Vgb(c,c)):(c=new dhb(bib(c.a,c.d,gq(ir,Rxb,17,c.d<<1,15,1))))}d=Vgb(d,c);return d}
function Uob(){function b(){try{return (new Map).entries().next().done}catch(a){return false}}
if(typeof Map==='function'&&Map.prototype.entries&&b()){return Map}else{return Vob()}}
function ST(a,b){if(a.s<b){do{a.t-=RT(a,a.s);++a.s}while(a.s<b);a.c.style[Kwb]=a.t+(im(),rwb)}else if(a.s>b){do{--a.s;a.t+=RT(a,a.s)}while(a.s>b);a.c.style[Kwb]=a.t+(im(),rwb)}YT(a)}
function O2(a){N2();Hrb(M2,(Rdb(a),'asking for '+a.k));if(py==a){Hrb(M2,(Rdb(OA),'Returning '+OA.k+' from fake RpcProxy'));return new Lbb}throw aE(new Beb(''+a+' is not supported'))}
function zg(b,c){var d,e,f,g;for(e=0,f=b.length;e<f;e++){g=b[e];try{g[1]?g[0].Kd()&&(c=yg(c,g)):g[0].Ld()}catch(a){a=_D(a);if(Zq(a,20)){d=a;bg();kg(d,true)}else throw aE(a)}}return c}
function mS(a){var b;if(a._){!!a.$&&fN(a.$,false);a.$=new FN;kN(a.$,a.Q.a);se(a.$,oxb,true);b=new EJ;nJ(b,new PJ(new lF(jF(new kF).a.a),new AS(a)));vI(a.$,b);s2((og(),ng),new CS(a))}}
function eW(a){var b,c;if(a.Db){b=(a.zc.offsetHeight||0)|0;c=(a.zc.offsetWidth||0)|0;if(b>a.oc||c>a.pc){a.oc=b;a.pc=c;a.Ob=-a.a.j;a.Pb=-a.a.L;g6(a.lc)}else{a.oc=b;a.pc=c}nW(a);fY(a)}}
function phb(a,b){var c,d,e;e=a.e;if(b==0||a.e==0){return}d=b>>5;a.d-=d;if(!uhb(a.a,a.d,a.a,d,b&31)&&e<0){for(c=0;c<a.d&&a.a[c]==-1;c++){a.a[c]=0}c==a.d&&++a.d;++a.a[c]}Lgb(a);a.b=-2}
function rhb(a,b,c,d){var e,f,g;if(d==0){Sfb(b,0,a,c,a.length-c)}else{g=32-d;a[a.length-1]=0;for(f=a.length-1;f>c;f--){a[f]|=b[f-c-1]>>>g;a[f-1]=b[f-c-1]<<d}}for(e=0;e<c;e++){a[e]=0}}
function tf(a,b){qtb(b,'Cannot suppress a null exception.');ltb(b!=a,'Exception can not suppress itself.');if(a.g){return}a.j==null?(a.j=jq(eq(SB,1),Jtb,20,0,[b])):(a.j[a.j.length]=b)}
function fg(b,c,d){var e,f;e=dg();try{if(qf){try{return cg(b,c,d)}catch(a){a=_D(a);if(Zq(a,20)){f=a;kg(f,true);return undefined}else throw aE(a)}}else{return cg(b,c,d)}}finally{gg(e)}}
function Dp(b,c){var d,e;!c.e||c.oe();e=c.f;Vm(c,b.b);try{Lp(b.a,c)}catch(a){a=_D(a);if(Zq(a,90)){d=a;throw aE(new Wp(d.a))}else throw aE(a)}finally{e==null?(c.e=true,c.f=null):(c.f=e)}}
function VT(a,b){var c,d;a.r!=-1&&th(a.u[a.r],Dxb);a.r=b-1;c=a.u[a.r];eh(c,Dxb);if(a.s>a.r){ST(a,a.r)}else if(ih(a.k)<(Gh(),Fh).Yd(c)+((c.offsetWidth||0)|0)&&!a.d){d=QT(a,a.r);ST(a,d)}}
function H0(a,b){var c,d,e,f,g,h,i;if(!b||!a.p){return}h=a.V.wb;for(g=new sjb((new kjb(b)).a);g.b;){f=rjb(g);e=c$(a.p,f.gg());if(e){AT(h,f.gg());d=h.a;i=h.b;c=WU(a.V,d,i);OU(a.V,e,c)}}}
function d3(a,b){var c,d,e;if(a.b.a.length!=0){for(d=new slb(a.b);d.a<d.c.a.length;){c=qlb(d);e=null.tg();if(jfb(b.substr(0,e.length),e)){f3(a,null.ug+' '+null.ug);Ukb(a.b,c);return}}}}
function qN(b){try{var c=$wnd.document.body;var d=c.currentStyle?c.currentStyle:getComputedStyle(c);if(d&&d.position=='relative'){return c.getBoundingClientRect()[b]}}catch(a){}return 0}
function VX(a,b){var c,d,e,f,g;for(f=(g=(new mkb(a.Eb)).a.$f().Oe(),new rkb(g));f.a.Ye();){e=(c=f.a.Ze(),c.hg());d=Ewb+e.c+Fwb+e.k;!!b&&Tib(b.a,d)?OM(e):!!a.tb&&a.tb.contains(d)&&JM(e)}}
function u1(){var a=document.createElement(Eyb);var b=['animation','oAnimation','mozAnimation','webkitAnimation'];for(var c=0;c<b.length;c++){if(a.style[b[c]]!==undefined){return b[c]}}}
function Bfb(a){var b,c;if(a>=aub){b=55296+(a-aub>>10&1023)&bub;c=56320+(a-aub&1023)&bub;return String.fromCharCode(b)+(''+String.fromCharCode(c))}else{return String.fromCharCode(a&bub)}}
function T7(a,b){this.c=new qob;this.a=new qob;this.b=new qob;this.d=gq(QB,_tb,2,0,6,1);!!a&&(this.e=new BZ,yZ(this.e,a,b),aQ(this.e,new B1),this.f=rQ(this.e),TH(VK(b),this.f),undefined)}
function qrb(a,b){var c,d,e,f;c=Sib(a.a,b);if(!c){d=new Prb(b);e=(Arb(),xrb)?null:d.c;f=wfb(e,0,$wnd.Math.max(0,ofb(e,Bfb(46))));Lrb(d,qrb(a,f));Vib(a.a,xrb?null:d.c,d);return d}return c}
function XX(a,b){var c,d,e,f;e=Ewb+b.col1+Fwb+b.row1;f=Rib(a.Eb,Neb(b.id));Wib(a.Kb,b);WX(a,b,f);d=f.d;if(Tib(a.b,e)){c=Sib(a.b,e);jfb(Ij(d.style),(Ak(),Pub))?(fN(c,false),bh(c.i)):NN(c)}}
function L2(a,b,c){var d,e,f,g,h;for(f=(Vcb(),jq(eq(aB,1),Jtb,77,0,[Scb,Ucb,Rcb,Qcb,Tcb])),g=0,h=f.length;g<h;++g){e=f[g];d=b+'-'+xfb(e.b!=null?e.b:''+e.c,(Epb(),Cpb));c==e?eh(a,d):th(a,d)}}
function oob(){oob=HE;mob=jq(eq(QB,1),_tb,2,6,['Sun','Mon','Tue','Wed','Thu','Fri','Sat']);nob=jq(eq(QB,1),_tb,2,6,['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'])}
function FE(){EE={};!Array.isArray&&(Array.isArray=function(a){return Object.prototype.toString.call(a)==='[object Array]'});function b(){return (new Date).getTime()}
!Date.now&&(Date.now=b)}
function zfb(a){var b,c,d;c=a.length;d=0;while(d<c&&(vtb(d,a.length),a.charCodeAt(d)<=32)){++d}b=c;while(b>d&&(vtb(b-1,a.length),a.charCodeAt(b-1)<=32)){--b}return d>0||b<c?a.substr(d,b-d):a}
function DL(){QH.call(this);this.d=(JF(),dj($doc));this.c=aj($doc);Wg(this.d,TF(this.c));oe(this,this.d);this.a=(OI(),LI);this.b=(SI(),RI);this.d['cellSpacing']='0';this.d['cellPadding']='0'}
function O1(){F1();var a;this.a=new ucb(P1());if(this.a.j){a=I1();a!=-1&&tcb(this.a,a)}this.a.e?(this.b=Jvb in window):this.a.j?(this.b=!!navigator.msMaxTouchPoints):(this.b=!this.a.p&&G1())}
function s5(a){var b,c,d,e,f;if(a.k<3){Hrb(Qrb((Rdb(Qz),Qz.k)),'Not enough data for speed calculation');return 0}d=a.k%3;b=a.s[d];c=a.b[d];d+=3;--d;d=d%3;e=a.s[d];f=a.b[d];return (b-e)/(f-c)}
function aI(b,c){ZH();var d,e,f,g;d=null;for(g=b.Oe();g.Ye();){f=g.Ze();try{c.Qe(f)}catch(a){a=_D(a);if(Zq(a,20)){e=a;!d&&(d=new vob);Uib(d.a,e,d)}else throw aE(a)}}if(d){throw aE(new _H(d))}}
function G4(a,b,c){var d,e,f,g;d=(!b3&&(b3=new l3),b3).c.d[(new A4(b)).b];if(!d){d={};a.d[(new A4(b)).b]=d}for(f=new Mmb(c.c.b.Oe());f.b.Ye();){e=f.b.Ze();g=d[e];if(!g){g=[];d[e]=g}g.push(c)}}
function gdb(){gdb=HE;edb=new hdb('WEBSOCKET',0,'websocket');fdb=new hdb('WEBSOCKET_XHR',1,'websocket-xhr');ddb=new hdb('STREAMING',2,'streaming');cdb=new hdb('LONG_POLLING',3,'long-polling')}
function yQ(a,b,c,d){var e,f;jfb(c.substr(0,1),'-')?(f='-'.length,jfb(b.substr(b.length-f,f),'-')&&tfb(c,'-','')):(e='-'.length,jfb(b.substr(b.length-e,e),'-')||(b+='-'));a.zf().qd(b+(''+c),d)}
function CT(a,b,c){if(!a.sheet.cssRules[c]){return -1}var d=a.sheet.cssRules[c].selectorText;var e=a.sheet.cssRules[c].cssText.replace(d,b);a.sheet.deleteRule(c);return a.sheet.insertRule(e,c)}
function oeb(a){meb==null&&(meb=new RegExp('^\\s*[+-]?(NaN|Infinity|((\\d+\\.?\\d*)|(\\.\\d+))([eE][+-]?\\d+)?[dDfF]?)\\s*$'));if(!meb.test(a)){throw aE(new cfb(Wtb+a+'"'))}return parseFloat(a)}
function Hq(a,b){var c,d,e;b&=63;if(b<22){c=a.l<<b;d=a.m<<b|a.l>>22-b;e=a.h<<b|a.m>>22-b}else if(b<44){c=0;d=a.l<<b-22;e=a.m<<b-22|a.l>>44-b}else{c=0;d=0;e=a.l<<b-44}return oq(c&Ovb,d&Ovb,e&Stb)}
function AT(h,a){var b=a.length;var c=0;var d=0;var e=0;var f=0;var g=0;while(c<b){d=a.charCodeAt(c);d===32?(e=e+1):d>47&&d<58&&(e===0?(g=g*10+d-48):(f=f*10+d-48));if(e===2){break}c++}h.b=f;h.a=g}
function Qe(a,b){var c;c=a.Xc;if(!b){try{!!c&&c.vd()&&a.yd()}finally{a.Xc=null}}else{if(c){throw aE(new Beb('Cannot set a new parent without first clearing the old parent'))}a.Xc=b;b.vd()&&a.wd()}}
function jJ(a,b,c,d){var e,f,g,h;h=(JF(),a.Yc);g=Yi($doc);g.text=b;g.removeAttribute(nwb);g.value=c;f=(Gh(),h).options.length;(d<0||d>f)&&(d=f);if(d==f){Ph(h,g,null)}else{e=h.options[d];Ph(h,g,e)}}
function dO(a,b){if(a.b==b){return}if(b){a.a.innerHTML='+';Be((JF(),a.Yc),'minus',false);Be(a.Yc,'plus',true)}else{a.a.innerHTML='&#x2212;';Be((JF(),a.Yc),'plus',false);Be(a.Yc,'minus',true)}a.b=b}
function QT(a,b){var c;c=((a.k.offsetWidth||0)|0)-((a.i.offsetWidth||0)|0);jfb(Ij(a.f.style),Pub)||(c-=(a.f.offsetWidth||0)|0);c=er(c-RT(a,b));while(b>0&&c-RT(a,b-1)>0){--b;c=er(c-RT(a,b))}return b}
function yU(a){var b,c,d,e;KN(a.q);for(c=(e=(new mkb(a.b)).a.$f().Oe(),new rkb(e));c.a.Ye();){b=(d=c.a.Ze(),d.hg());fN(b,false);bh(b.i)}Yib(a.b);!!a.r&&Yib(a.r);!!a.i&&Yib(a.i);!!a.tb&&a.tb.clear()}
function MO(a){var b,c,d,e;for(c=(e=(new bkb(a.F.a)).a.$f().Oe(),new hkb(e));c.a.Ye();){b=(d=c.a.Ze(),d.gg());b.d.style[Xwb]='';b.d.style[Ywb]=''}Yib(a.F.a);a.i.a=gq(KB,Jtb,1,0,5,1);Yib(a.D);_g(a.r)}
function Blb(a,b,c,d,e){var f,g,h,i;f=d-c;if(f<7){ylb(b,c,d);return}h=c+e;g=d+e;i=h+(g-h>>1);Blb(b,a,h,i,-e);Blb(b,a,i,g,-e);if($nb(a[i-1],a[i])<=0){while(c<d){b[c++]=a[h++]}return}zlb(a,h,i,g,b,c,d)}
function Qf(a){var b;if(a.c==null){b=dr(a.b)===dr(Of)?null:a.b;a.d=b==null?Xtb:ar(b)?b==null?null:b.name:cr(b)?'String':Sdb(O(b));a.a=a.a+': '+(ar(b)?b==null?null:b.message:b+'');a.c='('+a.d+') '+a.a}}
function uhb(a,b,c,d,e){var f,g,h;f=true;for(g=0;g<d;g++){f=f&c[g]==0}if(e==0){Sfb(c,d,a,0,b);g=b}else{h=32-e;f=f&c[g]<<h==0;for(g=0;g<b-1;g++){a[g]=c[g+d]>>>e|c[g+d+1]<<h}a[g]=c[g+d]>>>e;++g}return f}
function wf(a,b,c,d){var e,f,g,h,i;b.De(d+c+a);xf(a,b,d);for(f=(a.j==null&&(a.j=gq(SB,Jtb,20,0,0,1)),a.j),g=0,h=f.length;g<h;++g){e=f[g];wf(e,b,'Suppressed: ','\t'+d)}i=a.e;!!i&&wf(i,b,'Caused by: ',d)}
function Jq(a,b){var c,d,e,f;b&=63;c=a.h&Stb;if(b<22){f=c>>>b;e=a.m>>b|c<<22-b;d=a.l>>b|a.m<<22-b}else if(b<44){f=0;e=c>>>b-22;d=a.m>>b-22|a.h<<44-b}else{f=0;e=0;d=c>>>b-44}return oq(d&Ovb,e&Ovb,f&Stb)}
function AK(a,b){var c,d,e,f,g,h;a.i||(b=1-b);g=0;e=0;f=0;c=0;d=er(b*a.d);h=er(b*a.e);switch(0){case 0:g=a.d-d>>1;e=a.e-h>>1;f=e+h;c=g+d;}(TJ(),SJ).ff(ie(a.a),'rect('+g+'px, '+f+'px, '+c+'px, '+e+'px)')}
function gQ(a,b){var c,d,e,f,g,h,i,j,k;a.wf(fQ(a));c=R4(a.qg);if(c){e=new vob;k=_1(c);for(d=0;d<k.length;d++){j=k[d];if(b.Hf(j)){i=c[j];for(f=0;f<i.length;f++){g=i[f];h=Uib(e.a,g,e);h==null&&w4(g,b)}}}}}
function ZR(a,b,c,d){var e,f;f=0;e=0;if(c<0){if(b>1){while(b>1&&e>c){--b;e-=a[b-1]}d&&e<c&&++b;f=b}else{f=1}}else{if(b<a.length){while(b<=a.length&&e<c){e+=a[b-1];++b}f=b}else{f=a.length}}return d?f:f-1}
function J1(a){if(a.a.t==5){return 'v-android'}else if(a.a.t==4){return 'v-ios v-ios'+a.a.u}else if(a.a.t==1){return 'v-win'}else if(a.a.t==3){return 'v-lin'}else if(a.a.t==2){return 'v-mac'}return null}
function tJ(a,b,c){var d,e;if(c<0||c>a.b.a.length){throw aE(new Ddb)}Mkb(a.b,c,b);e=0;for(d=0;d<c;d++){Zq(Qkb(a.b,d),98)&&++e}Mkb(a.f,e,b);oJ(a,c,(JF(),b.Yc));se(b,ye(b.Yc)+'-'+owb,false);DJ(a,b);return b}
function pT(a,b,c){var d;a.b=b;a.a=c;d=b.sb;Ie(d,a,(En(),En(),Dn));Ie(d,a,(dn(),dn(),cn));Ie(d,a,(Sn(),Sn(),Rn));Ie(d,a,(Ln(),Ln(),Kn));Ie(d,a,(ln(),ln(),kn));Ie(d,a,(mo(),mo(),lo));Ie(d,a,(to(),to(),so))}
function c7(a,b,c){var d,e,f,g,h,i,j,k;if(a==null||a.length==0||jfb(Xtb,a)){return null}f=vdb(a);d=new qob;for(e=0;e<(i=xdb(f),i).length;e++){g=(h=xdb(f),h)[e];j=(k=f[g],k);Uib(d,b.Of(g),c.Of(j))}return d}
function YV(a){var b,c;b=-qh(a.zc);c=-((a.zc.scrollTop||0)|0);a.Qc.style[Kwb]=b+(im(),rwb);a.I.style[Kwb]=b+rwb;a.c.style[Lwb]=c+rwb;a.gc.style[Lwb]=c+rwb;a.D.style[Kwb]=b-a.g+rwb;a.cc.style[Lwb]=c-a.f+rwb}
function J2(a,b){w2();if(!b){a.ondrag=function(){return false};a.onselectstart=function(){return false};a.style.webkitUserSelect=Pub}else{a.ondrag=null;a.onselectstart=null;a.style.webkitUserSelect='text'}}
function FX(a){var b,c,d,e,f;if(!a.tb){return}if(a.b){for(d=new sjb((new kjb(a.b)).a);d.b;){c=rjb(d);f=c.gg();b=c.hg();e=a.tb.contains(f)?a.ub:null;RN(b,e)}}if(a.q){e=a.tb.contains(a.j)?a.ub:null;RN(a.q,e)}}
function rab(b,c){if(!b){return}var d=[];for(var e=0;e<c.length;e++){var f=c[e];var g=Object.getOwnPropertyNames(f).find(function(a){return /^(a|value_0|value.*g\$)$/.test(a)});var h=g?f[g]:f;d.push(h)}b(d)}
function mW(a,b,c){var d,e,f;if(b.col1<=a.ob&&b.col2>a.ob){d=a.a.g;f=$R(d,b.col1,a.ob+1);e=$R(d,a.ob+1,b.col2+1)-qh(a.zc)+1;if(e>0){f+=e;c.d.style[$wb]=''}else{c.d.style[$wb]='0'}c.d.style[Rub]=f+(im(),rwb)}}
function rcb(b,c){var d,e;d=lfb(c,Bfb(46));d<0&&(d=c.length);b.b=peb(scb(c,0,d));e=mfb(c,Bfb(46),d+1);e<0&&(e=c.length);try{b.c=peb(sfb(scb(c,d+1,e),'[^0-9].*',''))}catch(a){a=_D(a);if(!Zq(a,49))throw aE(a)}}
function pJ(a){var b,c,d;AJ(a,null);b=a.i?a.d:MF(a.d);while(JF(),HF.Ge(b)>0){ah(b,HF.Fe(b,0))}for(d=new slb(a.b);d.a<d.c.a.length;){c=qlb(d);c.Yc['colSpan']=1}a.f.a=gq(KB,Jtb,1,0,5,1);a.b.a=gq(KB,Jtb,1,0,5,1)}
function Ak(){Ak=HE;pk=new Dk;hk=new Zk;kk=new _k;lk=new bl;nk=new dl;ok=new fl;qk=new hl;rk=new jl;sk=new ll;vk=new Fk;xk=new Hk;wk=new Jk;zk=new Lk;tk=new Nk;uk=new Pk;yk=new Rk;jk=new Tk;ik=new Vk;mk=new Xk}
function HL(a,b,c){var d,e,f;if(c<0||c>a.c){throw aE(new Ddb)}if(a.c==a.a.length){f=gq(lw,Jtb,13,a.a.length*2,0,1);for(e=0;e<a.a.length;++e){f[e]=a.a[e]}a.a=f}++a.c;for(d=a.c-1;d>c;--d){a.a[d]=a.a[d-1]}a.a[c]=b}
function s1(a,b){o1();if(a._vaadin_animationend_callbacks){var c=a._vaadin_animationend_callbacks;for(var d=0;d<c.length;d++){if(c[d].listener==b){a.removeEventListener(n1,c[d],false);return true}}return false}}
function mb(a){var b,c,d,e,f,g;b=gq(or,{742:1,3:1},176,a.a.a.length,0,1);b=Vkb(a.a,b);c=new pf;for(e=b,f=0,g=e.length;f<g;++f){d=e[f];Ukb(a.a,d);d.a.gd(c.a)}a.a.a.length>0&&qb(a.b,$wnd.Math.max(5,16-(Xf()-c.a)))}
function Vp(a){var b,c,d,e,f;c=a.size();if(c==0){return null}b=new Ofb(c==1?'Exception caught: ':c+' exceptions caught: ');d=true;for(f=a.Oe();f.Ye();){e=f.Ze();d?(d=false):(b.a+='; ',b);Kfb(b,e.Hd())}return b.a}
function vO(a,b){mL();nL.call(this,ej($doc));(JF(),this.Yc).className='gwt-TextArea';this.c=a;this.a=b;this.Yc.style[gvb]=(Cl(),ivb);this.Yc.style[pvb]='1';this.Yc.style[jwb]=(im(),'-1000.0px');Mj(this.Yc.style)}
function eV(a){var b,c,d,e,f,g;g=a.bb;b=hh(a.zc);e=new Wkb;f=0;for(f=0;f<a.kc.a.length;f++){Q$(a.a,f+1)||(e=Qkb(a.kc,f))}for(d=new slb(e);d.a<d.c.a.length;){c=qlb(d);if(hh(c.d)>=b){return g}else{++g}}return a.bb}
function BX(a,b){AX(a);aG(a.zc);(a.rc!=a.Kc||a.sc!=a.Lc)&&a.Kc!=-1&&a.Lc!=-1?h_(a.a,a.rc,a.Kc,a.sc,a.Lc):X$(a.a,a.Kc,a.Lc,(nh(kY(b)),!!(Gh(),b).shiftKey),!!b.metaKey||!!b.ctrlKey,true);a.xc=false;a.Kc=-1;a.Lc=-1}
function Crb(a,b){var c,d,e,f,g,h,i,j;for(e=Grb(a),g=0,i=e.length;g<i;++g){c=e[g];c.Ce(b)}j=!xrb&&a.e?xrb?null:a.d:null;while(j){for(d=Grb(j),f=0,h=d.length;f<h;++f){c=d[f];c.Ce(b)}j=!xrb&&j.e?xrb?null:j.d:null}}
function ON(a,b){HI(a.a,b);LN(a.a);le(a.f)&&(le(a.a)||le(a.g)||jfb((Ak(),tvb),Ij(a.e.style)))?(ke(a.f).className||'').indexOf(Swb)!=-1||se(a.f,Swb,true):(ke(a.f).className||'').indexOf(Swb)!=-1&&se(a.f,Swb,false)}
function PN(a,b){HI(a.g,b);LN(a.g);le(a.f)&&(le(a.a)||le(a.g)||jfb((Ak(),tvb),Ij(a.e.style)))?(ke(a.f).className||'').indexOf(Swb)!=-1||se(a.f,Swb,true):(ke(a.f).className||'').indexOf(Swb)!=-1&&se(a.f,Swb,false)}
function RN(a,b){HI(a.f,b);LN(a.f);le(a.f)&&(le(a.a)||le(a.g)||jfb((Ak(),tvb),Ij(a.e.style)))?(ke(a.f).className||'').indexOf(Swb)!=-1||se(a.f,Swb,true):(ke(a.f).className||'').indexOf(Swb)!=-1&&se(a.f,Swb,false)}
function xQ(a,b){a.zf().qd('v-disabled',!b);Zq(a.zf(),73)&&a.zf().Bd(b);Zq(a,137)||Nrb(Qrb((Rdb(Hz),Hz.k)),'Parent of connector '+q2(a)+' is null. This is typically an indication of a broken component hierarchy')}
function zW(a,b,c,d,e){var f,g,h,i;AU(a);for(i=d;i<=e;i++){for(f=b;f<=c;f++){if(a.rc!=f||a.sc!=i){g=WU(a,f,i);sob(a.u,new aZ(f,i));if(g){sob(a.t,g);eh(g.d,Gxb)}h=fV(a,Ewb+f+Fwb+i);if(h){sob(a.t,h);eh(h.d,Gxb)}}}}}
function S2(a,b){if(a.b){return true}else if(a.c){return Z2(b,a.c)}else{if(a.a){return a2(a.a,b)}else{throw aE(new Beb('StateChangeEvent should have either stateJson, changedProperties or changePropertiesSet'))}}}
function th(a,b){var c,d,e,f,g;b=Eh(b);g=a.className||'';e=Ch(g,b);if(e!=-1){c=zfb(g.substr(0,e));d=zfb(vfb(g,e+b.length));c.length==0?(f=d):d.length==0?(f=c):(f=c+' '+d);a.className=f||'';return true}return false}
function MT(a){var b,c,d;a.d=false;bh(a.g);c=a.u[a.r];c.style[Rub]='';d=a.g.value;if($T(d)&&!jfb(a.b,d)){for(b=0;b<a.u.length;b++){if(jfb(d,nh(a.u[b]))){WT(c,a.b);return}}A_(a.e,a.r,d);WT(c,d);YT(a)}else{WT(c,a.b)}}
function xX(a,b){var c,d;aG((JF(),a.Yc));a.Ub.className=Wxb;th(a.Vb,Ewb+a.$b);ie(a.yc).style[Kwb]='';if(a.Zb){c=new qob;d=b-a.Sb;d<0&&(d=0);d!=K$(a.a,a.$b)&&Uib(c,Neb(a.$b),Neb(d));c.a.c+c.b.c==0||g_(a.a,c)}a.$b=-1}
function Hob(a,b,c){var d,e,f,g,h;h=b==null?0:(g=Q(b),g|0);e=(d=a.a.get(h),d==null?new Array:d);if(e.length==0){a.a.set(h,e)}else{f=Eob(b,e);if(f){return f.ig(c)}}e[e.length]=new Bkb(b,c);++a.c;dob(a.b);return null}
function aF(a,b){var c,d,e;c=new Mfb;Kfb(c,(d=new gob(b.c),e=new Mfb,Kfb(e,fob(d)),e.a+=' ',Kfb(e,b.b),e.a+='\n',Kfb(e,b.a.Tf()),e.a+=': ',e.a));Kfb(c,b.d);if(a.a&&!!b.e){c.a+='\n';wf(b.e,new fF(c),'','')}return c.a}
function gtb(a,b,c,d,e,f){var g,h,i,j,k;if(dr(a)===dr(c)){a=a.slice(b,b+e);b=0}i=c;for(h=b,j=b+e;h<j;){g=$wnd.Math.min(h+10000,j);e=g-h;k=a.slice(h,g);k.splice(0,0,d,f?e:0);Array.prototype.splice.apply(i,k);h=g;d+=e}}
function Ne(a){if(!a.vd()){throw aE(new Beb("Should only call onDetach when the widget is attached to the browser's document"))}try{a.Ad();hp(a,false)}finally{try{a.td()}finally{JF();a.Yc.__listener=null;a.Uc=false}}}
function FW(a){var b,c;if(a.ob<a.ib.a.length){while(a.ib.a.length>a.ob){bh(Tkb(a.ib,a.ib.a.length-1))}}else{for(c=a.ib.a.length+1;c<=a.ob;c++){b=Ri($doc);xh(b,I$(c)+$xb);b.className=Zxb+c||'';Nkb(a.ib,b);Wg(a.Oc,b)}}}
function c$(a,b){var c,d,e,f;if(Tib(a.a.e,b)){return Sib(a.a.e,b)}d=Sib(dQ(a.a).c,b);c=ET(d,ph(a.a.f,'appId'));f=new fZ(uyb+d,c,a.a.f);Vib(a.a.e,b,f);e=new CO;BO(e,rQ(a.a));e.b=f;e.a=b;cG(f.a,e);WF(f.a,6272);return f}
function U1(a){if(a.nodeType!=1){return {}}if($wnd.document.defaultView&&$wnd.document.defaultView.getComputedStyle){return $wnd.document.defaultView.getComputedStyle(a,null)}if(a.currentStyle){return a.currentStyle}}
function Jp(a,b,c){var d;if(!b){throw aE(new afb('Cannot add a handler with a null type'))}if(!c){throw aE(new afb('Cannot add a null handler'))}a.b>0?Ip(a,new CM(a,b,c)):(d=Np(a,b,null),d.add(c));return new BM(a,b,c)}
function O0(a,b,c,d){var e;if(!a.V._){e=YU(a.V,b,c);if(e!=null&&e.length!=0){aP(a.u,e);TX(a.V,'='+e)}else{bP(a.u,gV(a.V,b,c))}}a.e=FV(a.V,b,c);a.o?NU(a.V,c$(a.p,oV(a.V))):dP(a.u,!a.e);d!=null?fP(a.u,d):fP(a.u,D$(b,c))}
function q1(a){o1();if(a.webkitAnimationName)return a.webkitAnimationName;if(a.animationName)return a.animationName;if(a.mozAnimationName)return a.mozAnimationName;if(a.oAnimationName)return a.oAnimationName;return ''}
function Ch(a,b){var c,d,e;c=a.indexOf(b);while(c!=-1){if(c==0||(vtb(c-1,a.length),a.charCodeAt(c-1)==32)){d=c+b.length;e=a.length;if(d==e||d<e&&(vtb(d,a.length),a.charCodeAt(d)==32)){break}}c=a.indexOf(b,c+1)}return c}
function eN(a){var b;if(!a.t&&(b=(F1(),!E1&&(E1=new O1),F1(),E1),b.a.j&&M1(b))){a.t=Ti($doc);a.t.style[gvb]=(Cl(),ivb);a.t.style['borderStyle']=(_j(),Pub);a.t.tabIndex=-1;a.t.frameBorder=0;a.t.marginHeight=0}return a.t}
function eP(a,b){var c,d,e;Fj(ie(a.B));dJ(a.B,'');if(!!b&&b.a.length!=0){ue(a.B,true);ue(a.C,true);for(d=new slb(b);d.a<d.c.a.length;){c=qlb(d);dJ(a.B,c)}lP(a,(e=gL(a.a),e==null?'':e))}else{ue(a.B,false);ue(a.C,false)}}
function Q5(a,b){var c,d,e,f,g,h,i;if(a.b){for(f=(i=(new bkb(a.a.p.a)).a.$f().Oe(),new hkb(i));f.a.Ye();){c=(h=f.a.Ze(),h.gg());th(c,Pzb)}Yib(a.a.p.a)}for(d=b,e=0,g=d.length;e<g;++e){c=d[e];eh(c,Pzb);a.b&&sob(a.a.p,c)}}
function sgb(a){var b,c;if(a>-140737488355328&&a<140737488355328){if(a==0){return 0}b=a<0;b&&(a=-a);c=er($wnd.Math.floor($wnd.Math.log(a)/0.6931471805599453));(!b||a!=$wnd.Math.pow(2,c))&&++c;return c}return tgb(hE(a))}
function x0(a,b){if(a.T!=b){a.T=b;b?Be((JF(),a.Yc),'protected',true):Be((JF(),a.Yc),'protected',false);if(a.C){if(b){if(a.o){a.o=false;tW(a.V)}}else{a.e=false;PR(a.Q);if(a.o){vab(a.W,a.V.sc,a.V.rc,false);qb(a.s,200)}}}}}
function ri(a,b){if(Element.prototype.getBoundingClientRect){return b.getBoundingClientRect().top+a.scrollTop|0}else{var c=b.ownerDocument;return c.getBoxObjectFor(b).screenY-c.getBoxObjectFor(c.documentElement).screenY}}
function qi(a,b){if(Element.prototype.getBoundingClientRect){return b.getBoundingClientRect().left+a.scrollLeft|0}else{var c=b.ownerDocument;return c.getBoxObjectFor(b).screenX-c.getBoxObjectFor(c.documentElement).screenX}}
function JU(a,b,c){var d,e,f,g,h,i,j;h=new vob;for(e=new sjb((new kjb(a.Bc)).a);e.b;){d=rjb(e);g=d.hg();sob(h,''+g.b)}j=gq(QB,_tb,2,Zib(h.a),6,1);fib(h,j);i=xT(b,j);for(f=0;f<i.length;f++){Rkb(c,i[f],0)!=-1||Nkb(c,i[f])}}
function p1(b,c){o1();var d=Ftb(function(a){c.Ef(a)});d.listener=c;b.addEventListener(n1,d,false);!b._vaadin_animationend_callbacks&&(b._vaadin_animationend_callbacks=[]);b._vaadin_animationend_callbacks.push(d);return d}
function PS(a,b){switch(JF(),$G((Gh(),b).type)){case _vb:if(b.touches.length>1){return}case 4:WR(a.F,b);break;case 8:case Rtb:case bwb:SF(a.Yc);case 8192:oS(a.F);break;case 64:cS(a.F,b);break;case awb:cS(a.F,b);Fh.Xd(b);}}
function wlb(a,b){var c,d,e;if(dr(a)===dr(b)){return true}if(a==null||b==null){return false}if(a.length!=b.length){return false}for(c=0;c<a.length;++c){d=a[c];e=b[c];if(!(d==e||d!=null&&jfb(d,e))){return false}}return true}
function CN(a){TJ();var b,c,d;c=null.tg();c+='-overlays';b=(JF(),mj($doc,c));if(!b){b=Ri($doc);b.id=c;d=rQ(a.d).Xc.nd().className||'';d!=null&&d.length!=0&&eh(b,d);eh(b,'v-overlay-container');Wg(ie((QK(),UK())),b)}return b}
function ZS(a,b,c,d,e){a.J.style[ovb]=(b&&!a.H?(wm(),vm):(wm(),um)).ke();a.p.style[ovb]=(e&&!a.n?(wm(),vm):(wm(),um)).ke();a.A.style[ovb]=(c&&!a.v?(wm(),vm):(wm(),um)).ke();a.d.style[ovb]=(d&&!a.b?(wm(),vm):(wm(),um)).ke()}
function A5(a){if(a.c<0){r5(a,0,a.c);a.c=0}else if(a.c>((a.q.scrollHeight||0)|0)-(a.q.clientHeight|0)){r5(a,((a.q.scrollHeight||0)|0)-(a.q.clientHeight|0),a.c);a.c=((a.q.scrollHeight||0)|0)-(a.q.clientHeight|0)}else{w5(a)}}
function hT(a,b){var c,d,e;d=kY(b);e=(Gh(),d).getAttribute($ub)||'';if(!Kh(d)){return}if(kh(Kh(d)).indexOf(wxb)!=-1&&e!=null&&e.indexOf(xxb)!=-1){c=a.c.wb;if(zT(e)==0){AT(c,e);Y$(a.c.a,c.a,c.b,Fh.be(d))}b.stopPropagation()}}
function UU(a){var b,c,d,e,f;b=new Ykb(a.Nc);for(e=new slb(a.Rc);e.a<e.c.a.length;){c=qlb(e);Okb(b,c)}for(f=new slb(a.d);f.a<f.c.a.length;){c=qlb(f);Okb(b,c)}for(d=new slb(a.kc);d.a<d.c.a.length;){c=qlb(d);Okb(b,c)}return b}
function t1(){var a=document.createElement(Eyb);var b={'animationName':Fyb,'OAnimationName':'oAnimationEnd','MozAnimation':Fyb,'WebkitAnimation':'webkitAnimationEnd'};for(var c in b){if(a.style[c]!==undefined){return b[c]}}}
function lW(a,b,c){var d,e,f;if(b.row1<=a.Tc&&b.row2>a.Tc){f=a.a.V.W;e=$R(f,b.row1,a.Tc+1);d=$R(f,a.Tc+1,b.row2+1)+1-((a.zc.scrollTop||0)|0);if(d>0){e+=d;c.d.style[_wb]=''}else{c.d.style[_wb]='0'}c.d.style[Qub]=e+(im(),rwb)}}
function sM(){function b(a){return parseInt(a[1])*evb+parseInt(a[2])}
var c=navigator.userAgent;if(c.indexOf('Macintosh')!=-1){var d=/rv:([0-9]+)\.([0-9]+)/.exec(c);if(d&&d.length==3){if(b(d)<=1008){return true}}}return false}
function GW(a){var b,c;if(a.Tc<a.jb.a.length){while(a.jb.a.length>a.Tc){bh(Tkb(a.jb,a.jb.a.length-1))}}else{for(b=a.jb.a.length+1;b<=a.Tc;b++){c=Ri($doc);c.innerHTML=''+b+$xb||'';c.className=_xb+b||'';Nkb(a.jb,c);Wg(a.Oc,c)}}}
function pX(a,b,c){var d,e,f,g;d=Ewb+b+Fwb+c;if(Tib(a.b,d)){return}g=fV(a,d);if(g){e=g.d}else{e=WU(a,b,c).d;SN(a.q,Kh((Gh(),e)))}ON(a.q,Sib(a.i,d));PN(a.q,Sib(a.r,d));f=a.tb.contains(d)?a.ub:null;RN(a.q,f);TN(a.q,e,c,b);a.j=d}
function xT(a,b){var c=[];var d=a.sheet.cssRules?a.sheet.cssRules:a.sheet.rules;for(var e=0;e<d.length;e++){var f=d[e];for(var g=0;g<b.length;g++){f['selectorText'].indexOf('.row'+b[g]+',')!==-1&&c.push(f['cssText'])}}return c}
function kgb(a){return a.e<=-32||a.e>(a.d>0?a.d:$wnd.Math.floor((a.a-1)*xAb)+1)?0:Ugb(a.e==0||a.a==0&&a.f!=-1?(!a.c&&(a.c=mhb(a.f)),a.c):a.e<0?Vgb((!a.c&&(a.c=mhb(a.f)),a.c),aib(-a.e)):Mgb((!a.c&&(a.c=mhb(a.f)),a.c),aib(a.e)))}
function Xhb(a,b,c,d,e){var f,g,h,i;if(dr(a)===dr(b)&&d==e){bib(a,d,c);return}for(h=0;h<d;h++){g=0;f=a[h];for(i=0;i<e;i++){g=bE(bE(nE(cE(f,yAb),cE(b[i],yAb)),cE(c[h+i],yAb)),cE(wE(g),yAb));c[h+i]=wE(g);g=sE(g,32)}c[h+e]=wE(g)}}
function U(a,b){var c,d,e;c=a.r;d=b>=a.t+a.k;if(a.p&&!d){e=(b-a.t)/a.k;a.fd(a.bd(e));return a.o&&a.r==c}if(!a.p&&b>=a.t){a.p=true;a.ed();if(!(a.o&&a.r==c)){return false}}if(d){a.o=false;a.p=false;a.dd();return false}return true}
function zV(a,b){var c,d,e,f;e=a.a.L;f=b-e;c=b+a.oc+e;f<0&&(f=0);d=a.a.O;while(a.Ab<c&&a.zb<d){if(a.eb+lV(a,a.db)<f){a.eb+=lV(a,a.db);++a.db}++a.zb;a.Ab+=lV(a,a.zb)}while(f>a.eb+lV(a,a.db)&&a.db<d){a.eb+=lV(a,a.db);++a.db}IW(a)}
function JE(a,b){var c=$wnd;if(a===''){return c}var d=a.split('.');!(d[0] in c)&&c.execScript&&c.execScript('var '+d[0]);if(b){var e=b.prototype.qg;e.e=b}for(var f;d.length&&(f=d.shift());){c=c[f]=c[f]||!d.length&&b||{}}return c}
function zi(){var a=/rv:([0-9]+)\.([0-9]+)(\.([0-9]+))?.*?/.exec(navigator.userAgent.toLowerCase());if(a&&a.length>=3){var b=parseInt(a[1])*1000000+parseInt(a[2])*evb+parseInt(a.length>=5&&!isNaN(a[4])?a[4]:0);return b}return -1}
function jI(a,b){var c;if(a.bb){throw aE(new Beb('Composite.initWidget() may only be called once.'))}if(!b){throw aE(new afb('widget cannot be null'))}Zq(b,179)&&b;Oe(b);c=(JF(),b.Yc);pe(a,c);(IK(),QF(c))&&JK(c,a);a.bb=b;Qe(b,a)}
function rZ(a,b,c){var d,e;switch(c.type){case 'IMAGE':u$((!a.D&&(a.D=new S0),a.D),b,new XI(z1(a.F,b)),c);break;case 'COMPONENT':d=ET(b,ph(a.f,'appId'));e=new fZ('overlay-component-'+b,d,a.f);u$((!a.D&&(a.D=new S0),a.D),b,e,c);}}
function Keb(a){var b,c,d;if(a<0){return 0}else if(a==0){return 32}else{d=-(a>>16);b=d>>16&16;c=16-b;a=a>>b;d=a-256;b=d>>16&8;c+=b;a<<=b;d=a-4096;b=d>>16&4;c+=b;a<<=b;d=a-Ztb;b=d>>16&2;c+=b;a<<=b;d=a>>14;b=d&~(d>>1);return c+2-b}}
function t6(a,b){var c;if(a.a!=null){(!a.D&&(a.D=qQ(a)),a.D).Xc.qd(a.a,false);th(CN(a.F),a.a)}a.a=b;if(b!=null){(!a.D&&(a.D=qQ(a)),a.D).Xc.qd(b,true);eh(CN(a.F),a.a);z6(b)}null.tg();c=new l5;!!a.G&&Dp(a.G,c);Rlb();amb();null.tg()}
function Wgb(a,b){var c;if(b<0){throw aE(new Cdb('Negative exponent'))}if(b==0){return Dgb}else if(b==1||Ogb(a,Dgb)||Ogb(a,Hgb)){return a}if(!$gb(a,0)){c=1;while(!$gb(a,c)){++c}return Vgb(ihb(c*b),Wgb(Zgb(a,c),b))}return _hb(a,b)}
function gK(){yI.call(this);this.C=new sK;this.F=false;this.H=-1;this.L=new CK(this);this.N=-1;Wg((JF(),this.Yc),SJ.cf());this.We(0,0);SJ.ef((null,mh(this.Yc))).className='gwt-PopupPanel';SJ.df(NF(this.Yc)).className='popupContent'}
function tQ(a,b,c){var d;if(!!a.r&&!!a.r.j){return}J2((JF(),b.Yc),false);if((F1(),!E1&&(E1=new O1),F1(),E1).a.t==5){return}j2(c.a,b.Yc);yj(c.a);a.r=new a5(a);d=vj(c.a)[0];a.B=ai((Gh(),d).clientX||0);a.C=ai(d.clientY||0);qb(a.r,500)}
function ncb(a,b){var c,d,e,f,g;g=b.indexOf('; cros ');if(g==-1){return}d=mfb(b,Bfb(41),g);if(d==-1){return}c=d;while(c>=g&&(vtb(c,b.length),b.charCodeAt(c)!=32)){--c}if(c==g){return}e=b.substr(c+1,d-(c+1));f=ufb(e,'\\.',0);ocb(a,f)}
function Y$(a,b,c,d){var e,f;if(a.V.sc!=c&&a.V.rc!=b){X$(a,b,c,false,false,true)}else{e=WU(a.V,b,c);d=e.o;a.b=d;GO(a.u);d=(f=gL(a.u.j),f==null?'':f)}a.t=false;A$(a);if(!a.e){if(!a.B&&!a.o){a.B=true;uX(a.V,true,d);a.u.u=true;hP(a.u)}}}
function QR(a,b,c,d){F$(a.d);a.c.C||(a.c.C=true,undefined);if(!bS(a.c.yc)){mX(a.c,true);DU(a.c)}lX(a.c,b,c);eY(a.c,b,b,c,c);d?cY(a.c,b,d.col2,c,d.row2,true):cY(a.c,b,b,c,c,true);PR(a);O0(a.d,b,c,null);vab(a.d.W,c,b,true);qb(a.d.s,200)}
function t5(a,b){var c,d,e,f,g;g=(Gh(),b).target;for(d=(f=(new bkb(a.p.a)).a.$f().Oe(),new hkb(f));d.a.Ye();){c=(e=d.a.Ze(),e.gg());if(Fh.ee(c,g)&&((c.scrollHeight||0)|0)>(c.clientHeight|0)){a.q=c;a.g=G5(a.q);return true}}return false}
function GE(a,b,c){var d=EE,h;var e=d[a];var f=e instanceof Array?e[0]:null;if(e&&!f){_=e}else{_=(h=b&&b.prototype,!h&&(h=EE[b]),IE(h));_.rg=c;!b&&(_.sg=LE);d[a]=_}for(var g=3;g<arguments.length;++g){arguments[g].prototype=_}f&&(_.qg=f)}
function s$(){TJ();AN.call(this);this.e=new W5(this);this.b=new h6(100,new Z5(this));cK(this,this.e);SJ.ef((JF(),JF(),mh(this.Yc))).className=oxb;wh(this.Yc,hj($doc));Je(this,new T5(this),kp?kp:(kp=new Bn));this.Yc['id']='PID_VAADIN_CM'}
function vV(a,b){var c,d,e,f;c=a.a.j;d=b-c;f=b+a.pc+c;d<0&&(d=0);e=a.a.i;while(a.yb<f&&a.xb<e){if(a.cb+K$(a.a,a.bb)<d){a.cb+=K$(a.a,a.bb);++a.bb}++a.xb;a.yb+=K$(a.a,a.xb)}while(d>a.cb+K$(a.a,a.bb)&&a.bb<e){a.cb+=K$(a.a,a.bb);++a.bb}DW(a)}
function He(a,b){var c=(a.className||'').split(/\s+/);if(!c){return}var d=c[0];var e=d.length;c[0]=b;for(var f=1,g=c.length;f<g;f++){var h=c[f];h.length>e&&h.charAt(e)=='-'&&h.indexOf(d)==0&&(c[f]=b+h.substring(e))}a.className=c.join(' ')}
function vS(a,b){var c;this.M=new yS(this);this.q=a;this.Q=b;this._=b.Sc;this.b=new bT(this);jI(this,this.b);_S(this.b);se(this.b,pxb,true);lS(this,false);this.B=new NS(this);se(this.B,pxb,true);LS(this.B);c=b.zc;YS(this.b,c);JS(this.B,c)}
function w4(b,c){var d,e,f,g;d=c.f;e=b.a;!e&&(e=O(d));f=new A4(e);try{q4(new r4(f,b.b),d,jq(eq(KB,1),Jtb,1,5,[]))}catch(a){a=_D(a);if(Zq(a,80)){g=a;throw aE(new Mf("Couldn't invoke @OnStateChange method "+f.b+'.'+b.b,g))}else throw aE(a)}}
function fS(a,b){a.r=b;if(b>0&&!a.a){a.a=new bT(a);YS(a.a,a.Q.c);ue(a.a,false);_S(a.a);se(a.a,mxb,true);a.A=new NS(a);JS(a.A,a.Q.c);KS(a.A,false);LS(a.A);se(a.A,mxb,true)}else if(b==0&&!!a.a){QS(a.a);a.a=null;bh(a.A.k);a.A=null}uS(a);sS(a)}
function kS(a,b){a.ab=b;if(b>0&&!a.X){a.X=new bT(a);YS(a.X,a.Q.Qc);ue(a.X,false);_S(a.X);se(a.X,'top-right',true);a.F=new NS(a);JS(a.F,a.Q.Qc);KS(a.F,false);LS(a.F);se(a.F,nxb,true)}else if(b==0&&!!a.X){QS(a.X);a.X=null;bh(a.F.k);a.F=null}uS(a);sS(a)}
function uS(a){if(a.ab>0&&a.r>0&&!a.W){a.W=new bT(a);YS(a.W,a.Q.Oc);ue(a.W,false);_S(a.W);se(a.W,nxb,true);a.D=new NS(a);JS(a.D,a.Q.Oc);KS(a.D,false);LS(a.D);se(a.D,nxb,true)}else if(!!a.W&&(a.ab==0||a.r==0)){QS(a.W);a.W=null;bh(a.D.k);a.D=null}}
function $gb(a,b){var c,d,e;if(b==0){return (a.a[0]&1)!=0}if(b<0){throw aE(new Cdb('Negative bit address'))}e=b>>5;if(e>=a.d){return a.e<0}c=a.a[e];b=1<<(b&31);if(a.e<0){d=Rgb(a);if(e<d){return false}else d==e?(c=-c):(c=~c)}return (c&b)!=0}
function wQ(a){!a.D&&(a.D=new S0);if(!!a.D&&kI((!a.D&&(a.D=new S0),a.D))){Oe((!a.D&&(a.D=new S0),a.D));Nrb(Qrb((Rdb(Hz),Hz.k)),'Widget is still attached to the DOM after the connector ('+q2(a)+') has been unregistered. Widget was removed.')}}
function Iq(a,b){var c,d,e,f,g;b&=63;c=a.h;d=(c&Pvb)!=0;d&&(c|=-1048576);if(b<22){g=c>>b;f=a.m>>b|c<<22-b;e=a.l>>b|a.m<<22-b}else if(b<44){g=d?Stb:0;f=c>>b-22;e=a.m>>b-22|c<<44-b}else{g=d?Stb:0;f=d?Ovb:0;e=c>>b-44}return oq(e&Ovb,f&Ovb,g&Stb)}
function Iob(a,b){var c,d,e,f,g,h;g=b==null?0:(f=Q(b),f|0);d=(c=a.a.get(g),c==null?new Array:c);for(h=0;h<d.length;h++){e=d[h];if(pob(b,e.gg())){if(d.length==1){d.length=0;Pob(a.a,g)}else{d.splice(h,1)}--a.c;dob(a.b);return e.hg()}}return null}
function zX(a,b){var c,d,e;aG((JF(),a.Yc));a.Ub.className=Wxb;ie(a.yc).style[Lwb]='';th(a.Vb,'row'+a._b);if(a.Zb){c=new qob;e=b-a.Sb;d=igb(zgb(e/a.Lb*72));d<0&&(d=0);d!=N$(a.a,a._b)&&Uib(c,Neb(a._b),new teb(d));c.a.c+c.b.c==0||t_(a.a,c)}a._b=-1}
function rK(){var a,b,c,d;null.tg();d=(FG(),rj($doc).clientWidth|0);c=rj($doc).clientHeight|0;null.tg((Ak(),Pub));null.tg((im(),swb));null.tg(swb);b=qj($doc);a=nj($doc);null.tg($wnd.Math.max(b,d)+rwb);null.tg($wnd.Math.max(a,c)+rwb);null.tg(tvb)}
function UZ(a){var b,c,d,e,f,g;d=new Wkb;g=rQ(a.a.a);e=a.a.a.j;for(c=new slb(a.b);c.a<c.c.a.length;){b=qlb(c);f=new nZ(a,e,b.key,b.type,g);jZ(f,b.caption);kZ(f,sZ(a.a.a,b.key));d.a[d.a.length]=f}return Vkb(d,gq(Mz,{731:1,3:1},127,d.a.length,0,1))}
function Hhb(a,b,c,d,e){var f,g,h;f=0;g=0;for(h=0;h<d;h++){f=(Uhb(),bE(nE(cE(c[h],yAb),cE(e,yAb)),cE(wE(f),yAb)));g=bE(tE(cE(a[b+h],yAb),cE(f,yAb)),g);a[b+h]=wE(g);g=rE(g,32);f=sE(f,32)}g=bE(tE(cE(a[b+d],yAb),f),g);a[b+d]=wE(g);return wE(rE(g,32))}
function XT(a,b,c){var d,e,f,g,h;if(c){a.c.style[Kwb]='';a.s=0;a.t=0}for(e=b.length;e<a.u.length;e++){bh(a.u[e])}Vf(a.u,b.length);for(d=0;d<b.length;d++){f=a.u[d];if(f){h=f;WT(h,b[d])}else{g=NT(b[d]);Wg(a.c,g);a.u[d]=g}}a.r>=a.u.length&&(a.r=-1);YT(a)}
function eeb(a){if(a.Vf()){var b=a.c;b.Wf()?(a.k='['+b.j):!b.Vf()?(a.k='[L'+b.Tf()+';'):(a.k='['+b.Tf());a.b=b.Sf()+'[]';a.i=b.Uf()+'[]';return}var c=a.g;var d=a.d;d=d.split('/');a.k=heb('.',[c,heb('$',d)]);a.b=heb('.',[c,heb('.',d)]);a.i=d[d.length-1]}
function TN(a,b,c,d){a.c=b;a.d=c;a.b=d;(JF(),a.Yc).style[ovb]=Bvb;!!a.t&&(a.t.style[ovb]=Bvb,undefined);a.i.style[ovb]=(wm(),Bvb);oN(a);a.k=oh(a.Yc,qwb);a.n=oh(a.Yc,Oub);JN(a);a.Yc.style[ovb]=Avb;!!a.t&&(a.t.style[ovb]=Avb,undefined);a.i.style[ovb]=Avb}
function SR(a,b,c,d,e,f,g,h,i){var j,k;O0(a.d,c,d,b);a.c.C||(a.c.C=true,undefined);if(!bS(a.c.yc)){mX(a.c,true);DU(a.c)}j=a.c.rc;k=a.c.sc;if(j!=c||k!=d){lX(a.c,c,d);PR(a)}eY(a.c,e,f,g,h);cY(a.c,e,f,g,h,true);i&&!DV(a.c,e,f,g,h)&&NW(a.c,e,f,g,h);SU(a.c)}
function wF(a){vF();if(!iF(rF,a)){return a}a.indexOf('&')!=-1&&(a=hF(pF,a,'&amp;'));a.indexOf('<')!=-1&&(a=hF(sF,a,'&lt;'));a.indexOf('>')!=-1&&(a=hF(qF,a,'&gt;'));a.indexOf('"')!=-1&&(a=hF(tF,a,'&quot;'));a.indexOf("'")!=-1&&(a=hF(uF,a,'&#39;'));return a}
function Le(a){var b;if(a.vd()){throw aE(new Beb("Should only call onAttach when the widget is detached from the browser's document"))}a.Uc=true;JF();bH(a.Yc,a);b=a.Vc;a.Vc=-1;b>0&&(a.Vc==-1?WF(a.Yc,b|(a.Yc.__eventBits||0)):(a.Vc|=b));a.sd();a.zd();hp(a,true)}
function AQ(a,b,c){var d,e,f;e='%'.length;jfb(b.substr(b.length-e,e),'%')!=ifb(a.q,'%');d='%'.length;jfb(c.substr(c.length-d,d),'%')!=ifb(a.p,'%');a.q=b;a.p=c;f=a.zf();f.qd('v-has-width',!Jcb(a.yf()));f.qd('v-has-height',!Icb(a.yf()));a.zf().rd(b);a.zf().pd(c)}
function oS(a){var b,c,d,e;a.C=false;hS(a,false);a.N&&pS(a);if(a.j){w_(a.q,a.G,a.I)}else if(a.s){b=$wnd.Math.min(a.e,a.G);c=$wnd.Math.max(a.f,a.H);d=$wnd.Math.min(a.K,a.I);e=$wnd.Math.max(a.L,a.J);b<=c&&d<=e&&x_(a.q,b,c,d,e)}th(ie(a.Q),'selecting');jS(a,false)}
function uW(a,b,c){var d,e,f;if(!c){return}th((JF(),c.Yc),Pxb);if((F1(),!E1&&(E1=new O1),F1(),E1).a.g){(!db&&(db=eb()?new fb:new nb),db).hd(new dZ(c),null)}else{Qe(c,null);Oe(c)}if(a.Db){f=a.wb;AT(f,b);e=WU(a,f.a,f.b);if(e){d=Sib(a.e,b);LM(e,!d?null:d.value)}}}
function u5(a,b){var c;a.r&&S(a.i);c=(Gh(),b).touches[0];if(t5(a,c)){Hrb(Qrb((Rdb(Qz),Qz.k)),'TouchDelegate takes over');b.stopPropagation();a.d=$F(a);p5=a;a.o=ai(c.clientY||0);a.s[0]=a.o;a.b[0]=Xf();a.k=1;a.n=v5(a);Hrb(Qrb((Rdb(Qz),Qz.k)),'ST'+a.n);a.j=false}}
function Xgb(a,b){var c,d,e,f,g,h;if(b.e==0){throw aE(new Cdb(zAb))}h=a.d;c=b.d;if((h!=c?h>c?1:-1:Mhb(a.a,b.a,h))==-1){return a}f=c;e=gq(ir,Rxb,17,f,15,1);if(f==1){e[0]=Ihb(a.a,h,b.a[0])}else{d=h-c+1;e=Bhb(null,d,a.a,h,b.a,c)}g=new bhb(a.e,f,e);Lgb(g);return g}
function UW(a,b,c){var d,e,f,g,h,i,j;if(b){AT(a.wb,c);j=a.wb.b;i=a.wb.a;h=fV(a,c);d=h?h:WU(a,i,j);e=new VN(a,Xg(d.d));ON(e,Sib(a.i,c));PN(e,Sib(a.r,c));g=a.tb.contains(c)?a.ub:null;RN(e,g);UN(e,d.d,j,i);Vib(a.b,c,e)}else{f=Xib(a.b,c);!!f&&(fN(f,false),bh(f.i))}}
function q$(a,b,c){var d,e,f,g,h,i;h=UZ(a.a);if(h==null||h.length==0){return}a.d=b;a.f=c;pJ(a.e);for(e=h,f=0,g=e.length;f<g;++f){d=e[f];nJ(a.e,new QJ((i=new Mfb,i.a+='<div>',Kfb(i,d.e),i.a+='<\/div>',i.a),d))}K2(ie(a.e));a.c=A2();_J(a,'');hN(a,1);bK(a,new _5(a))}
function AV(a,b){var c,d,e,f;e=a.a.L;f=b-e;c=b+a.oc+e;f<0&&(f=0);d=a.Tc+1;while(a.eb>f&&a.db>d){if(a.Ab-lV(a,a.zb)>c){a.Ab-=lV(a,a.zb);--a.zb}--a.db;a.eb-=lV(a,a.db)}if(a.eb<=0||a.db<=1){a.eb=0;a.db=d}while(c<a.Ab-lV(a,a.zb)&&a.zb>1){a.Ab-=lV(a,a.zb);--a.zb}IW(a)}
function ig(g){bg();function h(a,b,c,d,e){if(!e){e=a+' ('+b+':'+c;d&&(e+=':'+d);e+=')'}var f=Hf(e);kg(f,false)}
;function i(a){var b=a.onerror;if(b&&!g){return}a.onerror=function(){h.apply(this,arguments);b&&b.apply(this,arguments);return false}}
i($wnd);i(window)}
function F2(a){var b=a.ownerDocument.defaultView.getComputedStyle(a);var c=b.width;if(c==Bwb){return E2(a)}var d=parseFloat(c);var e=parseFloat(b.borderLeftWidth)+parseFloat(b.borderRightWidth);var f=parseFloat(b.paddingLeft)+parseFloat(b.paddingRight);return d+e+f}
function C2(a){var b=a.ownerDocument.defaultView.getComputedStyle(a);var c=b.height;if(c==Bwb){return B2(a)}var d=parseFloat(c);var e=parseFloat(b.borderTopWidth)+parseFloat(b.borderBottomWidth);var f=parseFloat(b.paddingTop)+parseFloat(b.paddingBottom);return d+e+f}
function jW(a){var b,c,d,e,f,g,h;g=new bkb(a.a.f);b=new qob;Wg(a.zc,a.hb);yh(a.hb,Xxb);for(f=(h=g.a.$f().Oe(),new hkb(h));f.a.Ye();){e=(d=f.a.Ze(),d.gg());vh(a.hb,'cell cs'+e);c=a.hb.clientWidth|0;Uib(b,e,new teb(igb(hgb(new ogb(c),new ogb(10)))))}bh(a.hb);M_(a.a,b)}
function wq(a){var b,c,d;c=a.l;if((c&c-1)!=0){return -1}d=a.m;if((d&d-1)!=0){return -1}b=a.h;if((b&b-1)!=0){return -1}if(b==0&&d==0&&c==0){return -1}if(b==0&&d==0&&c!=0){return Leb(c)}if(b==0&&d!=0&&c==0){return Leb(d)+22}if(b!=0&&d==0&&c==0){return Leb(b)+44}return -1}
function WU(a,b,c){var d,e,f,g;if(c<=a.Tc&&(b>=a.bb&&b<=a.xb||b<=a.ob)||b<=a.ob&&(c>=a.db&&c<=a.zb||c<=a.Tc)){return dV(a,b,c)}else{e=b-a.bb;f=c-a.db;if(e<0||f<0){return null}g=a.kc.a.length>f;if(g){d=Qkb(a.kc,f).a.length>e;if(d){return Qkb(Qkb(a.kc,f),e)}}}return null}
function AJ(a,b){var c,d;if(b==a.g){return}if(a.g){OJ(a.g);if(a.i){d=OF(ie(a.g));JF();if(HF.Ge(d)==2){c=HF.Fe(d,1);Be(c,pwb,false)}}}if(b){se(b,ye((JF(),b.Yc))+'-'+owb,true);if(a.i){d=OF(b.Yc);if(HF.Ge(d)==2){c=HF.Fe(d,1);Be(c,pwb,true)}}Qd();pc(a.Yc,new ec(b.Yc))}a.g=b}
function SM(a){a.d.style[uwb]=(rl(),Bvb);if(a.o==null||a.o.length==0){yh(a.d,'');a.d.style[pvb]=''}else{PV(a.n,Ewb+a.c+Fwb+a.k)&&!Zq(a,146)?(a.d.style[pvb]='',undefined):(a.d.style[pvb]='1',undefined);a.f&&a.gf()>0&&XV(a.n,a.b,a.o)>a.gf()?yh(a.d,'###'):yh(a.d,a.o)}GM(a)}
function hL(a,b,c){if(!a.Uc){return}if(c<0){throw aE(new Edb('Length must be a positive integer. Length: '+c))}if(b<0||c+b>ph((JF(),a.Yc),xwb).length){throw aE(new Edb('From Index: '+b+'  To Index: '+(b+c)+'  Text Length: '+ph((JF(),a.Yc),xwb).length))}zM((JF(),a.Yc),b,c)}
function oO(a){var b,c,d,e,f,g,h,i,j;if(a.a.C){g=a.a.yc.f;h=a.a.yc.e;j=a.a.yc.K;i=a.a.yc.L;f=new Mfb;for(e=j;e<=i;e++){for(c=h;c<=g;c++){b=ZU(a.a,c,e);b!=null&&(f.a+=''+b,f);c!=g&&(f.a+='\t',f)}e!=i&&(f.a+='\n',f)}d=f.a;return d}return "non-continous selection, can't copy"}
function SO(a,b){var c,d,e,f,g,h,i;c=a.d;if(!c){i='';for(f=a.t.S,g=0,h=f.length;g<h;++g){e=f[g];i+=e+'|'}i=wfb(i,0,i.length-1);d='^(('+i+')!){0,1}';d+='([A-Za-z]{1,3}[0-9]{1,7})';d+='(:([A-Za-z]{1,3}[0-9]{1,7})){0,1}';a.d=c=new RegExp(d);qb(new vP(a),2000)}return c.test(b)}
function uV(a,b){var c,d,e,f;c=a.a.j;d=b-c;f=b+a.pc+c;d<0&&(d=0);e=a.ob+1;while(a.cb>d&&a.bb>e){if(a.yb-K$(a.a,a.xb)>f){a.yb-=K$(a.a,a.xb);--a.xb}--a.bb;a.cb-=K$(a.a,a.bb)}if(a.cb<=0||a.bb<=1){a.cb=0;a.bb=e}while(f<a.yb-K$(a.a,a.xb)&&a.xb>1){a.yb-=K$(a.a,a.xb);--a.xb}DW(a)}
function CV(a){var b,c;b=new jT;b.c=a;b.a=a.vb;iT(b,a.Oc,a.Qc,a.c,a.zc);if(a.Sc&&(F1(),!E1&&(E1=new O1),F1(),E1).a.t==4){c=new h$(a);Ie(a,c,($o(),$o(),Zo));Ie(a,c,(Lo(),Lo(),Ko));Ie(a,c,(To(),To(),So));Ie(a,c,(Eo(),Eo(),Do))}a.Nb=$F(new YY(a));Ie(a,new $Y(a),(sn(),sn(),rn))}
function Cq(a){var b,c,d,e,f;if(isNaN(a)){return Tq(),Sq}if(a<-9223372036854775808){return Tq(),Qq}if(a>=9223372036854775807){return Tq(),Pq}e=false;if(a<0){e=true;a=-a}d=0;if(a>=Qtb){d=er(a/Qtb);a-=d*Qtb}c=0;if(a>=Rtb){c=er(a/Rtb);a-=c*Rtb}b=er(a);f=oq(b,c,d);e&&uq(f);return f}
function UO(a,b,c,d,e){var f;if(!a.f){return}if(a.k==-1){a.k=a.I.rc;a.n=a.I.sc}c?--a.n:d?++a.k:e?++a.n:--a.k;a.n==0&&(a.n=1);a.k==0&&(a.k=1);f=rV(a.I);a.n>f[2]-1&&(a.n=f[2]-1);a.k>f[3]-1&&(a.k=f[3]-1);if(b&&a.o!=-1);else{a.o=a.k;a.p=a.n}cP(a,a.o,a.p,a.k,a.n,false);QW(a.I,a.k,a.n)}
function qX(a,b){var c,d,e,f;if(a.T){for(f=(d=(new mkb(a.T)).a.$f().Oe(),new rkb(d));f.a.Ye();){e=(c=f.a.Ze(),c.hg());lkb(new mkb(b),e)||Oe(e)}}a.Tc>0&&a.ob>0&&rX(a,b,1,a.Tc,1,a.ob);a.Tc>0&&rX(a,b,1,a.Tc,a.bb,a.xb);a.ob>0&&rX(a,b,1,a.db,a.zb,a.ob);rX(a,b,a.bb,a.xb,a.db,a.zb);a.T=b}
function hY(a,b){var c,d,e;if(a.Nc.a.length!=0){d=new slb(b);while(d.a<d.c.a.length){c=qlb(d);MM(Qkb(a.Nc,(c.row-1)*a.ob+c.col-1),c.value,c.cellStyle,c.needsMeasure);e=Ewb+c.col+Fwb+c.row;gX(a,e,c.value,c.cellStyle,c.needsMeasure);c.value==null?Xib(a.e,e):Vib(a.e,e,c)}}ZX(a,false)}
function Vhb(a,b){Uhb();var c,d,e,f,g,h,i,j,k;if(b.d>a.d){h=a;a=b;b=h}if(b.d<63){return $hb(a,b)}g=(a.d&-2)<<4;j=Zgb(a,g);k=Zgb(b,g);d=Ohb(a,Ygb(j,g));e=Ohb(b,Ygb(k,g));i=Vhb(j,k);c=Vhb(d,e);f=Vhb(Ohb(j,d),Ohb(e,k));f=Jhb(Jhb(f,i),c);f=Ygb(f,g);i=Ygb(i,g<<1);return Jhb(Jhb(i,f),c)}
function eS(a,b){a.p=b;if(b){se(a.b,lxb,true);SS(a.b,b);if(a.W){SS(a.W,b);se(a.W,lxb,true)}if(a.X){SS(a.X,b);se(a.X,lxb,true)}if(a.a){SS(a.a,b);se(a.a,lxb,true)}jS(a,true)}else{se(a.b,lxb,false);!!a.W&&se(a.W,lxb,false);!!a.X&&se(a.X,lxb,false);!!a.a&&se(a.a,lxb,false);jS(a,false)}}
function nW(a){var b,c,d,e,f,g;for(c=(f=(new mkb(a.b)).a.$f().Oe(),new rkb(f));c.a.Ye();){b=(e=c.a.Ze(),e.hg());g=b.d;d=b.b;P$(a.a,d)||Q$(a.a,g)||!(d>=a.bb&&d<=a.xb&&g>=a.db&&g<=a.zb||d<=a.ob&&g<=a.Tc||d>a.ob&&d<=a.xb&&g<=a.Tc||g>a.Tc&&g<=a.zb&&d<=a.ob)?(fN(b,false),bh(b.i)):NN(b)}}
function zZ(a){if(!(!a.L&&(a.L=new m1),a.L).c){(!a.D&&(a.D=new S0),a.D).V.Fc&&E_((!a.D&&(a.D=new S0),a.D),a.e);V_((!a.D&&(a.D=new S0),a.D),null)}else if(!(!a.D&&(a.D=new S0),a.D).p){a.e=new qob;!a.d&&(a.d=new e$(a));V_((!a.D&&(a.D=new S0),a.D),a.d)}else{U$((!a.D&&(a.D=new S0),a.D))}}
function Ctb(a){var b,c,d,e;b=0;d=a.length;e=d-4;c=0;while(c<e){b=(vtb(c+3,a.length),a.charCodeAt(c+3)+(vtb(c+2,a.length),31*(a.charCodeAt(c+2)+(vtb(c+1,a.length),31*(a.charCodeAt(c+1)+(vtb(c,a.length),31*(a.charCodeAt(c)+31*b)))))));b=b|0;c+=4}while(c<d){b=b*31+hfb(a,c++)}b=b|0;return b}
function lcb(a,b){var c,d,e;d=b;e=a.length;while(d<e){c=(vtb(d,a.length),a.charCodeAt(d));Odb==null&&(Odb=new RegExp('[A-Z]','i'));if(!(Odb.test(String.fromCharCode(c))||(Ndb==null&&(Ndb=new RegExp('\\d')),Ndb.test(String.fromCharCode(c)))||c==95||c==46)){break}++d}return a.substr(b,d-b)}
function AZ(a){var b,c,d,e,f;e=(!a.L&&(a.L=new m1),a.L).bb;f=(!a.D&&(a.D=new S0),a.D);for(d=new slb(a.k);d.a<d.c.a.length;){b=qlb(d);Rkb(e,b,0)!=-1||UW(f.V,false,b)}if(e){for(c=new slb(e);c.a<c.c.a.length;){b=qlb(c);Rkb(a.k,b,0)!=-1||UW(f.V,true,b)}}a.k.a=gq(KB,Jtb,1,0,5,1);!!e&&Okb(a.k,e)}
function o$(a,b,c){var d,e;b=je(a.e);d=a.d;e=a.f;if(b+d>(FG(),rj($doc).clientWidth|0)){d=d-b;d<0&&(d=0)}c+e>(rj($doc).clientHeight|0)&&(e=$wnd.Math.max(0,(rj($doc).clientHeight|0)-c));e==0&&jN(a,(rj($doc).clientHeight|0)+rwb);lN(a,d,e);(JF(),a.Yc).style[gvb]=(Cl(),hvb);s2((og(),ng),new b6(a))}
function fob(a){var b,c,d;d=-a.a.getTimezoneOffset();b=(d>=0?'+':'')+(d/60|0);c=kob($wnd.Math.abs(d)%60);return (oob(),mob)[a.a.getDay()]+' '+nob[a.a.getMonth()]+' '+kob(a.a.getDate())+' '+kob(a.a.getHours())+':'+kob(a.a.getMinutes())+':'+kob(a.a.getSeconds())+' GMT'+b+c+' '+a.a.getFullYear()}
function OO(a){var b,c,d,e,f,g,h,i;if(a.A!=null){g=YO(a,a.A);if(!g){return}e=$wnd.Math.min(g.col1,g.col2);d=$wnd.Math.max(g.col1,g.col2);i=$wnd.Math.min(g.row1,g.row2);h=$wnd.Math.max(g.row1,g.row2);for(b=e;b<=d;b++){for(f=i;f<=h;f++){c=WU(a.I,b,f);!!c&&(c.d.style[Ywb]='',undefined)}}}a.A=null}
function $hb(a,b){var c,d,e,f,g,h,i,j,k,l,m;d=a.d;f=b.d;h=d+f;i=a.e!=b.e?-1:1;if(h==2){k=nE(cE(a.a[0],yAb),cE(b.a[0],yAb));m=wE(k);l=wE(sE(k,32));return l==0?new ahb(i,m):new bhb(i,2,jq(eq(ir,1),Rxb,17,15,[m,l]))}c=a.a;e=b.a;g=gq(ir,Rxb,17,h,15,1);Whb(c,d,e,f,g);j=new bhb(i,h,g);Lgb(j);return j}
function Zhb(a,b){Uhb();var c,d,e,f,g,h,i,j,k;j=a.e;if(j==0){return Igb(),Hgb}d=a.d;c=a.a;if(d==1){e=nE(cE(c[0],yAb),cE(b,yAb));i=wE(e);g=wE(sE(e,32));return g==0?new ahb(j,i):new bhb(j,2,jq(eq(ir,1),Rxb,17,15,[i,g]))}h=d+1;f=gq(ir,Rxb,17,h,15,1);f[d]=Yhb(f,c,d,b);k=new bhb(j,h,f);Lgb(k);return k}
function QU(a){var b,c,d,e,f,g;for(d=(g=(new bkb(a.u.a)).a.$f().Oe(),new hkb(g));d.a.Ye();){c=(e=d.a.Ze(),e.gg());if(c.a!=a.rc||c.b!=a.sc){b=WU(a,c.a,c.b);if(b){eh(b.d,Gxb);sob(a.t,b)}f=fV(a,Ewb+c.a+Fwb+c.b);if(f){sob(a.t,f);eh(f.d,Gxb)}}}if(a.nb){b=WU(a,a.nb.a,a.nb.b);!!b&&eh(b.d,Hxb)}PO(a.a.u)}
function vf(d,b){if(b instanceof Object){try{b.__java$exception=d;if(navigator.userAgent.toLowerCase().indexOf('msie')!=-1&&$doc.documentMode<9){return}var c=d;Object.defineProperties(b,{cause:{get:function(){var a=c.Gd();return a&&a.Ed()}},suppressed:{get:function(){return c.Fd()}}})}catch(a){}}}
function hH(){hH=HE;cH={_default_:nH,dragenter:mH,dragover:mH};eH={click:lH,dblclick:lH,mousedown:lH,mouseup:lH,mousemove:lH,mouseover:lH,mouseout:lH,mousewheel:lH,keydown:kH,keyup:kH,keypress:kH,touchstart:lH,touchend:lH,touchmove:lH,touchcancel:lH,gesturestart:lH,gestureend:lH,gesturechange:lH}}
function BK(a,b,c){var d;a.c=c;S(a);if(a.g){pb(a.g);a.g=null;yK(a)}a.a.M=b;fK(a.a);d=!c&&a.a.F;a.i=b;if(d){if(b){xK(a);ie(a.a).style[gvb]=ivb;a.a.N!=-1&&a.a.We(a.a.H,a.a.N);(TJ(),SJ).ff(ie(a.a),'rect(0px, 0px, 0px, 0px)');TH((QK(),UK()),a.a);a.g=new GK(a);qb(a.g,1)}else{T(a,200,Xf())}}else{zK(a)}}
function RR(a,b,c,d,e,f,g,h){if(a.d.o){a.d.o=false;tW(a.c)}a.d.e=g;lX(a.c,c,d);PR(a);a.c.C||(a.c.C=true,undefined);if(!bS(a.c.yc)){mX(a.c,true);DU(a.c)}eY(a.c,c,c,d,d);cY(a.c,c,c,d,d,true);f?aP(a.d.u,e):bP(a.d.u,e);dP(a.d.u,!g);b!=null?fP(a.d.u,b):fP(a.d.u,D$(c,d));QV(a.c)||QW(a.c,c,d);h||SU(a.c)}
function thb(a,b){var c,d,e,f,g;d=b>>5;b&=31;if(d>=a.d){return a.e<0?(Igb(),Cgb):(Igb(),Hgb)}f=a.d-d;e=gq(ir,Rxb,17,f+1,15,1);uhb(e,f,a.a,d,b);if(a.e<0){for(c=0;c<d&&a.a[c]==0;c++);if(c<d||b>0&&a.a[c]<<32-b!=0){for(c=0;c<f&&e[c]==-1;c++){e[c]=0}c==f&&++f;++e[c]}}g=new bhb(a.e,f,e);Lgb(g);return g}
function Ehb(a,b){var c,d,e,f,g;d=cE(b,yAb);if(dE(a,0)>=0){f=fE(a,d);g=mE(a,d)}else{c=sE(a,1);e=b>>>1;f=fE(c,e);g=mE(c,e);g=bE(qE(g,1),cE(a,1));if((b&1)!=0){if(dE(f,g)<=0){g=tE(g,f)}else{if(lE(tE(f,g),d)){g=bE(g,tE(d,f));f=tE(f,1)}else{g=bE(g,tE(qE(d,1),f));f=tE(f,2)}}}}return pE(qE(g,32),cE(f,yAb))}
function gO(a,b){uI.call(this);this.a=Ri($doc);this.b=false;this.f=false;this.k=-1;this.g=-1;this.i=-1;this.j=-1;this.n=-1;this.d=-1;this.e=a;this.c=b;(JF(),this.Yc).className='grouping';Be(this.Yc,'minus',true);this.a.innerHTML='&#x2212;';this.a.className='expand';Wg(this.Yc,this.a);dG(this.Yc,262145)}
function r1(a){o1();var b=a.a;if(!b.getPropertyValue)return '';if(b.getPropertyValue(Ayb))return b.getPropertyValue(Ayb);if(b.getPropertyValue(Byb))return b.getPropertyValue(Byb);if(b.getPropertyValue(Cyb))return b.getPropertyValue(Cyb);if(b.getPropertyValue(Dyb))return b.getPropertyValue(Dyb);return ''}
function bR(){this.d=Ri($doc);this.b=Ri($doc);this.a=Ri($doc);this.d.className='v-spreadsheet-popupbutton-overlay-header';this.b.className='v-window-closebox';this.b.setAttribute('role',Aub);this.a.className='header-caption';Wg(this.d,this.b);Wg(this.d,this.a);dG(this.b,1);cG(this.b,this);oe(this,this.d)}
function iS(a,b,c,d,e){var f;a.e=b;a.K=d;a.f=c;a.L=e;a.Y=$R(a.q.V.W,d,e+1);a.Z=$R(a.q.g,b,c+1);f=a.Z==0||a.Y==0;WS(a.b,b,c,d,e);f&&SS(a.b,true);if(a.ab>0&a.r>0){WS(a.W,b,c,d,e);f&&SS(a.W,true)}if(a.ab>0){WS(a.X,b,c,d,e);f&&SS(a.X,true)}if(a.r>0){WS(a.a,b,c,d,e);f&&SS(a.a,true)}a.p&&eS(a,false);a.o||mS(a)}
function dM(a){var b=$doc.createElement('div');b.tabIndex=0;var c=$doc.createElement('input');c.type='text';c.tabIndex=-1;c.setAttribute(Uub,'true');var d=c.style;d.opacity=0;d.height='1px';d.width='1px';d.zIndex=-1;d.overflow=Bvb;d.position=ivb;c.addEventListener('focus',a,false);b.appendChild(c);return b}
function qcb(b,c){b.u=-1;b.v=-1;if(c.length>=1){try{b.u=peb(c[0])}catch(a){a=_D(a);if(!Zq(a,21))throw aE(a)}}if(c.length>=2){try{b.v=peb(c[1])}catch(a){a=_D(a);if(!Zq(a,21))throw aE(a)}if(b.v==-1&&c[1].indexOf('-')!=-1){try{b.v=peb(wfb(c[1],0,lfb(c[1],Bfb(45))))}catch(a){a=_D(a);if(!Zq(a,21))throw aE(a)}}}}
function ug(a){var b,c,d,e,f,g,h;f=a.length;if(f==0){return null}b=false;c=new pf;while(Xf()-c.a<16){d=false;for(e=0;e<f;e++){h=a[e];if(!h){continue}d=true;if(!h[0].Kd()){a[e]=null;b=true}}if(!d){break}}if(b){g=[];for(e=0;e<f;e++){!!a[e]&&(g[g.length]=a[e],undefined)}return g.length==0?null:g}else{return a}}
function DX(a,b,c){var d,e,f,g;e=WU(a,b,c);d=fV(a,Ewb+b+Fwb+c);g=WU(a,a.rc,a.sc);f=fV(a,Ewb+a.rc+Fwb+a.sc);sob(a.u,new aZ(a.rc,a.sc));if(g){sob(a.t,g);th(g.d,Hxb);eh(g.d,Gxb)}if(f){sob(a.t,f);th(f.d,Hxb);eh(f.d,Gxb)}uob(a.u,new aZ(b,c));if(e){uob(a.t,e);th(e.d,Gxb)}if(d){uob(a.t,d);th(d.d,Gxb)}a.sc=c;a.rc=b}
function gN(a){var b,c,d;d=a.Uc&&a.M;eK(a);if(d){return false}else{a.jf(false);se(a,ye(SJ.ef((JF(),JF(),mh(a.Yc))))+'-'+Iwb,true);c=new T1(a.Yc);b=r1(c);b==null&&(b='');a.jf(true);if(b.indexOf(Iwb)!=-1){a.F=false;p1(a.Yc,new P6(a));return true}else{se(a,ye(SJ.ef((null,mh(a.Yc))))+'-'+Iwb,false);return false}}}
function lU(b,c){var d,e,f,g;if(c.a.length>0){e=new Ofb(mV(b.Ec));for(g=new slb(c);g.a<g.c.a.length;){f=qlb(g);try{Kfb(e,rfb(f,Exb,Fxb+b.Ac+' .cell.col'))}catch(a){a=_D(a);if(Zq(a,21)){d=a;Irb(b.U,(Kqb(),Iqb),'Invalid custom cell border style: '+f+', '+d.Hd())}else throw aE(a)}}_g(b.Ec);Wg(b.Ec,gj($doc,e.a))}}
function S0(){this.n=new qob;this.I=new $0(this);this.s=new _0;B0(this,(!Co&&(Co=new Ro),Co.a));this.V=new jY(this,this.Z);this.u=new pP(this,this.V);this.U=new _T(this);this.Q=new VR(this,this.V);Wg(ie(this.V),ie(this.u));Wg(ie(this.V),ie(this.U));jI(this,this.V);Je(this.V,new b1(this),(!ep&&(ep=new Bn),ep))}
function YO(a,b){var c,d,e,f,g,h;f=new SP;if(b.indexOf('!')!=-1){h=ufb(b,'!',0)[0];return jfb(H$(a.t),h)?YO(a,ufb(b,'!',0)[1]):null}else if(b.indexOf(':')!=-1){g=ufb(b,':',0);c=qP(g[0]);f.col1=c.a;f.row1=c.b;d=qP(g[1]);f.col2=d.a;f.row2=d.b}else{e=qP(b);f.col1=e.a;f.row1=e.b;f.col2=f.col1;f.row2=f.row1}return f}
function o2(a,b,c){m2();a.onload=Ftb(function(){a.onload=null;a.onerror=null;a.onreadystatechange=null;b.Gf(c)});a.onerror=Ftb(function(){a.onload=null;a.onerror=null;a.onreadystatechange=null;b.Ff(c)});a.onreadystatechange=function(){('loaded'===a.readyState||'complete'===a.readyState)&&a.onload(arguments[0])}}
function Igb(){Igb=HE;var a;Dgb=new ahb(1,1);Fgb=new ahb(1,10);Hgb=new ahb(0,0);Cgb=new ahb(-1,1);Egb=jq(eq(VB,1),Jtb,11,0,[Hgb,Dgb,new ahb(1,2),new ahb(1,3),new ahb(1,4),new ahb(1,5),new ahb(1,6),new ahb(1,7),new ahb(1,8),new ahb(1,9),Fgb]);Ggb=gq(VB,Jtb,11,32,0,1);for(a=0;a<Ggb.length;a++){Ggb[a]=nhb(qE(1,a))}}
function MK(){var c=function(){};c.prototype={className:'',clientHeight:0,clientWidth:0,dir:'',getAttribute:function(a,b){return this[a]},href:'',id:'',lang:'',nodeType:1,removeAttribute:function(a,b){this[a]=undefined},setAttribute:function(a,b){this[a]=b},src:'',style:{},title:''};$wnd.GwtPotentialElementShim=c}
function h_(a,b,c,d,e){var f,g,h,i;if(b==0||c==0||d==0||e==0||b==c&&d==e&&b==a.V.rc&&d==a.V.sc){return}f=c;g=e;if(b>c){i=b;b=c;c=i}if(d>e){i=d;d=e;e=i}if(a.u.f){cP(a.u,a.X,a.Y,f,g,false);NO(a.u)}else{h=TP(a.I,d,e,b,c);tab(a.W,a.V.sc,a.V.rc,h.row1,h.col1,h.row2,h.col2);fP(a.u,D$(a.V.rc,a.V.sc));PR(a.Q);qb(a.s,200)}}
function sS(a){VS(a.b,a.ab==0?0:a.ab+1,0,a.r==0?0:a.r+1,0);!!a.a&&VS(a.a,a.ab==0?0:a.ab+1,0,0,a.r);!!a.X&&VS(a.X,0,a.ab,a.r==0?0:a.r+1,0);!!a.W&&VS(a.W,0,a.ab,0,a.r);HS(a.B,a.ab==0?0:a.ab+1,0,a.r==0?0:a.r+1,0);!!a.A&&HS(a.A,a.ab==0?0:a.ab+1,0,0,a.r);!!a.F&&HS(a.F,0,a.ab,a.r==0?0:a.r+1,0);!!a.D&&HS(a.D,0,a.ab,0,a.r)}
function bT(a){this.F=a;this.B=Ri($doc);this.G=Ri($doc);this.k=Ri($doc);this.u=Ri($doc);this.a=Ri($doc);this.g=Ri($doc);this.i=Ri($doc);this.I=Ri($doc);this.o=Ri($doc);this.w=Ri($doc);this.c=Ri($doc);this.J=Ri($doc);this.p=Ri($doc);this.A=Ri($doc);this.d=Ri($doc);OS(this);dG(this.B,15736908);cG(this.B,new cT(this))}
function Nqb(a){Kqb();var b;b=yfb(a,(Epb(),Cpb));switch(b){case 'ALL':return Bqb;case 'CONFIG':return Cqb;case 'FINE':return Dqb;case 'FINER':return Eqb;case 'FINEST':return Fqb;case 'INFO':return Gqb;case 'OFF':return Hqb;case 'SEVERE':return Iqb;case tAb:return Jqb;default:throw aE(new zeb('Invalid level "'+a+'"'));}}
function Nq(a){var b,c,d,e,f;if(a.l==0&&a.m==0&&a.h==0){return '0'}if(a.h==Pvb&&a.m==0&&a.l==0){return '-9223372036854775808'}if(a.h>>19!=0){return '-'+Nq(Eq(a))}c=a;d='';while(!(c.l==0&&c.m==0&&c.h==0)){e=mq(Qvb);c=pq(c,e,true);b=''+Mq(lq);if(!(c.l==0&&c.m==0&&c.h==0)){f=9-b.length;for(;f>0;f--){b='0'+b}}d=b+d}return d}
function Tob(){if(!Object.create||!Object.getOwnPropertyNames){return false}var a='__proto__';var b=Object.create(null);if(b[a]!==undefined){return false}var c=Object.getOwnPropertyNames(b);if(c.length!=0){return false}b[a]=42;if(b[a]!==42){return false}if(Object.getOwnPropertyNames(b).length==0){return false}return true}
function NS(a){this.p=a;this.k=Ri($doc);this.q=Ri($doc);this.d=Ri($doc);this.j=Ri($doc);this.a=Ri($doc);this.k.className=qxb;eh(this.k,'paintmode');this.q.className='s-top';this.d.className=rxb;this.j.className=sxb;this.a.className=txb;Wg(this.q,this.d);Wg(this.q,this.j);Wg(this.d,this.a);Wg(this.k,this.q);oe(this,this.k)}
function z6(a){var b,c,d,e,f;e=$doc.querySelectorAll('link[rel~="icon"]');for(c=0;c<e.length;c++){d=e[c];b=(Gh(),d).getAttribute('href')||'';if(b!=null&&b.indexOf('VAADIN/themes')!=-1&&(f=Rzb.length,jfb(b.substr(b.length-f,f),Rzb))){b=tfb(b,'VAADIN/themes/.+?/favicon.ico','VAADIN/themes/'+a+Rzb);d.setAttribute('href',b)}}}
function PO(a){var b,c,d,e,f,g,h;for(c=(h=(new bkb(a.F.a)).a.$f().Oe(),new hkb(h));c.a.Ye();){b=(g=c.a.Ze(),g.gg());d=new aZ(b.c,b.k);if(!Pib(a.D,d)){b.d.style[Xwb]='';b.d.style[Ywb]=''}}Yib(a.F.a);a.f&&IO(a);for(f=new sjb((new kjb(a.D)).a);f.b;){e=rjb(f);b=WU(a.I,e.gg().a,e.gg().b);if(b){b.d.style[Xwb]=e.hg();sob(a.F,b)}}}
function fN(a,b){var c,d;if((SJ.ef((JF(),JF(),mh(a.Yc))).className||'').indexOf(Iwb)!=-1){p1(a.Yc,new R6(a,b))}else{se(a,ye(SJ.ef((null,mh(a.Yc))))+'-'+Jwb,true);d=new T1(a.Yc);c=r1(d);c==null&&(c='');if(c.indexOf(Jwb)!=-1){a.F=false;p1(a.Yc,new T6(a,b));a.K=false}else{se(a,ye(SJ.ef((null,mh(a.Yc))))+'-'+Jwb,false);iN(a,b)}}}
function j2(a,b){var c;c=new dcb;ccb(c,_F((Gh(),a).type));Xbb(c,(w2(),H2(a)));Ybb(c,I2(a));Fh.Sd(a)==1?Wbb(c,(icb(),fcb)):Fh.Sd(a)==2?Wbb(c,(icb(),hcb)):Fh.Sd(a)==4?Wbb(c,(icb(),gcb)):Wbb(c,(icb(),fcb));Vbb(c,!!a.altKey);Zbb(c,!!a.ctrlKey);$bb(c,!!a.metaKey);bcb(c,!!a.shiftKey);if(b){_bb(c,k2(c.c,b));acb(c,l2(c.d,b))}return c}
function EU(a){var b,c,d,e,f,g;for(g=new slb(a.ic);g.a<g.c.a.length;){e=qlb(g);th(e,Ixb)}for(d=new slb(a.K);d.a<d.c.a.length;){b=qlb(d);th(b,Jxb)}if(a.jb){for(f=new slb(a.jb);f.a<f.c.a.length;){e=qlb(f);th(e,Ixb)}}if(a.ib){for(c=new slb(a.ib);c.a<c.c.a.length;){b=qlb(c);th(b,Jxb)}}Yib(a.wc.a);Yib(a.tc.a);Yib(a.vc.a);Yib(a.uc.a)}
function VO(a,b,c){var d,e,f,g,h,i,j,k;g=$wnd.Math.min(b.col1,b.col2);f=$wnd.Math.max(b.col1,b.col2);k=$wnd.Math.min(b.row1,b.row2);j=$wnd.Math.max(b.row1,b.row2);if(f>20000){Erb(Qrb((Rdb(Xw),Xw.i)));return}for(d=g;d<=f;d++){for(i=k;i<=j;i++){e=WU(a.I,d,i);if(e){h=e.d;h.style[Xwb]=c;sob(a.F,e);Uib(a.D,new aZ(d,i),c)}}}Nkb(a.i,b)}
function qP(a){var b,c,d,e,f,g,h,i,j;b='';g='';if(a!=null){j=ufb(a.toUpperCase(),'[0-9]',0);i=ufb(a,'[A-z]',0);j.length>0&&(b=j[0]);i.length>0&&(g=i[i.length-1])}h=g.length>0?Neb(peb(g)).a:0;d=0;for(f=0;f<b.length;f++){e=(vtb(f,b.length),b.charCodeAt(f));c=0;e>=65&&e<=90?(c=e-64):e>=97&&e<=122&&(c=e-96);d=d*26+c}return new aZ(d,h)}
function g_(a,b){var c,d,e,f,g,h,i;for(d=new sjb((new kjb(b)).a);d.b;){c=rjb(d);e=c.gg().a;h=c.hg().a;if(h==0){if(!a.v){a.v=new Wkb;Nkb(a.v,Neb(e))}else Rkb(a.v,Neb(e),0)!=-1||Nkb(a.v,Neb(e))}a.g[e-1]=h}rW(a.V,false);if(a.J){for(g=new slb(a.J);g.a<g.c.a.length;){f=qlb(g);XX(a.V,f)}}a.d=true;i=rV(a.V);Aab(a.W,b,i[0],i[1],i[2],i[3])}
function t_(a,b){var c,d,e,f,g,h,i;for(d=new sjb((new kjb(b)).a);d.b;){c=rjb(d);e=c.gg().a;h=c.hg().a;if(h==0){if(!a.w){a.w=new Wkb;Nkb(a.w,Neb(e))}else Rkb(a.w,Neb(e),0)!=-1||Nkb(a.w,Neb(e))}a.M[e-1]=h}rW(a.V,false);if(a.J){for(g=new slb(a.J);g.a<g.c.a.length;){f=qlb(g);XX(a.V,f)}}a.d=true;i=rV(a.V);Oab(a.W,b,i[0],i[1],i[2],i[3])}
function Uhb(){Uhb=HE;var a,b;Rhb=gq(VB,Jtb,11,32,0,1);Shb=gq(VB,Jtb,11,32,0,1);Thb=jq(eq(ir,1),Rxb,17,15,[1,5,25,125,625,3125,15625,78125,390625,1953125,9765625,48828125,vAb,wAb]);a=1;for(b=0;b<=18;b++){Rhb[b]=nhb(a);Shb[b]=nhb(qE(a,b));a=nE(a,5)}for(;b<Shb.length;b++){Rhb[b]=Vgb(Rhb[b-1],Rhb[1]);Shb[b]=Vgb(Shb[b-1],(Igb(),Fgb))}}
function ZT(a){var b,c,d,e,f,g;e=a.g.value;if(e.length>31){e=e.substr(0,31);tj(a.g,e)}yh(a.v,e);f=(a.v.offsetWidth||0)|0;f<50&&(f=50);c=a.u[a.r];b=ih(a.k);d=(Gh(),Fh).Yd(c)+((c.offsetWidth||0)|0)+10;while(d>b&&a.s<a.u.length-1){g=RT(a,a.s);d=er(d-g);a.t-=g;++a.s}a.c.style[Kwb]=a.t+(im(),rwb);a.g.style[Rub]=f+5+rwb;c.style[Rub]=f+rwb}
function zH(){$wnd.addEventListener('mouseout',Ftb(function(a){var b=(hH(),dH);if(b&&!a.relatedTarget){if('html'==a.target.tagName.toLowerCase()){var c=$doc.createEvent(cvb);c.initMouseEvent(Gvb,true,true,$wnd,0,a.screenX,a.screenY,a.clientX,a.clientY,a.ctrlKey,a.altKey,a.shiftKey,a.metaKey,a.button,null);b.dispatchEvent(c)}}}),true)}
function BQ(a){var b,c,d,e,f,g;g=a.yf();f=ye(a.zf().nd());a.zf().qd('v-widget',true);yQ(a,f,'-error',null!=g.jb);for(b=0;b<a.t.length;b++){e=a.t[b];a.zf().qd(e,false);yQ(a,f+'-',e,false)}a.t.length=0;if(Hcb(g.nb)){for(d=g.nb.Oe();d.Ye();){c=d.Ze();a.zf().qd(c,true);yQ(a,f+'-',c,true);Wf(a.t,c)}}g.mb!=null&&!jfb(g.mb,f)&&te(a.zf(),g.mb)}
function cY(a,b,c,d,e,f){var g,h,i,j,k,l;a.v=false;if(f){AU(a);EU(a);i=WU(a,a.rc,a.sc);a.nb=null;!!i&&th(i.d,Hxb)}for(l=d;l<=e;l++){for(h=b;h<=c;h++){if(h!=a.rc||l!=a.sc){i=WU(a,h,l);sob(a.u,new aZ(h,l));if(i){sob(a.t,i);eh(i.d,Gxb)}j=fV(a,Ewb+h+Fwb+l);if(j){sob(a.t,j);eh(j.d,Gxb)}}}}for(k=d;k<=e;k++){TW(a,k)}for(g=b;g<=c;g++){SW(a,g)}}
function rR(){af();this.j=Ri($doc);this.a=new vR(this);this.f=new Wkb;this.j.className=jxb;this.j.setAttribute('role',Aub);this.e=new GN;re(this.e,'v-spreadsheet-popupbutton-overlay');this.i=new DL;te(this.i,'overlay-layout');this.g=new bR;_Q(this.g,this.e);BL(this.i,this.g);vI(this.e,this.i);oe(this,this.j);Ie(this,this,(ln(),ln(),kn))}
function qV(a,b,c,d){var e,f,g,h;h=new Ofb(Fxb);Jfb(Kfb(Kfb(h,a.Ac),' .sheet .cell.cs'),b);for(g=new sjb((new kjb(c)).a);g.b;){e=rjb(g);Deb(e.hg(),b)&&Kfb(Jfb(Kfb(Kfb((h.a+=Qxb,h),a.Ac),Nxb),e.gg()),'.cell.cs0')}for(f=new sjb((new kjb(d)).a);f.b;){e=rjb(f);Deb(e.hg(),b)&&Kfb(Jfb(Kfb(Kfb((h.a+=Qxb,h),a.Ac),Lxb),e.gg()),'.cell.cs0')}return h.a}
function bY(a){var b,c,d,e,f,g;RX(a,a.gc,a.dc,a.mb,a.jb,false,a.fc);g=a.Z?a.fc+1:a.fc;if(a.hc.childNodes.length==g){return}_g(a.hc);for(e=1;e<=g;e++){c=Ri($doc);Wg(a.hc,c);(Gh(),Fh).fe(c,''+e);c.className=dyb;f=e;JF();HF.Me(c,1);cG(c,new FY(a,f))}_g(a.cc);for(d=1;d<=g-1;d++){b=Ri($doc);Wg(a.cc,b);b.className=Ywb;b.style[Kwb]=15*d+(im(),rwb)}}
function vQ(a,b){b.Hf('id')&&(a.yf().lb!=null?wh(ie(a.zf()),a.yf().lb):b.b||(ie(a.zf()).removeAttribute('id'),undefined));Zq(a.yf(),128)?Zq(a.zf(),60)&&a.zf().Cd((a.yf(),0)):Zq(a.yf(),168)&&Zq(a.zf(),60)&&a.zf().Cd(a.yf().n);gQ(a,b);BQ(a);AQ(a,a.yf().ob==null?'':a.yf().ob,a.yf().kb==null?'':a.yf().kb);if(!a.u&&a.Bf()){a.u=true;null.tg(a.zf())}}
function jS(a,b){var c,d,e,f;if(a._){c=!(!!a.a&&a.a.K>a.b.K)&&b;e=!(!!a.X&&a.X.j>a.b.j)&&b;ZS(a.b,c,e,c,e);if(a.a){c=!(!!a.b&&a.b.K>=a.a.K)&&b;d=!(!!a.W&&a.W.j>a.a.j)&&b;ZS(a.a,c,d,c,d)}if(a.X){f=!(!!a.W&&a.W.K>a.X.K)&&b;e=!(!!a.b&&a.b.j>=a.X.j)&&b;ZS(a.X,f,e,f,e)}if(a.W){f=!(!!a.X&&a.X.K>=a.W.K)&&b;d=!(!!a.a&&a.a.j>=a.W.j)&&b;ZS(a.W,f,d,f,d)}}}
function $T(a){var b,c,d;if(a==null){return false}d=a.length;if(d<1||d>31){return false}for(c=0;c<d;c++){b=(vtb(c,a.length),a.charCodeAt(c));switch(b){case 47:case 92:case 63:case 42:case 93:case 91:case 58:return false;default:continue;}}vtb(0,a.length);if(a.charCodeAt(0)==39||(vtb(d-1,a.length),a.charCodeAt(d-1)==39)){return false}return true}
function khb(a,b){var c,d,e,f,g,h,i,j,k,l,m,n,o,p,q;n=b.length;i=n;vtb(0,b.length);if(b.charCodeAt(0)==45){l=-1;m=1;--n}else{l=1;m=0}f=(xhb(),whb)[10];e=n/f|0;q=n%f;q!=0&&++e;h=gq(ir,Rxb,17,e,15,1);c=vhb[8];g=0;o=m+(q==0?f:q);for(p=m;p<i;p=o,o=p+f){d=peb(b.substr(p,o-p));j=(Uhb(),Yhb(h,h,g,c));j+=Nhb(h,g,d);h[g++]=j}k=g;a.e=l;a.d=k;a.a=h;Lgb(a)}
function FH(){var d=$wnd.onbeforeunload;var e=$wnd.onunload;$wnd.onbeforeunload=function(a){var b,c;try{b=Ftb(LG)()}finally{c=d&&d(a)}if(b!=null){return b}if(c!=null){return c}};$wnd.onunload=Ftb(function(a){try{FG();zG&&np((!AG&&(AG=new YG),AG))}finally{e&&e(a);$wnd.onresize=null;$wnd.onscroll=null;$wnd.onbeforeunload=null;$wnd.onunload=null}})}
function Lp(b,c){var d,e,f,g,h,i;if(!c){throw aE(new afb('Cannot fire null event'))}try{++b.b;h=(e=Op(b,c.ne(),null),e);d=null;i=b.c?h.ag(h.size()):h._f();while(b.c?i.dg():i.Ye()){g=b.c?i.eg():i.Ze();try{c.le(g)}catch(a){a=_D(a);if(Zq(a,20)){f=a;!d&&(d=new vob);Uib(d.a,f,d)}else throw aE(a)}}if(d){throw aE(new Up(d))}}finally{--b.b;b.b==0&&Pp(b)}}
function dX(a,b,c,d){var e,f,g,h,i,j,k,l,m,n,o,p;k=(d.offsetWidth||0)|0;j=b-k;i=(Gh(),Fh).Yd(d);if(j>0){o=(FG(),(rj($doc).clientWidth|0)+oj($doc));n=oj($doc);h=o-i;e=i-n;h<b&&e>=j&&(i-=j)}l=Fh.Zd(d);p=(FG(),pj($doc));m=pj($doc)+(rj($doc).clientHeight|0);f=l-p;g=m-(l+((d.offsetHeight||0)|0));g<c&&f>=c?(l-=c):(l+=(d.offsetHeight||0)|0);lN(a.qb,i,l)}
function aQ(a,b){a.F=b;a.H='1';!!a&&(ie((!a.D&&(a.D=new S0),a.D)).tkPid='1',undefined);Cp((!a.G&&(a.G=new Fp(a)),a.G),(Q2(),P2),a);f0((!a.D&&(a.D=new S0),a.D),a.H);hQ(a,cy,a.a);!a.D&&(a.D=new S0);a.j=new Lbb;A0((!a.D&&(a.D=new S0),a.D),a.j);v0((!a.D&&(a.D=new S0),a.D),new ZZ(a));a.b=Ie(y1(a.F),new $Z,(sn(),sn(),rn));rab(a.j.d,jq(eq(KB,1),Jtb,1,5,[]))}
function q3(c){var d={setter:function(a,b){a.b=b},getter:function(a){return a.b}};c.Kf(dB,'title',d);var d={setter:function(a,b){a.q=b},getter:function(a){return a.q}};c.Kf(nB,Kzb,d);var d={setter:function(a,b){a.n=Idb(b)},getter:function(a){return Ldb(a.n)}};c.Kf(hB,Lzb,d);var d={setter:function(a,b){a.ob=b},getter:function(a){return a.ob}};c.Kf(PA,Rub,d)}
function cO(a,b){var c;b.b=a.b;b.e=a.e;b.f=a.f;yh(b.a,nh(a.a));c=(JF(),b.Yc).style;re(b,a.Yc.className||'');a.i>-1&&(c[Kwb]=a.i+(im(),rwb),undefined);a.j>-1&&(c[Lwb]=a.j+(im(),rwb),undefined);a.d>-1&&(c[Qub]=a.d+(im(),rwb),undefined);a.n>-1&&(c[Rub]=a.n+(im(),rwb),undefined);a.k>-1&&(c[kwb]=a.k+(im(),rwb),undefined);a.g>-1&&(c[jwb]=a.g+(im(),rwb),undefined)}
function Fhb(a,b){var c,d,e,f,g,h;f=(d=wE(a),d!=0?Leb(d):Leb(wE(rE(a,32)))+32);g=(e=wE(b),e!=0?Leb(e):Leb(wE(rE(b,32)))+32);h=$wnd.Math.min(f,g);f!=0&&(a=sE(a,f));g!=0&&(b=sE(b,g));do{if(dE(a,b)>=0){a=tE(a,b);a=sE(a,(c=wE(a),c!=0?Leb(c):Leb(wE(rE(a,32)))+32))}else{b=tE(b,a);b=sE(b,(c=wE(b),c!=0?Leb(c):Leb(wE(rE(b,32)))+32))}}while(dE(a,0)!=0);return qE(b,h)}
function WX(a,b,c){var d,e,f;f=0;e=0;d=c.d;if(a.ob>=b.col1&&b.col2>a.ob){mW(a,b,c);Uib(a.Kb,b,c);f=1}else{f=$R(a.a.g,b.col1,b.col2+1);d.style[Rub]=f+(im(),rwb)}if(a.Tc>=b.row1&&b.row2>a.Tc){lW(a,b,c);Uib(a.Kb,b,c);e=1}else{e=$R(a.a.V.W,b.row1,b.row2+1);d.style[Qub]=e+(im(),rwb)}f==0||e==0?(c.d.style[nvb]=(Ak(),Pub),undefined):(c.d.style[nvb]='flex',undefined)}
function Chb(a,b,c){var d,e,f,g,h,i,j,k,l,m,n,o,p;n=a.a;o=a.d;p=a.e;if(o==1){d=cE(n[0],yAb);e=cE(b,yAb);f=fE(d,e);j=mE(d,e);p!=c&&(f=oE(f));p<0&&(j=oE(j));return jq(eq(VB,1),Jtb,11,0,[nhb(f),nhb(j)])}h=o;i=p==c?1:-1;g=gq(ir,Rxb,17,h,15,1);k=jq(eq(ir,1),Rxb,17,15,[Dhb(g,n,o,b)]);l=new bhb(i,h,g);m=new bhb(p,1,k);Lgb(l);Lgb(m);return jq(eq(VB,1),Jtb,11,0,[l,m])}
function NX(a){var b,c,d,e,f,g,h;RX(a,a.I,a.F,a.lb,a.ib,true,a.H);g=a.Z?a.H+1:a.H;if(a.J.childNodes.length==g){return}_g(a.J);for(e=1;e<=g;e++){h=$i($doc);c=Ri($doc);Wg(a.J,c);c.appendChild(h);(Gh(),Fh).fe(h,''+e);c.className=dyb;f=e;JF();HF.Me(c,1);cG(c,new DY(a,f))}_g(a.D);for(d=1;d<=g-1;d++){b=Ri($doc);Wg(a.D,b);b.className=Ywb;b.style[Lwb]=18*d+(im(),rwb)}}
function Dhb(a,b,c,d){var e,f,g,h,i,j,k;j=0;f=cE(d,yAb);for(h=c-1;h>=0;h--){k=pE(qE(j,32),cE(b[h],yAb));if(dE(k,0)>=0){i=fE(k,f);j=mE(k,f)}else{e=sE(k,1);g=d>>>1;i=fE(e,g);j=mE(e,g);j=bE(qE(j,1),cE(k,1));if((d&1)!=0){if(dE(i,j)<=0){j=tE(j,i)}else{if(lE(tE(i,j),f)){j=bE(j,tE(f,i));i=tE(i,1)}else{j=bE(j,tE(qE(f,1),i));i=tE(i,2)}}}}a[h]=wE(cE(i,yAb))}return wE(j)}
function XR(a,b){var c,d;a.S=KV(a.Q,a.f,a.L);a.T=LV(a.Q,a.f,a.L);a.R=HV(a.Q,a.f,a.L);a.g=!a.S&&!a.T;a.i=!a.S&&!a.R;a.t=qh(a.Q.zc);a.u=(a.Q.zc.scrollTop||0)|0;a.c=(c=oj($doc),w2(),(Gh(),b).type.indexOf(kxb)!=-1?Rm(b.changedTouches[0])+c:ai(b.clientX||0)+c);a.d=(d=pj($doc),b.type.indexOf(kxb)!=-1?Sm(b.changedTouches[0])+d:ai(b.clientY||0)+d);a.U=a.f;a.V=a.L;rS(a)}
function bib(a,b,c){var d,e,f,g,h;for(f=0;f<b;f++){d=0;for(h=f+1;h<b;h++){d=bE(bE(nE(cE(a[f],yAb),cE(a[h],yAb)),cE(c[f+h],yAb)),cE(wE(d),yAb));c[f+h]=wE(d);d=sE(d,32)}c[f+b]=wE(d)}shb(c,c,b<<1);d=0;for(e=0,g=0;e<b;++e,g++){d=bE(bE(nE(cE(a[e],yAb),cE(a[e],yAb)),cE(c[g],yAb)),cE(wE(d),yAb));c[g]=wE(d);d=sE(d,32);++g;d=bE(d,cE(c[g],yAb));c[g]=wE(d);d=sE(d,32)}return c}
function qQ(b){var c,d,e,f;e=new A4(b.qg);try{f=S4(new r4(e,'getWidget'));d=z4(f);return d}catch(a){a=_D(a);if(Zq(a,80)){c=a;throw aE(new Ceb('Default implementation of createWidget() does not work for '+Tdb(b.qg)+'. This might be caused by explicitely using '+'super.createWidget() or some unspecified '+'problem with the widgetset compilation.',c))}else throw aE(a)}}
function Ngb(a,b){var c,d,e,f,g,h,i,j,k,l,m,n,o,p;f=b.e;if(f==0){throw aE(new Cdb(zAb))}e=b.d;d=b.a;if(e==1){return Chb(a,d[0],f)}n=a.a;o=a.d;c=o!=e?o>e?1:-1:Mhb(n,d,o);if(c<0){return jq(eq(VB,1),Jtb,11,0,[Hgb,a])}p=a.e;h=o-e+1;k=e;i=p==f?1:-1;g=gq(ir,Rxb,17,h,15,1);j=Bhb(g,h,n,o,d,e);l=new bhb(i,h,g);m=new bhb(p,k,j);Lgb(l);Lgb(m);return jq(eq(VB,1),Jtb,11,0,[l,m])}
function Qgb(a,b){var c,d,e,f;c=a.e<0?new bhb(1,a.d,a.a):a;d=b.e<0?new bhb(1,b.d,b.a):b;if(c.e==0){return d}else if(d.e==0){return c}if((c.d==1||c.d==2&&c.a[1]>0)&&(d.d==1||d.d==2&&d.a[1]>0)){return nhb(Fhb((f=c.d>1?pE(qE(c.a[1],32),cE(c.a[0],yAb)):cE(c.a[0],yAb),nE(c.e,f)),(e=d.d>1?pE(qE(d.a[1],32),cE(d.a[0],yAb)):cE(d.a[0],yAb),nE(d.e,e))))}return Ghb(Kgb(c),Kgb(d))}
function QN(a,b){if(b){Qm(a.e,qI(a.g.a));ue(a.g,false);a.e.style[nvb]=(Ak(),tvb);a.e.focus();a.e.select()}else{HI(a.g,a.e.value);ue(a.g,true);a.e.style[nvb]=(Ak(),Pub);FU(a.r,qI(a.g.a),a.b,a.d)}le(a.f)&&(le(a.a)||le(a.g)||jfb((Ak(),tvb),Ij(a.e.style)))?(ke(a.f).className||'').indexOf(Swb)!=-1||se(a.f,Swb,true):(ke(a.f).className||'').indexOf(Swb)!=-1&&se(a.f,Swb,false)}
function uX(a,b,c){var d,e;a._=true;CT(a.$,'.'+a.Ac+' .sheet div'+(Exb+a.rc+'.row'+a.sc),0);re(a.sb,Ewb+a.rc+Fwb+a.sc+' cell'+' '+(e=XU(a,a.rc,a.sc),!e?'cs0':e.cellStyle));if(PV(a,Ewb+a.rc+Fwb+a.sc)){a.ab=true;d=fV(a,Ewb+a.rc+Fwb+a.sc);!!d&&qe(a.sb,Jj(d.d.style))}SX(a);b&&(EV(a,a.rc,a.sc)||QW(a,a.rc,a.sc),s2((og(),ng),new xY(a,false)));s2((og(),ng),new zY(a,c));iL(a.sb,c)}
function peb(a){var b,c,d,e,f;if(a==null){throw aE(new cfb(Xtb))}d=a.length;e=d>0&&(vtb(0,a.length),a.charCodeAt(0)==45||(vtb(0,a.length),a.charCodeAt(0)==43))?1:0;for(b=e;b<d;b++){if(Pdb((vtb(b,a.length),a.charCodeAt(b)))==-1){throw aE(new cfb(Wtb+a+'"'))}}f=parseInt(a,10);c=f<Ytb;if(isNaN(f)){throw aE(new cfb(Wtb+a+'"'))}else if(c||f>Otb){throw aE(new cfb(Wtb+a+'"'))}return f}
function v_(a,b,c){var d,e,f,g,h,i,j;b==0?(b=1):b<0&&(b=jV(a.V)+1);b>a.i&&(b=a.i);c==0?(c=1):c<0&&(c=VU(a.V)+1);c>a.O&&(c=a.O);h=a.V.rc;i=a.V.sc;if(b<=h){d=b;e=h}else{d=h;e=b}if(c<=i){f=c;g=i}else{f=i;g=c}if(a.u.f){cP(a.u,a.X,a.Y,b,c,false)}else{j=TP(a.I,f,g,d,e);eY(a.V,j.col1,j.col2,j.row1,j.row2);cY(a.V,j.col1,j.col2,j.row1,j.row2,true);fP(a.u,E$(j.col1,j.col2,j.row1,j.row2))}}
function sq(a,b,c,d,e,f){var g,h,i,j,k,l,m;j=vq(b)-vq(a);g=Hq(b,j);i=oq(0,0,0);while(j>=0){h=yq(a,g);if(h){j<22?(i.l|=1<<j,undefined):j<44?(i.m|=1<<j-22,undefined):(i.h|=1<<j-44,undefined);if(a.l==0&&a.m==0&&a.h==0){break}}k=g.m;l=g.h;m=g.l;g.h=l>>>1;g.m=k>>>1|(l&1)<<21;g.l=m>>>1|(k&1)<<21;--j}c&&uq(i);if(f){if(d){lq=Eq(a);e&&(lq=Kq(lq,(Tq(),Rq)))}else{lq=oq(a.l,a.m,a.h)}}return i}
function ufb(a,b,c){var d,e,f,g,h,i,j,k;d=new RegExp(b,'g');j=gq(QB,_tb,2,0,6,1);e=0;k=a;g=null;while(true){i=d.exec(k);if(i==null||k==''||e==c-1&&c>0){j[e]=k;break}else{h=i.index;j[e]=k.substr(0,h);k=wfb(k,h+i[0].length,k.length);d.lastIndex=0;if(g==k){j[e]=k.substr(0,1);k=k.substr(1)}g=k;++e}}if(c==0&&a.length>0){f=j.length;while(f>0&&j[f-1]==''){--f}f<j.length&&(j.length=f)}return j}
function tO(a,b){var c,d,e,f;d=!a.c.vb&&!!(Gh(),b).ctrlKey||!!(Gh(),b).metaKey;f=kY(b);e=TV(a.c,f);if(!d||!e){return}if(((Gh(),b).keyCode|0)==67||(b.keyCode|0)==88){(af(),_e).bf((JF(),a.Yc));iL(a,oO(a.a));c=ph(a.Yc,xwb).length;c>0&&hL(a,0,c);a.Yc.style[jwb]=(im(),'100.0px');Ag((og(),new xO(a,b)),100)}if((b.keyCode|0)==86){(JF(),a.Yc)[xwb]='';(af(),_e).bf(a.Yc);Ag((og(),new zO(a)),100)}}
function n2(){var a,b,c,d,e,f,g,h,i,j;this.a=new vob;new qob;a=$doc;a.getElementsByTagName(Iyb)[0];i=a.getElementsByTagName('script');for(e=0;e<i.length;e++){b=i[e];j=b.src;j!=null&&j.length!=0&&sob(this.a,j)}g=a.getElementsByTagName(Fub);for(d=0;d<g.length;d++){f=g[d];h=f.rel;c=f.href;kfb(Jyb,h)&&c!=null&&c.length!=0&&sob(this.a,c);kfb('import',h)&&c!=null&&c.length!=0&&sob(this.a,c)}}
function fT(a,b){var c,d;if(!a.c._){if(!a.b){return}d=(Gh(),b).keyCode|0;c=Fh.Td(b);if((c==122||c==121)&&(!!b.ctrlKey||!!b.metaKey)){Fh.Xd(b);b.stopPropagation();return}if(c==0){switch(d){case 38:case 40:case 37:case 39:case 9:case 8:case 46:case 32:Fh.Xd(b);b.stopPropagation();break;case 13:y_(a.c.a,b,String.fromCharCode(c));}}else !b.ctrlKey&&!b.metaKey&&y_(a.c.a,b,String.fromCharCode(c))}}
function Lhb(a,b,c,d,e){var f,g;f=bE(cE(b[0],yAb),cE(d[0],yAb));a[0]=wE(f);f=rE(f,32);if(c>=e){for(g=1;g<e;g++){f=bE(f,bE(cE(b[g],yAb),cE(d[g],yAb)));a[g]=wE(f);f=rE(f,32)}for(;g<c;g++){f=bE(f,cE(b[g],yAb));a[g]=wE(f);f=rE(f,32)}}else{for(g=1;g<c;g++){f=bE(f,bE(cE(b[g],yAb),cE(d[g],yAb)));a[g]=wE(f);f=rE(f,32)}for(;g<e;g++){f=bE(f,cE(d[g],yAb));a[g]=wE(f);f=rE(f,32)}}dE(f,0)!=0&&(a[g]=wE(f))}
function dV(a,b,c){var d,e,f,g,h,i,j,k,l;f=b-1;j=c-1;if(j<0||f<0){return null}if(a.Tc<c){l=c>=a.db;k=a.d.a.length>c-a.db;if(l&&k){g=Qkb(a.d,c-a.db).a.length>f;if(g){return Qkb(Qkb(a.d,c-a.db),f)}}}else if(a.ob<b){h=b-a.bb;k=a.Rc.a.length>j;if(k){i=b>=a.bb;g=Qkb(a.Rc,j).a.length>h;if(i&&g){return Qkb(Qkb(a.Rc,j),h)}}}else{e=j*a.ob+f;d=a.Nc.a.length>e;if(e>=0&&d){return Qkb(a.Nc,e)}}return null}
function y6(a,b,c,d,e){var f,g;f=null;if(b!=null){f=v6(d);!f&&Orb(Qrb((Rdb(fA),fA.k)),'Did not find the link tag for the old theme ('+d+'), adding a new stylesheet for the new theme ('+e+')')}if(c!=null){g=Vi($doc);g.rel=Jyb;g.type=Yxb;g.href=e;o2(g,new M6(a,c,e,f),null);f?Zg($doc.getElementsByTagName(Iyb)[0],g,f):Wg($doc.getElementsByTagName(Iyb)[0],g)}else{!!f&&ah(Kh((Gh(),f)),f);t6(a,null)}}
function dS(a,b){var c,d,e,f,g,h,i,j;a.o=true;c=(g=oj($doc),w2(),(Gh(),b).type.indexOf(kxb)!=-1?Rm(b.changedTouches[0])+g:ai(b.clientX||0)+g);d=(h=pj($doc),b.type.indexOf(kxb)!=-1?Sm(b.changedTouches[0])+h:ai(b.clientY||0)+h);if(YR(a,d,c)){return}i=c-a.v+qh(a.Q.zc)-a.t;j=d-a.w+((a.Q.zc.scrollTop||0)|0)-a.u;i-=70;j-=20;e=a.q.g;f=a.q.V.W;a.U=ZR(e,a.O,i,true);a.V=ZR(f,a.P,j,true);v_(a.Q.a,a.U,a.V)}
function cS(a,b){var c,d,e,f,g,h,i,j,k,l;a.j=false;a.s=false;c=(i=oj($doc),w2(),(Gh(),b).type.indexOf(kxb)!=-1?Rm(b.changedTouches[0])+i:ai(b.clientX||0)+i);d=(j=pj($doc),b.type.indexOf(kxb)!=-1?Sm(b.changedTouches[0])+j:ai(b.clientY||0)+j);if(YR(a,d,c)){return}k=c-a.v+qh(a.Q.zc)-a.t;l=d-a.w+((a.Q.zc.scrollTop||0)|0)-a.u;f=a.q.g;h=a.q.V.W;e=ZR(f,a.e,k,false);g=ZR(h,a.K,l,false);e>=0&&g>=0&&tS(a,e,g)}
function OX(b){var c,d,e,f,g,h,i,j;i=b.a.n;try{f=new Ykb(new bkb(i));Rlb();Clb(f.a,f.a.length);g=f.a.length;h=new Ofb(mV(b.Dc));for(d=0;d<g;d++){e=(otb(d,f.a.length),f.a[d]);j=Cib(Gob(i.a,e));Kfb(h,Fxb+b.Ac+' .sheet .cell.cf'+e+' {'+j+'}')}_g(b.Dc);Wg(b.Dc,gj($doc,h.a))}catch(a){a=_D(a);if(Zq(a,21)){c=a;Nrb(b.U,'SheetWidget:updateConditionalFormattingStyles: '+zf(c,c.Hd())+cyb)}else throw aE(a)}}
function ggb(a,b){var c,d,e,f,g,h;e=mgb(a);h=mgb(b);if(e==h){if(a.e==b.e&&a.a<54&&b.a<54){return a.f<b.f?-1:a.f>b.f?1:0}d=a.e-b.e;c=(a.d>0?a.d:$wnd.Math.floor((a.a-1)*xAb)+1)-(b.d>0?b.d:$wnd.Math.floor((b.a-1)*xAb)+1);if(c>d+1){return e}else if(c<d-1){return -e}else{f=(!a.c&&(a.c=mhb(a.f)),a.c);g=(!b.c&&(b.c=mhb(b.f)),b.c);d<0?(f=Vgb(f,aib(-d))):d>0&&(g=Vgb(g,aib(d)));return Jgb(f,g)}}else return e<h?-1:1}
function vU(a){var b,c,d,e,f,g,h,i;bh(a.fb);for(f=new slb(a.K);f.a<f.c.a.length;){d=qlb(f);g=Kh((Gh(),d));!!g&&g.removeChild(d)}a.K.a=gq(KB,Jtb,1,0,5,1);for(e=new slb(a.ic);e.a<e.c.a.length;){d=qlb(e);g=Kh((Gh(),d));!!g&&g.removeChild(d)}a.ic.a=gq(KB,Jtb,1,0,5,1);for(i=new slb(a.kc);i.a<i.c.a.length;){h=qlb(i);for(c=new slb(h);c.a<c.c.a.length;){b=qlb(c);bh(b.d)}h.a=gq(KB,Jtb,1,0,5,1)}a.kc.a=gq(KB,Jtb,1,0,5,1)}
function jU(a,b){var c,d,e,f,g;!a.Cc&&(a.Cc=new qob);d=b.b;g=b.k;e=Ewb+d+Fwb+g;if(d!=0&&g!=0){Vib(a.Cc,e,b);if(d>=a.bb&&d<=a.xb&&g>=a.db&&g<=a.zb||d<=a.ob&&g<=a.Tc||d>a.ob&&d<=a.xb&&g<=a.Tc||g>a.Tc&&g<=a.zb&&d<=a.ob){c=WU(a,d,g);f=b.Xc;if(f){if(a==f){PM(c,(JF(),b.Yc))}else{Oe(b);PM(c,(JF(),b.Yc));Qe(b,a)}}else{PM(c,(JF(),b.Yc));Qe(b,a)}}}else{while(Tib(a.Cc,e)){kR(b,--d);e=Ewb+d+Fwb+g}Vib(a.Cc,e,b)}qR(b,a,a.zc)}
function wU(a,b){var c,d,e,f,g,h;a.Db=false;for(e=new slb(aV(a));e.a<e.c.a.length;){d=qlb(e);sW(a,d)}a.S=null;for(h=(f=(new mkb(a.Bc)).a.$f().Oe(),new rkb(f));h.a.Ye();){g=(c=h.a.Ze(),c.hg());sW(a,g)}Yib(a.Bc);if(a.T){Yib(a.T);a.T=null}vU(a);Yib(a.e);Yib(a.qc);vT(a.w);zU(a);DU(a);xU(a);CU(a);yU(a);if(b){vT(a.Ec);bh(a.w);bh(a.Dc);bh(a.Ec);bh(a.$);bh(a.Wb);bh(a.Fb);!!a.pb&&bh(a.pb);if(a.Nb){AM(a.Nb.a);a.Nb=null}}}
function DV(a,b,c,d,e){return (b<=a.ob||b>=eV(a)&&b<=jV(a))&&(d<=a.Tc||d<=sV(a)&&d>=VU(a))&&(b>=a.bb&&b<=a.xb&&e>=a.db&&e<=a.zb||b<=a.ob&&e<=a.Tc||b>a.ob&&b<=a.xb&&e<=a.Tc||e>a.Tc&&e<=a.zb&&b<=a.ob)&&(c>=a.bb&&c<=a.xb&&d>=a.db&&d<=a.zb||c<=a.ob&&d<=a.Tc||c>a.ob&&c<=a.xb&&d<=a.Tc||d>a.Tc&&d<=a.zb&&c<=a.ob)&&(c>=a.bb&&c<=a.xb&&e>=a.db&&e<=a.zb||c<=a.ob&&e<=a.Tc||c>a.ob&&c<=a.xb&&e<=a.Tc||e>a.Tc&&e<=a.zb&&c<=a.ob)}
function cP(a,b,c,d,e,f){var g,h,i,j,k,l,m,n,o,p;if(b==d&&c==e){g=D$(b,c)}else{if(b>d){o=b;b=d;d=o}if(c>e){o=c;c=e;e=o}g=D$(b,c)+':'+D$(d,e)}if(f&&a.s>=0){g=','+g;++a.q}k=fL(a.e);i=k>0;if(i){l=eL(a.e);h=l+k;a.s=l;a.q=h}else if(f||a.s<0){l=a.q;h=a.q;a.s=a.q}else{l=a.s;h=a.q}p=(j=gL(a.e),j==null?'':j);m=p.substr(0,l);n=wfb(p,h,p.length);p=m+g+n;a.q=(m+g).length;iL(a.e,p);a.e==a.w&&iL(a.j,p);s2((og(),ng),new FP(a))}
function sJ(a,b){var c,d,e;d=(JF(),dj($doc));a.d=aj($doc);Wg(d,TF(a.d));if(!b){e=cj($doc);Wg(a.d,TF(e))}a.i=b;c=(FI(),EI).af();Wg(c,TF(d));pe(a,c);Qd();Db(kd,a.Yc);a.Vc==-1?WF(a.Yc,2225|(a.Yc.__eventBits||0)):(a.Vc|=2225);a.Yc.className='gwt-MenuBar';b?se(a,ye(a.Yc)+'-'+'vertical',true):se(a,ye(a.Yc)+'-'+'horizontal',true);a.Yc.style['outline']='0px';a.Yc.setAttribute('hideFocus','true');Ie(a,new LJ(a),(dn(),dn(),cn))}
function NN(a){var b,c;if(a.c){b=ih(a.c);c=jh(a.c);if(b>=hh(a.o)&&b<ih(a.o)&&c>=jh(a.o)&&c<=gh(a.o)){JN(a);(JF(),a.Yc).style[ovb]=Avb;!!a.t&&(a.t.style[ovb]=Avb,undefined);a.i.style[ovb]=(wm(),Avb);a.M||oN(a)}else{(JF(),a.Yc).style[ovb]=Bvb;!!a.t&&(a.t.style[ovb]=Bvb,undefined);a.i.style[ovb]=(wm(),Bvb)}}else{(JF(),a.Yc).style[ovb]=Bvb;!!a.t&&(a.t.style[ovb]=Bvb,undefined);a.i.style[ovb]=(wm(),Bvb);fN(a,false);bh(a.i)}}
function WO(a,b){var c,d,e,f,g,h,i,j,k,l;MO(a);k=XO(a,b);Yib(a.G);e=0;d=0;for(j=new slb(k);j.a<j.c.a.length;){i=qlb(j);h=YO(a,i);if(!h){continue}if(Tib(a.G,i)){c=Sib(a.G,i)}else{d=d%EO.a.length;c=Qkb(EO,d);Vib(a.G,i,c);++d}c=rfb(c,'%s','0.25');VO(a,h,c);g=b.indexOf(i,e);f=(JF(),$i($doc));l=b.substr(e,g-e);l=sfb(l,' ','&nbsp;');f.innerHTML=l||'';Wg(a.r,f);e=g+i.length;f=$i($doc);(Gh(),Fh).fe(f,i);f.style[Xwb]=c;Wg(a.r,f)}}
function sW(b,c){var d,e,f,g,h;try{d=(JF(),c.Yc);g=Kh((Gh(),d));h=c.Xc;e=nf(b.zc,g)||nf(b.Oc,g)||nf(b.Qc,g)||nf(b.c,g);if(e||M(c,b.S)||!!g&&!!g.parentNode&&$g(b.zc,g.parentNode)){Qe(c,null);f=Kh(d);!!f&&f.removeChild(d);return true}else if(M(b,h)){Qe(c,null);return true}else{return false}}catch(a){a=_D(a);if(Zq(a,21)){Irb(b.U,(Kqb(),Jqb),'Exception while removing child widget from SheetWidget')}else throw aE(a)}return false}
function cW(b){var c,d,e,f,g;e=b.Mc+((b.zc.scrollTop||0)|0);d=qh(b.zc);g=e-b.Pb;c=d-b.Ob;if($wnd.Math.abs(g)<(b.a.L/2|0)&&$wnd.Math.abs(c)<(b.a.j/2|0)){return}try{if($wnd.Math.abs(c)>(b.a.j/2|0)){b.Ob=d;c>0?vV(b,d):c<0&&uV(b,d)}if($wnd.Math.abs(g)>(b.a.L/2|0)){b.Pb=e;g>0?zV(b,e):g<0&&AV(b,e)}g6(b.Rb)}catch(a){a=_D(a);if(Zq(a,20)){f=a;Nrb(b.U,'SheetWidget:updateSheetDisplay: '+zf(f,f.Hd()))}else throw aE(a)}HW(b);MX(b,g,c);QU(b)}
function jdb(){dR.call(this);this.q=new sdb;this.c=new kdb;this.e=new qob;this.e.put('error',new pdb('Error: ',' - close with ESC-key',($cb(),Ycb)));this.e.put('warning',new pdb('Warning: ',null,Ycb));this.e.put('humanized',new pdb('Info: ',null,Ycb));this.e.put('tray',new pdb('Status: ',null,Ycb));this.e.put('assistive',new pdb('Note: ',null,Ycb));this.g=new bdb;this.d=new mdb;this.j=new qdb;this.k=new rdb;this.mb='v-ui';this.n=1}
function Ohb(a,b){var c,d,e,f,g,h,i,j,k,l;g=a.e;i=b.e;if(i==0){return a}if(g==0){return b.e==0?b:new bhb(-b.e,b.d,b.a)}f=a.d;h=b.d;if(f+h==2){c=cE(a.a[0],yAb);d=cE(b.a[0],yAb);g<0&&(c=oE(c));i<0&&(d=oE(d));return nhb(tE(c,d))}e=f!=h?f>h?1:-1:Mhb(a.a,b.a,f);if(e==-1){l=-i;k=g==i?Phb(b.a,h,a.a,f):Khb(b.a,h,a.a,f)}else{l=g;if(g==i){if(e==0){return Igb(),Hgb}k=Phb(a.a,f,b.a,h)}else{k=Khb(a.a,f,b.a,h)}}j=new bhb(l,k.length,k);Lgb(j);return j}
function WW(a,b){var c,d,e,f,g;if(!a.s){a.s=b}else{Yib(a.s);!!b&&vib(a.s,b)}if(!!b&&b.a.c+b.b.c!=0){g=new Mfb;for(e=(f=(new bkb(b)).a.$f().Oe(),new hkb(f));e.a.Ye();){c=rfb(rfb((d=e.a.Ze(),d.gg()),Ewb,Exb),' r','.r');g.a+=''+c;e.a.Ye()&&(g.a+=',',g)}if(!a.pb){a.pb=_i($doc);a.pb.type=Yxb;wh(a.pb,a.Ac+'-hyperlinkstyle');Wg(a.w.parentNode,a.pb);g.a+='{ cursor: pointer !important; }';yT(a.pb,g.a)}else{CT(a.pb,g.a,0)}}else{!!a.pb&&CT(a.pb,Oxb,0)}}
function VG(b){var c,d,e,f,g,h,i,j,k,l,m,n,o;k=new qob;if(b!=null&&b.length>1){l=b.substr(1);for(h=ufb(l,'&',0),i=0,j=h.length;i<j;++i){g=h[i];f=ufb(g,'=',2);e=f[0];if(e.length==0){continue}m=f.length>1?f[1]:'';try{m=(Xp(m),o=/\+/g,decodeURIComponent(m.replace(o,'%20')))}catch(a){a=_D(a);if(!Zq(a,82))throw aE(a)}n=k.get(e);if(!n){n=new Wkb;k.put(e,n)}n.add(m)}}for(d=k.$f().Oe();d.Ye();){c=d.Ze();c.ig(Ulb(c.hg()))}k=(Rlb(),new jnb(k));return k}
function MX(a,b,c){var d,e,f,g,h,i,j;e=Qkb(Qkb(a.kc,0),0);j=Qkb(a.kc,a.kc.a.length-1);h=Qkb(j,j.a.length-1);f=e.k;i=h.k;d=e.c;g=h.c;tW(a);if(f>a.zb||i<a.db||d>a.xb||g<a.bb){LW(a,a.db,a.zb,a.bb,a.xb,a.kc,a.zc);b!=0&&a.ob>0&&LW(a,a.db,a.zb,1,a.ob,a.d,a.c);c!=0&&a.Tc>0&&LW(a,1,a.Tc,a.bb,a.xb,a.Rc,a.Qc)}else{MW(a,b,c,a.db,a.zb,a.bb,a.xb,a.kc,a.zc);b!=0&&a.ob>0&&MW(a,b,0,a.db,a.zb,1,a.ob,a.d,a.c);c!=0&&a.Tc>0&&MW(a,0,c,1,a.Tc,a.bb,a.xb,a.Rc,a.Qc)}}
function Mgb(a,b){var c,d,e,f,g,h,i,j,k,l;if(b.e==0){throw aE(new Cdb(zAb))}e=b.e;if(b.d==1&&b.a[0]==1){return b.e>0?a:a.e==0?a:new bhb(-a.e,a.d,a.a)}k=a.e;j=a.d;d=b.d;if(j+d==2){l=fE(cE(a.a[0],yAb),cE(b.a[0],yAb));k!=e&&(l=oE(l));return nhb(l)}c=j!=d?j>d?1:-1:Mhb(a.a,b.a,j);if(c==0){return k==e?Dgb:Cgb}if(c==-1){return Hgb}g=j-d+1;f=gq(ir,Rxb,17,g,15,1);h=k==e?1:-1;d==1?Dhb(f,a.a,j,b.a[0]):Bhb(f,g,a.a,j,b.a,d);i=new bhb(h,g,f);Lgb(i);return i}
function _X(a,b,c,d,e,f){var g,h;Xib(a.Cc,Ewb+d+Fwb+c);Vib(a.Cc,Ewb+f+Fwb+e,b);h=b.Xc;if(f>=a.bb&&f<=a.xb&&e>=a.db&&e<=a.zb||f<=a.ob&&e<=a.Tc||f>a.ob&&f<=a.xb&&e<=a.Tc||e>a.Tc&&e<=a.zb&&f<=a.ob){g=WU(a,f,e);if(h){if(M(a,h)){(d>=a.bb&&d<=a.xb&&c>=a.db&&c<=a.zb||d<=a.ob&&c<=a.Tc||d>a.ob&&d<=a.xb&&c<=a.Tc||c>a.Tc&&c<=a.zb&&d<=a.ob)&&KM(WU(a,d,c));PM(g,(JF(),b.Yc))}else{Oe(b);PM(g,(JF(),b.Yc));Qe(b,a)}}else{PM(g,(JF(),b.Yc));Qe(b,a)}}else !!h&&Oe(b)}
function WR(a,b){var c,d;a.S=KV(a.Q,a.f,a.L);a.T=LV(a.Q,a.f,a.L);a.R=HV(a.Q,a.f,a.L);a.g=!a.S&&!a.T;a.i=!a.S&&!a.R;a.t=qh(a.Q.zc);a.u=(a.Q.zc.scrollTop||0)|0;a.c=(c=oj($doc),w2(),(Gh(),b).type.indexOf(kxb)!=-1?Rm(b.changedTouches[0])+c:ai(b.clientX||0)+c);a.d=(d=pj($doc),b.type.indexOf(kxb)!=-1?Sm(b.changedTouches[0])+d:ai(b.clientY||0)+d);a.U=a.f;a.V=a.L;a.C=true;a.j=false;a.s=false;rS(a);UF((JF(),a.Yc));Fh.Xd(b);eh(ie(a.Q),'selecting');jS(a,true)}
function aib(a){Uhb();var b,c,d,e;b=er(a);if(a<Shb.length){return Shb[b]}else if(a<=50){return Wgb((Igb(),Fgb),b)}else if(a<=evb){return Ygb(Wgb(Rhb[1],b),b)}if(a>1000000){throw aE(new Cdb('power of ten too big'))}if(a<=Otb){return Ygb(Wgb(Rhb[1],b),b)}d=Wgb(Rhb[1],Otb);e=d;c=hE(a-Otb);b=er(a%Otb);while(dE(c,Otb)>0){e=Vgb(e,d);c=tE(c,Otb)}e=Vgb(e,Wgb(Rhb[1],b));e=Ygb(e,Otb);c=hE(a-Otb);while(dE(c,Otb)>0){e=Ygb(e,Otb);c=tE(c,Otb)}e=Ygb(e,b);return e}
function Ji(a){if(a.offsetTop==null){return 0}var b=0;var c=a.ownerDocument;var d=a.parentNode;if(d){while(d.offsetParent){b-=d.scrollTop;d=d.parentNode}}while(a){b+=a.offsetTop;if(c.defaultView.getComputedStyle(a,'')[gvb]==hvb){b+=c.body.scrollTop;return b}var e=a.offsetParent;e&&$wnd.devicePixelRatio&&(b+=parseInt(c.defaultView.getComputedStyle(e,'').getPropertyValue('border-top-width')));if(e&&e.tagName=='BODY'&&a.style.position==ivb){break}a=e}return b}
function gY(a){var b,c,d;a.W=gq(ir,Rxb,17,a.a.O,15,1);a.Mc=0;d=0;if(a.Tc>0){d=mU(a,1,a.Tc);a.Mc=er(d+1)}b=mU(a,a.Tc+1,a.a.O);a.Bb=0;a.ob>0&&(a.Bb=pU(a,1,a.ob));c=pU(a,a.ob+1,a.a.i);fY(a);d>0&&a.Bb>0?th(a.Oc,fyb):eh(a.Oc,fyb);d>0?th(a.Qc,fyb):eh(a.Qc,fyb);a.Bb>0?th(a.c,fyb):eh(a.c,fyb);a.Qc.style[Kwb]=(im(),swb);a.c.style[Lwb]=swb;a.I.style[Kwb]=swb;a.gc.style[Kwb]=swb;YV(a);a.fb.style[Qub]=b+rwb;a.fb.style[Rub]=c+rwb;a.c.style[Qub]=b+rwb;a.Qc.style[Rub]=c+rwb}
function DW(a){var b,c;if(a.ib){if(a.ob>0){FW(a)}else{vW(a.ib);a.ib=null}}else if(a.ob>0){a.ib=new Wkb;FW(a)}for(c=a.bb;c<=a.xb;c++){if(c>a.ob){if(c-a.bb<a.K.a.length){b=Qkb(a.K,c-a.bb)}else{b=Ri($doc);Wg(a.Qc,b);Mkb(a.K,c-a.bb,b)}b.className=Zxb+c||'';xh(b,I$(c)+$xb);tob(a.tc,Neb(c))&&eh(b,Jxb)}else{Nrb(a.U,'Trying to add plain column header (index:'+c+') into frozen pane, horizontalSplitPosition: '+a.ob)}}while(a.K.a.length>a.xb-a.bb+1){bh(Tkb(a.K,a.K.a.length-1))}}
function IW(a){var b,c;if(a.jb){if(a.Tc>0){GW(a)}else{vW(a.jb);a.jb=null}}else if(a.Tc>0){a.jb=new Wkb;GW(a)}for(b=a.db;b<=a.zb;b++){if(a.Tc<b){if(b-a.db<a.ic.a.length){c=Qkb(a.ic,b-a.db)}else{c=Ri($doc);Wg(a.c,c);Mkb(a.ic,b-a.db,c)}c.className=_xb+b||'';c.innerHTML=''+b+$xb||'';tob(a.wc,Neb(b))&&eh(c,Ixb)}else{Nrb(a.U,'Trying to add plain row header (index:'+b+') into frozen pane, verticalSplitPosition: '+a.Tc)}}while(a.ic.a.length>a.zb-a.db+1){bh(Tkb(a.ic,a.ic.a.length-1))}}
function iV(a,b,c,d){var e,f,g,h,i;f=fV(a,Ewb+d.c+Fwb+d.k);if(!f){i=d.d;g=d.c;h=d.k;e=false;if(b<(Gh(),Fh).Yd(i)&&d.c>a.bb){--g;while(P$(a.a,g)&&g>a.bb){--g}e=true}else if(b>Fh.Yd(i)+((i.offsetWidth||0)|0)&&d.c<a.xb){++g;while(P$(a.a,g)&&g<a.xb){++g}e=true}if(c<Fh.Zd(i)&&d.k>a.db){--h;while(Q$(a.a,h)&&h>a.db){--h}e=true}else if(c>Fh.Zd(i)+((i.offsetHeight||0)|0)&&d.k<a.zb){++h;while(Q$(a.a,h)&&h<a.zb){++h}e=true}if(e){return iV(a,b,c,WU(a,g,h))}return d}else{return f}}
function OR(a,b){var c,d,e,f,g,h,i;e=a.c.yc.e;g=a.c.yc.f;i=a.c.yc.K;c=a.c.yc.L;d=a.c.rc;h=a.c.sc;f=L$(a.d,d,h);if(!!f&&a.a!=0){d=a.a;h=f.row1}--h;while(!!a.d.w&&Rkb(a.d.w,Neb(h),0)!=-1&&h>1){--h}if(!b&&(e!=g||i!=c)&&(!f||e!=f.col1||g!=f.col2||i!=f.row1||c!=f.row2)){if(h<i){h=c;while(!!a.d.w&&Rkb(a.d.w,Neb(h),0)!=-1&&h>i){--h}--d;while(!!a.d.v&&Rkb(a.d.v,Neb(d),0)!=-1&&d>=e){--d}d<e&&(d=g);while(!!a.d.v&&Rkb(a.d.v,Neb(d),0)!=-1&&d>=e){--d}}FR(a,d,h)}else{h>0&&GR(a,d,h)}}
function LR(a,b){var c,d,e,f,g,h,i;e=a.c.yc.e;g=a.c.yc.f;i=a.c.yc.K;c=a.c.yc.L;d=a.c.rc;h=a.c.sc;f=L$(a.d,d,h);if(!!f&&a.a!=0){d=a.a;h=f.row2}++h;while(!!a.d.w&&Rkb(a.d.w,Neb(h),0)!=-1&&h<a.d.O){++h}if(!b&&(e!=g||i!=c)&&(!f||e!=f.col1||g!=f.col2||i!=f.row1||c!=f.row2)){if(h>c){h=i;while(!!a.d.w&&Rkb(a.d.w,Neb(h),0)!=-1&&h<c){++h}++d;while(!!a.d.v&&Rkb(a.d.v,Neb(d),0)!=-1&&d<=g){++d}d>g&&(d=e);while(!!a.d.v&&Rkb(a.d.v,Neb(d),0)!=-1&&d<=g){++d}}FR(a,d,h)}else{h<=a.d.O&&GR(a,d,h)}}
function NR(a,b){var c,d,e,f,g,h,i;e=a.c.yc.e;g=a.c.yc.f;i=a.c.yc.K;c=a.c.yc.L;d=a.c.rc;h=a.c.sc;f=L$(a.d,d,h);if(!!f&&a.b!=0){d=f.col2;h=a.b}++d;while(!!a.d.v&&Rkb(a.d.v,Neb(d),0)!=-1&&d<a.d.i){++d}if(!b&&(e!=g||i!=c)&&(!f||e!=f.col1||g!=f.col2||i!=f.row1||c!=f.row2)){if(d>g){d=e;while(!!a.d.v&&Rkb(a.d.v,new Eeb(d),0)!=-1&&d<=g){++d}++h;while(!!a.d.w&&Rkb(a.d.w,Neb(h),0)!=-1&&h<=c){++h}h>c&&(h=i);while(!!a.d.w&&Rkb(a.d.w,Neb(h),0)!=-1&&h<=c){++h}}FR(a,d,h)}else{d<=a.d.i&&GR(a,d,h)}}
function MR(a,b){var c,d,e,f,g,h,i;e=a.c.yc.e;g=a.c.yc.f;i=a.c.yc.K;c=a.c.yc.L;d=a.c.rc;h=a.c.sc;f=L$(a.d,d,h);if(!!f&&a.b!=0){d=f.col1;h=a.b}--d;while(!!a.d.v&&Rkb(a.d.v,Neb(d),0)!=-1&&d>0){--d}if(!b&&(e!=g||i!=c)&&(!f||e!=f.col1||g!=f.col2||i!=f.row1||c!=f.row2)){if(d<e){d=g;while(!!a.d.v&&Rkb(a.d.v,Neb(d),0)!=-1&&d>=e){--d}--h;while(!!a.d.w&&Rkb(a.d.w,Neb(h),0)!=-1&&h>=i){--h}h<i&&(h=c);while(!!a.d.w&&Rkb(a.d.w,Neb(h),0)!=-1&&h>=i){--h}}FR(a,d,h)}else{d>0&&GR(a,d,h)}}
function Ghb(a,b){var c,d,e,f,g,h;c=Sgb(a);d=Sgb(b);e=$wnd.Math.min(c,d);phb(a,c);phb(b,d);if(Jgb(a,b)==1){f=a;a=b;b=f}do{if(b.d==1||b.d==2&&b.a[1]>0){b=nhb(Fhb((h=a.d>1?pE(qE(a.a[1],32),cE(a.a[0],yAb)):cE(a.a[0],yAb),nE(a.e,h)),(g=b.d>1?pE(qE(b.a[1],32),cE(b.a[0],yAb)):cE(b.a[0],yAb),nE(b.e,g))));break}if(b.d>a.d*1.2){b=Xgb(b,a);b.e!=0&&phb(b,Sgb(b))}else{do{Qhb(b.a,b.a,b.d,a.a,a.d);Lgb(b);b.b=-2;phb(b,Sgb(b))}while(Jgb(b,a)>=0)}f=b;b=a;a=f}while(a.e!=0);return Ygb(b,e)}
function kW(b,c){var d,e,f,g,h,i;try{g=nV(b);if(!g){Nrb(b.U,'Selected cell is null');return}LM(g,c);h=XV(b,g.b,c);d=g.c;if(b.ab){f=M$(b.a,b.rc,b.sc);d=f.col2;i=$R(b.a.g,f.col1,f.col2+1)}else{i=K$(b.a,d)}while(i<h&&d<b.a.i){i+=K$(b.a,++d)}ve(b.sb,i+1+rwb)}catch(a){a=_D(a);if(Zq(a,21)){e=a;Nrb(b.U,'SheetWidget:recalculateInputElementWidth: '+zf(e,e.Hd())+' while calculating input element width');EV(b,b.rc,b.sc)||QW(b,b.rc,b.sc);s2((og(),ng),new xY(b,false))}else throw aE(a)}}
function xhb(){xhb=HE;vhb=jq(eq(ir,1),Rxb,17,15,[Ytb,1162261467,BAb,wAb,362797056,1977326743,BAb,387420489,Qvb,214358881,429981696,815730721,1475789056,170859375,268435456,410338673,612220032,893871739,1280000000,1801088541,113379904,148035889,191102976,vAb,308915776,387420489,481890304,594823321,729000000,887503681,BAb,1291467969,1544804416,1838265625,60466176]);whb=jq(eq(ir,1),Rxb,17,15,[-1,-1,31,19,15,13,11,11,10,9,9,8,8,8,8,7,7,7,7,7,7,7,6,6,6,6,6,6,6,6,6,6,6,6,6,6,5])}
function IO(a){var b,c,d,e,f,g,h,i;i=(f=gL(a.e),f==null?'':f);c=eL(a.e);e=0;while(--c>0){vtb(c,i.length);i.charCodeAt(c)==34&&(c==0||(vtb(c-1,i.length),i.charCodeAt(c-1)!=92))&&++e}if(e%2==1){return}g=-1;d=-1;c=eL(a.e);while(c>0){b=(vtb(c-1,i.length),i.charCodeAt(c-1));if(qfb(String.fromCharCode(b))){g=c;break}--c}c=eL(a.e);while(c<i.length){b=(vtb(c,i.length),i.charCodeAt(c));if(qfb(String.fromCharCode(b))){d=c;break}++c}h=i.substr(g,d-g);OO(a);if(SO(a,h)){a.s=g;a.q=d;mP(a,h)}}
function CW(a){var b,c,d,e,f,g,h;BU(a.Nc);for(g=new slb(a.Rc);g.a<g.c.a.length;){e=qlb(g);BU(e)}a.Rc.a=gq(KB,Jtb,1,0,5,1);for(h=new slb(a.d);h.a<h.c.a.length;){e=qlb(h);BU(e)}a.d.a=gq(KB,Jtb,1,0,5,1);for(f=new slb(a.kc);f.a<f.c.a.length;){e=qlb(f);BU(e)}a.kc.a=gq(KB,Jtb,1,0,5,1);Wg(a.zc,a.fb);if(a.Tc>0&&a.ob>0){LU(a);MU(a);GU(a)}else a.Tc>0?MU(a):a.ob>0&&GU(a);for(c=a.db;c<=a.zb;c++){e=new Xkb(a.xb);for(d=a.bb;d<=a.xb;d++){b=new UM(a,d,c);Wg(a.zc,b.d);e.a[e.a.length]=b}Nkb(a.kc,e)}}
function HU(a){var b,c,d,e,f;f=Ri($doc);eh(f,'cell-range-bg-color');f.style[Rub]=(im(),swb);f.style[Qub]=swb;Wg(a.zc,f);e=new T1(f);b=S1(e,Xwb);b=rfb(b,'!important','');ah(a.zc,f);if(b!=null&&zfb(b).length!=0){d=lf();(JF(),d.Yc).height=1;d.Yc.width=1;of(d.Yc.getContext('2d'),b);d.Yc.getContext('2d').fillRect(0,0,1,1);c='url("'+d.Yc.toDataURL()+'")';yT(a.Dc,'.'+a.Ac+Kxb+'background-image: '+c+' !important;'+'}')}else{yT(a.Dc,'.'+a.Ac+Kxb+'background-color: rgba(232, 242, 252, 0.8) !important;'+'}')}}
function LW(a,b,c,d,e,f,g){var h,i,j,k,l;for(k=b;k<=c;k++){if(f.a.length>k-b){l=(otb(k-b,f.a.length),f.a[k-b])}else{l=new Wkb;rtb(k-b,f.a.length);htb(f.a,k-b,l)}for(h=d;h<=e;h++){if(l.a.length>h-d){i=(otb(h-d,l.a.length),l.a[h-d]);QM(i,h,k,Sib(a.e,Ewb+h+Fwb+k))}else{i=new VM(a,h,k,Sib(a.e,Ewb+h+Fwb+k));Wg(g,i.d);rtb(h-d,l.a.length);htb(l.a,h-d,i)}}while(l.a.length>e-d+1){bh(Tkb(l,l.a.length-1).d)}}while(f.a.length>c-b+1){for(j=new slb(Tkb(f,f.a.length-1));j.a<j.c.a.length;){i=qlb(j);bh(i.d)}}ZX(a,false)}
function mP(a,b){var c,d,e,f,g,h,i,j,k,l;if(Tib(a.G,b)){j=YO(a,b);if(!j){return}f=$wnd.Math.min(j.col1,j.col2);e=$wnd.Math.max(j.col1,j.col2);l=$wnd.Math.min(j.row1,j.row2);k=$wnd.Math.max(j.row1,j.row2);if(e>20000){Erb(Qrb((Rdb(Xw),Xw.i)));return}for(c=f;c<=e;c++){for(i=l;i<=k;i++){d=WU(a.I,c,i);if(d){h=d.d;g=rfb(Sib(a.G,b),'%s','0.75');c==f&&(h.style['borderLeft']=Zwb+g,undefined);c==e&&(h.style[$wb]=Zwb+g,undefined);i==l&&(h.style['borderTop']=Zwb+g,undefined);i==k&&(h.style[_wb]=Zwb+g,undefined)}}}a.A=b}}
function Jhb(a,b){var c,d,e,f,g,h,i,j,k,l,m,n,o;g=a.e;i=b.e;if(g==0){return b}if(i==0){return a}f=a.d;h=b.d;if(f+h==2){c=cE(a.a[0],yAb);d=cE(b.a[0],yAb);if(g==i){k=bE(c,d);o=wE(k);n=wE(sE(k,32));return n==0?new ahb(g,o):new bhb(g,2,jq(eq(ir,1),Rxb,17,15,[o,n]))}return nhb(g<0?tE(d,c):tE(c,d))}else if(g==i){m=g;l=f>=h?Khb(a.a,f,b.a,h):Khb(b.a,h,a.a,f)}else{e=f!=h?f>h?1:-1:Mhb(a.a,b.a,f);if(e==0){return Igb(),Hgb}if(e==1){m=g;l=Phb(a.a,f,b.a,h)}else{m=i;l=Phb(b.a,h,a.a,f)}}j=new bhb(m,l.length,l);Lgb(j);return j}
function d_(a,b,c,d){var e,f,g,h,i;F$(a);g=a.V.Tc>0?1:sV(a.V);c||O0(a,b,g,null);if(c){i=a.V.rc;e=i>b?b:i;f=i>b?i:b;h=a.O;if(bS(a.V.yc)){eY(a.V,e,f,1,h);cY(a.V,e,f,1,h,true)}else{cY(a.V,e,f,1,h,false)}bS(a.V.yc)?uab(a.W,1,e,h,f):xab(a.W,1,e,h,f)}else if(d){a.V.C&&(a.V.C=false,undefined);bS(a.V.yc)&&mX(a.V,false);CX(a.V,b,g);PR(a.Q);cY(a.V,b,b,1,a.O,false);yab(a.W,g,b)}else{a.V.C||(a.V.C=true,undefined);if(!bS(a.V.yc)){mX(a.V,true);DU(a.V)}lX(a.V,b,g);eY(a.V,b,b,1,a.O);cY(a.V,b,b,1,a.O,true);PR(a.Q);Bab(a.W,b,g)}qb(a.s,200)}
function q_(a,b,c,d){var e,f,g,h,i;f=a.V.ob>0?1:eV(a.V);F$(a);c||O0(a,f,b,null);if(c){e=a.i;i=a.V.sc;g=i>b?b:i;h=i>b?i:b;if(bS(a.V.yc)){eY(a.V,1,e,g,h);cY(a.V,1,e,g,h,true)}else{cY(a.V,1,e,g,h,false)}bS(a.V.yc)?uab(a.W,g,1,h,e):xab(a.W,g,1,h,e)}else if(d){a.V.C&&(a.V.C=false,undefined);bS(a.V.yc)&&mX(a.V,false);CX(a.V,f,b);PR(a.Q);cY(a.V,1,a.i,b,b,false);Lab(a.W,b,f)}else{a.V.C||(a.V.C=true,undefined);if(!bS(a.V.yc)){mX(a.V,true);DU(a.V)}lX(a.V,f,b);eY(a.V,1,a.i,b,b);cY(a.V,1,a.i,b,b,true);PR(a.Q);Nab(a.W,b,f)}qb(a.s,200)}
function LX(b){var c,d,e,f,g,h,i,j,k,l;h=(w1(),false&&XG('debug')!=null);l=b.a.f;k=0;h&&(k=(Rfb(),hE(Date.now())));i=b.a.N;c=b.a.k;if(l){try{j=new Ofb(mV(b.Dc));for(g=new sjb((new kjb(l)).a);g.b;){f=rjb(g);f.gg().a==0?Kfb(j,Fxb+b.Ac+' .sheet .cell {'+f.hg()+'}'):Kfb(j,qV(b,f.gg(),i,c)+' {'+f.hg()+'}')}_g(b.Dc);Wg(b.Dc,gj($doc,j.a))}catch(a){a=_D(a);if(Zq(a,21)){d=a;Nrb(b.U,'SheetWidget:updateStyles: '+zf(d,d.Hd())+cyb)}else throw aE(a)}}if(h){e=(Rfb(),hE(Date.now()));Hrb(b.U,'Style update took:'+xE(tE(e,k))+'ms')}jW(b);HU(b)}
function tS(a,b,c){var d,e,f,g,h,i,j;if(b>=a.e&&b<=a.f&&c>=a.K&&c<=a.L){j=$wnd.Math.abs(a.L-c);h=$wnd.Math.abs(a.f-b);if(a._||j==0&&h==0){gS(a,0,0,0,0);hS(a,false);return}hS(a,true);a.j=true;if(j>h){i=$wnd.Math.max(a.K+1,a.L-j+1);gS(a,a.e,a.f,i,a.L)}else{i=$wnd.Math.max(a.e+1,a.f-h+1);gS(a,i,a.f,a.K,a.L)}}else if(c<a.K||c>a.L||b<a.e||b>a.f){hS(a,true);a.s=true;d=c-a.L;g=a.K-c;e=a.e-b;f=b-a.f;$wnd.Math.max(d,g)>$wnd.Math.max(e,f)?d>g?gS(a,a.e,a.f,a.L+1,c):gS(a,a.e,a.f,c+1,a.K-1):f>e?gS(a,a.f+1,b,a.K,a.L):gS(a,b+1,a.e-1,a.K,a.L)}}
function CX(a,b,c){var d,e,f,g,h,i;h=WU(a,a.rc,a.sc);g=fV(a,Ewb+a.rc+Fwb+a.sc);if(a.v){sob(a.u,new aZ(a.rc,a.sc));if(h){sob(a.t,h);eh(h.d,Gxb)}if(g){sob(a.t,g);eh(g.d,Gxb)}a.v=false}else{sob(a.u,new aZ(a.rc,a.sc));if(h){sob(a.t,h);eh(h.d,Gxb)}if(g){sob(a.t,g);eh(g.d,Gxb)}i=M$(a.a,b,c);TW(a,c);if(i){for(d=i.row1+1;d<=i.row2;d++){TW(a,d)}}SW(a,b);if(i){for(d=i.col1+1;d<=i.col2;d++){SW(a,d)}}}if(h){a.nb=null;th(h.d,Hxb)}!!g&&th(g.d,Hxb);f=WU(a,b,c);if(f){a.nb=new aZ(f.c,f.k);eh(f.d,Hxb)}e=fV(a,Ewb+b+Fwb+c);!!e&&eh(e.d,Hxb);a.sc=c;a.rc=b}
function OS(a){a.B.className=qxb;a.F._&&eh(a.B,kxb);a.G.className='s-top';a.k.className=rxb;a.u.className=sxb;a.a.className=txb;a.g.className='s-corner';a.i.className='s-corner-touch';a.I.className=uxb;a.o.className=uxb;a.w.className=uxb;a.c.className=uxb;a.J.className=vxb;a.p.className=vxb;a.A.className=vxb;a.d.className=vxb;if(a.F._){Wg(a.u,a.i);Wg(a.i,a.g)}else{Wg(a.u,a.g)}Wg(a.G,a.k);Wg(a.G,a.u);Wg(a.k,a.a);Wg(a.B,a.G);if(a.F._){Wg(a.G,a.J);Wg(a.k,a.p);Wg(a.u,a.A);Wg(a.a,a.d);Wg(a.J,a.I);Wg(a.p,a.o);Wg(a.A,a.w);Wg(a.d,a.c)}oe(a,a.B)}
function KU(a,b,c,d,e){var f,g,h,i,j,k,l,m;l=e;m=new qob;for(h=c;h<=d;h++){k=new Mfb;j=a.W[h-1];Kfb(Hfb(Kfb(Hfb(Kfb(Kfb(Kfb(Hfb(Kfb(Kfb(Kfb(Hfb(Kfb(Kfb((k.a+='.',k),a.Ac),Nxb),h),', .'),a.Ac),'>.resize-line.row'),h),' { '),Q$(a.a,h)?Mxb:'display: flex;'),'height: '),j),'px; top:'),l),'px; }\n');l+=j;Uib(m,Neb(h),Neb(l));Nkb(b,k.a)}for(g=new sjb((new kjb(a.Eb)).a);g.b;){f=rjb(g);i=f.hg().k-1;!(i==d&&d==a.Tc)&&Pib(m,Neb(i))?(f.hg().d.style[kwb]=Rib(m,Neb(i)).a+(im(),rwb),undefined):i<c&&d!=a.Tc&&(f.hg().d.style[kwb]=(im(),swb),undefined)}}
function IU(a,b,c,d,e){var f,g,h,i,j,k,l,m,n;l=e;m=new qob;for(k=c;k<=d;k++){n=new Mfb;h=J$(a.a,k);Kfb(Hfb(Kfb(Hfb(Kfb(Kfb(Kfb(Hfb(Kfb(Kfb(Kfb(Hfb(Kfb(Kfb((n.a+='.',n),a.Ac),Lxb),k),', .'),a.Ac),'>.resize-line.col'),k),' { '),P$(a.a,k)?Mxb:''),'width: '),h),'px; left:'),l),'px; }\n');l+=h;Uib(m,Neb(k),Neb(l));Nkb(b,n.a)}f=ih((JF(),a.Yc));for(j=new sjb((new kjb(a.Eb)).a);j.b;){i=rjb(j);g=i.hg().c-1;!(g==d&&d==a.ob)&&Pib(m,Neb(g))?(i.hg().d.style[jwb]=Rib(m,Neb(g)).a+(im(),rwb),undefined):g>d&&d!=a.ob&&(i.hg().d.style[jwb]=f+(im(),rwb),undefined)}}
function WS(a,b,c,d,e){var f;th(a.B,Ewb+a.e+Fwb+a.C);if(a.s>0&&b<a.s){b=a.s;US(a,true)}else{US(a,false)}if(a.t>0&&d<a.t){d=a.t;$S(a,true)}else{$S(a,false)}if(a.r>0&&e>a.r){e=a.r;RS(a,true);a.i.style[nvb]=(Ak(),Pub);a.g.style[nvb]=Pub}else{RS(a,false);a.i.style[nvb]=(Ak(),tvb);a.g.style[nvb]=tvb}if(a.q>0&&a.q<c){c=a.q;XS(a,true)}else{XS(a,false)}a.e=b;a.C=d;a.f=c;a.D=e;a.K=c-b;a.j=e-d;if(b<=c&&d<=e){eh(a.B,Ewb+a.e+Fwb+a.C);Ee((JF(),a.Yc),true);aT(a);f=a.F.q.V.W;f!=null&&f.length!=0&&TS(a,$R(a.F.q.V.W,a.C,a.D+1))}else{Ee((JF(),a.Yc),false)}}
function ngb(a){var b,c,d,e,f;if(a.g!=null){return a.g}if(a.a<32){a.g=zhb(hE(a.f),er(a.e));return a.g}e=Ahb((!a.c&&(a.c=mhb(a.f)),a.c),0);if(a.e==0){return e}b=(!a.c&&(a.c=mhb(a.f)),a.c).e<0?2:1;c=e.length;d=-a.e+c-b;f=new Mfb;f.a+=''+e;if(a.e>0&&d>=-6){if(d>=0){Lfb(f,c-er(a.e),String.fromCharCode(46))}else{f.a=wfb(f.a,0,b-1)+'0.'+vfb(f.a,b-1);Lfb(f,b+1,Dfb(Zfb,0,-er(d)-1))}}else{if(c-b>=1){Lfb(f,b,String.fromCharCode(46));++c}Lfb(f,c,String.fromCharCode(69));d>0&&Lfb(f,++c,String.fromCharCode(43));Lfb(f,++c,''+xE(hE(d)))}a.g=f.a;return a.g}
function $J(a,b){var c,d,e,f;if(b.a||!a.K&&b.b){a.I&&(b.a=true);return}b.c&&false&&(b.a=true);if(b.a){return}d=b.d;c=UJ(a,d);c&&(b.b=true);a.I&&(b.a=true);f=(JF(),$G((Gh(),d).type));switch(f){case 512:case 256:case 128:{(d.keyCode|0)&bub;(d.shiftKey?1:0)|(d.metaKey?8:0)|(d.ctrlKey?2:0)|(d.altKey?4:0);return}case 4:case _vb:{if(IF){b.b=true;return}}if(!c&&a.u){a.Ve(true);return}break;case 8:case 64:case 1:case 2:case Rtb:{if(IF){b.b=true;return}break}case 2048:{e=Fh.Wd(d);if(a.I&&!c&&!!e){e.blur&&e!=$doc.body&&e.blur();b.a=true;return}break}}}
function FO(){FO=HE;EO=new Wkb;Nkb(EO,'rgba(48, 144, 240, %s)');Nkb(EO,'rgba(236, 100, 100, %s)');Nkb(EO,'rgba(152, 223, 88, %s)');Nkb(EO,'rgba(249, 221, 81, %s)');Nkb(EO,'rgba(36, 220, 212, %s)');Nkb(EO,'rgba(236, 100, 165, %s)');Nkb(EO,'rgba(104, 92, 176, %s)');Nkb(EO,'rgba(255, 125, 66, %s)');Nkb(EO,'rgba(51, 97, 144, %s)');Nkb(EO,'rgba(170, 81, 77, %s)');Nkb(EO,'rgba(127, 176, 83, %s)');Nkb(EO,'rgba(187, 168, 91, %s)');Nkb(EO,'rgba(36, 121, 129, %s)');Nkb(EO,'rgba(150, 57, 112, %s)');Nkb(EO,'rgba(75, 86, 168, %s)');Nkb(EO,'rgba(154, 89, 61, %s)')}
function OW(a,b,c,d){var e,f,g,h,i,j;j=false;b<=a.ob&&(b=a.ob+1);f=eV(a);h=jV(a);if(d){if(b<f){i=0;for(e=f-1;e>=b-1&&e>0;e--){i+=K$(a.a,e)}zh(a.zc,qh(a.zc)-i);(b<=a.bb||i>(a.a.j/2|0))&&(j=true)}else if(b>h){i=0;g=a.a.i;for(e=h+1;e<=b+1&&e<=g;e++){i+=K$(a.a,e)}zh(a.zc,qh(a.zc)+i);(b>=a.xb||i>(a.a.j/2|0))&&(j=true)}}else{if(c>h){i=0;g=a.a.i;for(e=h+1;e<=c+1&&e<=g;e++){i+=K$(a.a,e)}zh(a.zc,qh(a.zc)+i);(c>=a.xb||i>(a.a.j/2|0))&&(j=true)}else if(c<f){i=0;for(e=f-1;e>=c-1&&e>0;e--){i+=K$(a.a,e)}zh(a.zc,qh(a.zc)-i);(c<=a.bb||i>(a.a.j/2|0))&&(j=true)}}return j}
function Dq(a,b){var c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,A,B,C,D,F,G;c=a.l&8191;d=a.l>>13|(a.m&15)<<9;e=a.m>>4&8191;f=a.m>>17|(a.h&255)<<5;g=(a.h&1048320)>>8;h=b.l&8191;i=b.l>>13|(b.m&15)<<9;j=b.m>>4&8191;k=b.m>>17|(b.h&255)<<5;l=(b.h&1048320)>>8;B=c*h;C=d*h;D=e*h;F=f*h;G=g*h;if(i!=0){C+=c*i;D+=d*i;F+=e*i;G+=f*i}if(j!=0){D+=c*j;F+=d*j;G+=e*j}if(k!=0){F+=c*k;G+=d*k}l!=0&&(G+=c*l);n=B&Ovb;o=(C&511)<<13;m=n+o;q=B>>22;r=C>>9;s=(D&262143)<<4;t=(F&31)<<17;p=q+r+s+t;v=D>>18;w=F>>5;A=(G&4095)<<8;u=v+w+A;p+=m>>22;m&=Ovb;u+=p>>22;p&=Ovb;u&=Stb;return oq(m,p,u)}
function uU(a,b,c){var d,e,f,g,h,i,j;i=jh(a.zc);g=hh(a.zc);f=gh(a.zc);h=ih(a.zc);a.A=c;a.B=b;b<i?(a.O||!a.Jc&&!a.Ic)&&(a.Y=b-i):b>f?(a.Y=b-f):(a.Y=0);c<g?(a.P||!a.Hc&&!a.Ic)&&(a.X=c-g):c>h?(a.X=c-h):(a.X=0);j=false;if(((a.zc.scrollTop||0)|0)!=0){e=b<i;if(!a.O&&(a.Ic||a.Jc)&&IV(a,a.Kc,a.Lc)&&!e){a.zc.scrollTop=0;dW(a);a.O=true;j=true}}if(qh(a.zc)!=0){d=c<g;if(!a.P&&(a.Ic||a.Hc)&&IV(a,a.Kc,a.Lc)&&!d){zh(a.zc,0);dW(a);a.P=true;j=true}}if(a.Y<0&&((a.zc.scrollTop||0)|0)!=0||a.Y>0||a.X<0&&qh(a.zc)!=0||a.X>0){wX(a);j=true}if(j){return true}else{AX(a);return false}}
function YR(a,b,c){var d,e,f,g,h,i,j;i=jh(a.Q.zc);g=hh(a.Q.zc);f=gh(a.Q.zc);h=ih(a.Q.zc);a.c=c;a.d=b;b<i?(a.g||!a.T&&!a.S)&&(a.n=b-i):b>f?(a.n=b-f):(a.n=0);c<g?(a.i||!a.R&&!a.S)&&(a.k=c-g):c>h?(a.k=c-h):(a.k=0);j=false;if(((a.Q.zc.scrollTop||0)|0)!=0){e=b<i;if(!a.g&&(a.S||a.T)&&IV(a.Q,a.U,a.V)&&!e){a.Q.zc.scrollTop=0;dW(a.Q);a.u=0;a.g=true;j=true}}if(qh(a.Q.zc)!=0){d=c<g;if(!a.i&&(a.S||a.R)&&IV(a.Q,a.U,a.V)&&!d){zh(a.Q.zc,0);dW(a.Q);a.t=0;a.i=true;j=true}}if(a.n<0&&((a.Q.zc.scrollTop||0)|0)!=0||a.n>0||a.k<0&&qh(a.Q.zc)!=0||a.k>0){nS(a);j=true}if(j){return true}else{pS(a);return false}}
function QX(a,b){var c,d,e,f;c=0;if(a.H>0){d=a.Z?a.H+1:a.H;c=3+d*18}f=0;if(a.fc>0){e=a.Z?a.fc+1:a.fc;f=1+e*15}if(f==0){a.gc.style[nvb]=(Ak(),Pub);a.hc.style[nvb]=Pub}else{a.gc.style[nvb]=(Ak(),tvb);a.hc.style[nvb]=tvb}a.Z||(a.hc.style[nvb]=(Ak(),Pub),undefined);!!a.jb&&a.fc>0?(a.dc.style[nvb]=(Ak(),tvb),undefined):(a.dc.style[nvb]=(Ak(),Pub),undefined);a.gc.style[Rub]=f+(im(),rwb);a.gc.style[kwb]=b+rwb;a.dc.style[Rub]=f+rwb;a.dc.style[kwb]=b+rwb;a.hc.style[kwb]=b+c+rwb;if(a.Db){a.hc.style[Qub]=$U(a)+rwb;a.hc.style[eyb]=$U(a)+rwb}a.hc.style[Rub]=f+rwb;return f}
function H1(a){var b,c,d,e,f;if(D1==null){c='';d='';e='';b='';if(a.a.g){c='ff';d=c+a.a.b;e=d+a.a.c;b='gecko'}else if(a.a.e){c='sa';d='ch';b=Gyb}else if(a.a.q){c='sa';d=c+a.a.b;e=d+a.a.c;b=Gyb}else if(a.a.p){c='sa';d=c+a.a.b;e=d+a.a.c;b=Gyb}else if(a.a.j){c='ie';d=c+a.a.b;e=d+a.a.c;b='trident'}else if(a.a.f){c='edge';d=c+a.a.b;e=d+a.a.c;b=''}else if(a.a.o){c='op';d=c+a.a.b;e=d+a.a.c;b='presto'}D1='v-'+c;d.length==0||(D1=D1+' '+'v-'+d);e.length==0||(D1=D1+' '+'v-'+e);b.length==0||(D1=D1+' '+'v-'+b);f=J1(a);f!=null&&(D1=D1+' '+f);a.b&&(D1=D1+' '+'v-'+kxb)}return D1}
function sU(a,b){var c,d,e,f,g,h,i,j,k,l,m;i=a.a.p;j=false;for(e=new slb(b);e.a<e.c.a.length;){d=qlb(e);m=Ewb+d.col+Fwb+d.row;d.value==null?Xib(a.e,m):Vib(a.e,m,d);if(!gX(a,m,d.value,d.cellStyle,d.needsMeasure)){f=null;JV(a,d.col,d.row)?(f=Qkb(Qkb(a.kc,d.row-a.db),d.col-a.bb)):IV(a,d.col,d.row)&&(f=dV(a,d.col,d.row));if(f){g=Ewb+f.c+Fwb+f.k;k=!!i&&Tib(dQ(i.a).c,g);if(k){h=c$(i,g);h.b&&(j=true)}if(!(k&&!a.Fc)){MM(f,d.value,d.cellStyle,d.needsMeasure);f.g=true}}l=a.Tc>0?0:a.bb;for(;l<d.col;l++){c=WU(a,l,d.row);!!c&&(c.g=true)}}}ZX(a,false);j||s2((og(),ng),new BY(a))}
function Hi(a){if(a.offsetLeft==null){return 0}var b=0;var c=a.ownerDocument;var d=a.parentNode;if(d){while(d.offsetParent){b-=d.scrollLeft;c.defaultView.getComputedStyle(d,'').getPropertyValue('direction')=='rtl'&&(b+=d.scrollWidth-d.clientWidth);d=d.parentNode}}while(a){b+=a.offsetLeft;if(c.defaultView.getComputedStyle(a,'')[gvb]==hvb){b+=c.body.scrollLeft;return b}var e=a.offsetParent;e&&$wnd.devicePixelRatio&&(b+=parseInt(c.defaultView.getComputedStyle(e,'').getPropertyValue('border-left-width')));if(e&&e.tagName=='BODY'&&a.style.position==ivb){break}a=e}return b}
function iU(a,b){var c,d,e,f,g,h,i,j,k,l;l=new Mfb;for(k=b.row1;k<=b.row2;k++){for(c=b.col1;c<=b.col2;c++){l.a+=Exb+c+'.row'+k;(k!=b.row2||c!=b.col2)&&(l.a+=',',l)}}if(l.a.length!=0){l.a+='{ display: none; }';yT(a.Fb,l.a)}i=Ewb+b.col1+Fwb+b.row1;j=new PP(a,b.col1,b.row1);f='cs0';d=WU(a,b.col1,b.row1);!!d&&(f=d.b);MM(j,ZU(a,b.col1,b.row1),f,false);h=j.d;eh(h,bxb);WX(a,b,j);Wg(hV(a,b.col1,b.row1),h);Uib(a.Eb,Neb(b.id),j);!!a.r&&Tib(a.r,i)&&NM(j);!!a.tb&&a.tb.contains(i)&&OM(j);if(Tib(a.b,i)){e=Sib(a.b,i);UN(e,h,b.row1,b.col1)}if(!!a.T&&Tib(a.T,i)){g=Sib(a.T,i);hU(a,j,g)}}
function gW(a,b){a.Ac='spreadsheet-'+b;eh(a.Gc,a.Ac);a.w.type=Yxb;wh(a.w,a.Ac+'-dynamicStyle');Wg(a.Qb,a.w);a.Dc.type=Yxb;wh(a.Dc,a.Ac+'-sheetStyle');Wg(a.Qb,a.Dc);a.Ec.type=Yxb;wh(a.Ec,a.Ac+'-customCellSizeStyle');Wg(a.Qb,a.Ec);a.$.type=Yxb;wh(a.$,a.Ac+'-editedCellStyle');Wg(a.Qb,a.$);yT(a.$,'.notusedselector{ display: inline !important; outline: none !important; width: auto !important; z-index: -10; }');yT(a.$,'.notusedselector{ overflow: hidden; }');a.Fb.type=Yxb;wh(a.Fb,a.Ac+'-mergedRegionStyle');Wg(a.Qb,a.Fb);a.Wb.type=Yxb;wh(a.Wb,a.Ac+'-resizeStyle');Wg(a.Qb,a.Wb)}
function hN(b,c){var d,e,f,g,h,i,j;if(!b.Uc){return}h=-1;try{h=peb(Lj((JF(),b.Yc).style))}catch(a){a=_D(a);if(Zq(a,21)){h=evb}else throw aE(a)}h==-1&&(h=_M);if((F1(),!E1&&(E1=new O1),F1(),E1).a.j){oh((JF(),b.Yc),qwb);oh(b.Yc,Oub)}f=(!E1&&(E1=new O1),E1);if(f.a.j&&M1(f)){g=new X6((i=hh((JF(),b.Yc)),i-=ij($doc),i-=(bN==-1&&(bN=qN(jwb)),bN),i),(j=jh(b.Yc),j-=jj($doc),j-=(cN==-1&&(cN=qN(kwb)),cN),j),oh(b.Yc,Oub),oh(b.Yc,qwb));g.b+=er(g.d*(1-c)/2);g.c+=er(g.a*(1-c)/2);g.d=er(g.d*c);g.a=er(g.a*c);d=Xg(b.Yc);e=(!E1&&(E1=new O1),E1);if(e.a.j&&M1(e)){pN(eN(b),g);!Xg(b.t)&&Zg(d,b.t,b.Yc)}}}
function uZ(a,b){var c,d,e,f,g;f=(!a.D&&(a.D=new S0),a.D);e=(!a.L&&(a.L=new m1),a.L);if(b.Hf('componentIDtoCellKeysMap')){c=e.o;d=new qob;!!c&&c.a.c+c.b.c!=0&&Jpb(c,new f$(a,d));qX(f.V,d);e.$||H0(f,e.c)}if(b.Hf('cellKeysToEditorIdMap')){zZ(a);e.$||H0(f,e.c)}b.Hf(oyb)&&(e.$?E_(f,a.e):H0(f,e.c));(b.Hf('cellComments')||b.Hf('cellCommentAuthors'))&&K_(f,e.b,e.a);b.Hf('visibleCellComments')&&AZ(a);b.Hf('invalidFormulaCells')&&h0(f,e.F);b.Hf('overlays')&&(g=!(!a.L&&(a.L=new m1),a.L).N?(Rlb(),Rlb(),Plb):(!a.L&&(a.L=new m1),a.L).N,xZ(a,g.keySet()),qZ(a,g),a.c=g.keySet(),undefined);fY(f.V)}
function eT(a,b){var c,d;if(!a.c._){if(!a.b||(c=b.composedPath(),ssb(Flb(c,c.length),new lT))){return}d=(Gh(),b).keyCode|0;switch(d){case 8:case 113:case 38:case 40:case 37:case 39:case 9:case 46:case 32:if(Fh.Td(b)==0){y_(a.c.a,b,'');Fh.Xd(b);b.stopPropagation()}break;case 89:if(!a.a&&!!b.ctrlKey||!!b.metaKey){rab(a.c.a.W.C,jq(eq(KB,1),Jtb,1,5,[]));Fh.Xd(b);b.stopPropagation()}break;case 90:if(!a.a&&!!b.ctrlKey||!!b.metaKey){rab(a.c.a.W.G,jq(eq(KB,1),Jtb,1,5,[]));Fh.Xd(b);b.stopPropagation()}break;case 65:if(!a.a&&!!b.ctrlKey||!!b.metaKey){H_(a.c.a);Fh.Xd(b);b.stopPropagation()}}}}
function pq(a,b,c){var d,e,f,g,h,i;if(b.l==0&&b.m==0&&b.h==0){throw aE(new Cdb('divide by zero'))}if(a.l==0&&a.m==0&&a.h==0){c&&(lq=oq(0,0,0));return oq(0,0,0)}if(b.h==Pvb&&b.m==0&&b.l==0){return qq(a,c)}i=false;if(b.h>>19!=0){b=Eq(b);i=!i}g=wq(b);f=false;e=false;d=false;if(a.h==Pvb&&a.m==0&&a.l==0){e=true;f=true;if(g==-1){a=nq((Tq(),Pq));d=true;i=!i}else{h=Iq(a,g);i&&uq(h);c&&(lq=oq(0,0,0));return h}}else if(a.h>>19!=0){f=true;a=Eq(a);d=true;i=!i}if(g!=-1){return rq(a,g,i,f,c)}if(Bq(a,b)<0){c&&(f?(lq=Eq(a)):(lq=oq(a.l,a.m,a.h)));return oq(0,0,0)}return sq(d?a:oq(a.l,a.m,a.h),b,i,f,e,c)}
function JW(a,b,c){var d,e;zh(a.zc,b);Ah(a.zc,c);a.oc=(a.zc.offsetHeight||0)|0;a.pc=(a.zc.offsetWidth||0)|0;a.Ob=b;a.Pb=c;a.db=1;a.eb=0;a.Tc>0&&(a.db=a.Tc+1);a.bb=1;a.cb=0;a.ob>0&&(a.bb=a.ob+1);a.xb=0;DU(a);zU(a);d=a.a.j;if(a.cb<b-d){do{a.cb+=K$(a.a,a.bb);++a.bb}while(a.cb<b-d)}a.xb=a.bb;a.yb=a.cb+K$(a.a,a.bb);e=a.a.L;if(a.eb<c-e){do{a.db>=a.a.M.length?(a.eb+=bV(a)):(a.eb+=lV(a,a.db));++a.db}while(a.eb<c-e)}a.zb=a.db;a.Ab=a.eb+lV(a,a.zb);while(a.yb<b+a.pc+d&&a.xb<a.a.i){++a.xb;a.yb+=K$(a.a,a.xb)}while(a.Ab<c+a.oc+e&&a.zb<a.a.O){++a.zb;a.zb>=a.a.M.length?(a.Ab+=bV(a)):(a.Ab+=lV(a,a.zb))}}
function Qg(a,b){var c,d,e,f,g,h,i,j,k;j='';if(b.length==0){return a.Od(Ktb,Htb,-1,-1)}k=zfb(b);jfb(k.substr(0,3),'at ')&&(k=k.substr(3));k=k.replace(/\[.*?\]/g,'');g=k.indexOf('(');if(g==-1){g=k.indexOf('@');if(g==-1){j=k;k=''}else{j=zfb(k.substr(g+1));k=zfb(k.substr(0,g))}}else{c=k.indexOf(')',g);j=k.substr(g+1,c-(g+1));k=zfb(k.substr(0,g))}g=lfb(k,Bfb(46));g!=-1&&(k=k.substr(g+1));(k.length==0||jfb(k,'Anonymous function'))&&(k=Htb);h=ofb(j,Bfb(58));e=pfb(j,Bfb(58),h-1);i=-1;d=-1;f=Ktb;if(h!=-1&&e!=-1){f=j.substr(0,e);i=Kg(j.substr(e+1,h-(e+1)));d=Kg(j.substr(h+1))}return a.Od(f,k,i,d)}
function IS(a,b,c,d,e){var f;th(a.k,Ewb+a.b+Fwb+a.n);if(a.g>0&&b<a.g){b=a.g;a.d.style[ovb]=(wm(),Bvb)}else{a.d.style[ovb]=(wm(),Avb)}if(a.i>0&&d<a.i){d=a.i;a.q.style[ovb]=(wm(),Bvb)}else{a.q.style[ovb]=(wm(),Avb)}if(a.f>0&&e>a.f){e=a.f;a.a.style[ovb]=(wm(),Bvb)}else{a.a.style[ovb]=(wm(),Avb)}if(a.e>0&&a.e<c){c=a.e;a.j.style[ovb]=(wm(),Bvb)}else{a.j.style[ovb]=(wm(),Avb)}a.b=b;a.n=d;a.c=c;a.o=e;if(b<=c&&d<=e){eh(a.k,Ewb+a.b+Fwb+a.n);Ee((JF(),a.Yc),true);a.Yc.style[uwb]='';MS(a);f=a.p.q.V.W;f!=null&&f.length!=0&&GS(a,$R(a.p.q.V.W,a.n,a.o+1))}else{Ee((JF(),a.Yc),false);a.Yc.style[uwb]=(rl(),Bvb)}}
function tV(a,b,c){var d,e,f,g,h,i,j,k;a.Zb=true;d=b-a.Sb;d<0&&(d=0);vT(a.Wb);d>0?HI(a.Yb,'Width: '+d+rwb):HI(a.Yb,Sxb);j='.'+a.Ac+Txb+a.$b+'{width:'+d+Uxb;yT(a.Wb,j);e=0;k=ih(a.zc)-b;for(g=a.$b+1;g<=a.xb&&e<k;g++){e+=K$(a.a,g)}i=b-a.Tb;i<a.Sb-a.Tb&&(i=a.Sb-a.Tb);j='';for(h=a.$b+1;h<=a.xb;h++){j+='.'+a.Ac+Txb+h;a.xb!=h&&(j+=',')}if(!!a.ib&&a.$b>=a.ib.a.length){for(f=1;f<=a.ib.a.length;f++){i+=K$(a.a,f)}}i=a.Cb+i;(!a.ib||a.$b>a.ib.a.length)&&(i-=qh(a.zc));if(j.length!=0){j+='{margin-left:'+i+Uxb;yT(a.Wb,j)}j='.'+a.Ac+'.col-resizing > div.resize-line.ch {margin-left:'+(i-1)+Uxb;yT(a.Wb,j);sX(a,b,c)}
function f3(a,b){var c,d;if(!a.e){a.e=new JI;Ie(a.e,new h3(a),(ln(),ln(),kn));Ie(a.e,new j3(a),($o(),$o(),Zo))}c=zfb(qI(a.e.a));c+=c.length==0?'Using Evaluation License of: ':', ';HI(a.e,c+b);TH((QK(),UK()),a.e);ie(a.e).className='';d=ie(a.e).style;d[gvb]=(Cl(),hvb);d[axb]=(Nl(),'center');d['right']=(im(),swb);d[jwb]=swb;d['bottom']=swb;d['padding']='0.5em 1em';d['font-family']='sans-serif';d['fontSize']='12.0px';d[eyb]='1.1em';d['color']='white';d[Xwb]='black';(Gh(),d).opacity=0.7;d[pvb]='2147483646';d[kwb]=Bwb;d[Rub]=Bwb;d[nvb]=(Ak(),tvb);d['whiteSpace']=(Im(),'normal');d[ovb]=(wm(),Avb);d['margin']=swb}
function JX(a,b,c,d,e,f,g){var h,i,j,k,l,m;if(f.a.length==0){return}j=new slb(g);l=null;m=-1;i=a.a.p;while(j.a<j.c.a.length){h=qlb(j);if(h.row>=b&&h.row<=c&&h.col>=d&&h.col<=e){if(m!=h.row){(otb(0,f.a.length),f.a[0]).a.length>0&&Qkb((otb(0,f.a.length),f.a[0]),0).k!=b&&(b=Qkb((otb(0,f.a.length),f.a[0]),0).k);l=Qkb(f,h.row-b);m=h.row;(otb(0,l.a.length),l.a[0]).c!=d&&(d=(otb(0,l.a.length),l.a[0]).c)}a.rc==h.col&&a.sc==h.row&&!!i&&d$(i,Ewb+h.col+Fwb+h.row)||MM(Qkb(l,h.col-d),h.value,h.cellStyle,h.needsMeasure)}k=Ewb+h.col+Fwb+h.row;gX(a,k,h.value,h.cellStyle,h.needsMeasure);h.value==null?Xib(a.e,k):Vib(a.e,k,h)}ZX(a,false)}
function S1(j,a){var b=j.a;var c=j.b;if(a.indexOf(Ywb)>-1&&a.indexOf('Width')>-1){var d=a.substring(0,a.length-5)+'Style';if(b.getPropertyValue)var e=b.getPropertyValue(d);else var e=b[d];if(e==Pub)return '0px'}if(b.getPropertyValue){a=a.replace(/([A-Z])/g,'-$1').toLowerCase('en');var f=b.getPropertyValue(a)}else{var f=b[a];var g=c.style;if(!/^\d+(px)?$/i.test(f)&&/^\d/.test(f)){var h=g.left,i=c.runtimeStyle.left;c.runtimeStyle.left=b.left;g.left=f||0;f=g.pixelLeft+rwb;g.left=h;c.runtimeStyle.left=i}}if(a.indexOf('margin')>-1&&f==Bwb){return '0px'}a==Rub&&f==Bwb?(f=c.clientWidth+rwb):a==Qub&&f==Bwb&&(f=c.clientHeight+rwb);return f}
function y5(a,b){var c,d,e,f,g,h,i;if(!a.j){f=Xf()-a.b[0];Hrb(Qrb((Rdb(Qz),Qz.k)),f+' ms from start to move')}e=(h=(Gh(),b).changedTouches[0],a.f=ai(h.clientY||0),i=a.k++,i=i%3,a.b[i]=Xf(),a.s[i]=a.f,a.j?a.j:$wnd.Math.abs(a.o-a.f)>=3);if(e){c=a.o-a.f;d=a.n+c;if(d>((a.q.scrollHeight||0)|0)-(a.q.clientHeight|0)){g=c+a.n-(((a.q.scrollHeight||0)|0)-(a.q.clientHeight|0));g=g/2|0;g>(o5?0:(a.q.clientHeight|0)/3|0)&&(g=o5?0:(a.q.clientHeight|0)/3|0);c=((a.q.scrollHeight||0)|0)-(a.q.clientHeight|0)+g-a.n}else if(d<0){g=d/2|0;-g>(o5?0:(a.q.clientHeight|0)/3|0)&&(g=-(o5?0:(a.q.clientHeight|0)/3|0));c=g-a.n}B5(a,c);a.j=true;Fh.Xd(b);b.stopPropagation()}}
function Dc(){Dc=HE;Bc=new Mb('aria-activedescendant');new yc('aria-atomic');new Mb('aria-autocomplete');new Mb('aria-controls');new Mb('aria-describedby');new Mb('aria-dropeffect');new Mb('aria-flowto');new yc('aria-haspopup');Cc=new yc('aria-label');new Mb('aria-labelledby');new yc('aria-level');new Mb('aria-live');new yc('aria-multiline');new yc('aria-multiselectable');new Mb('aria-orientation');new Mb('aria-owns');new yc('aria-posinset');new yc('aria-readonly');new Mb('aria-relevant');new yc('aria-required');new yc('aria-setsize');new Mb('aria-sort');new yc('aria-valuemax');new yc('aria-valuemin');new yc('aria-valuenow');new yc('aria-valuetext')}
function vZ(a,b){var c,d,e,f;d=(!a.D&&(a.D=new S0),a.D);c=(!a.L&&(a.L=new m1),a.L);if(c.O||b.b){c.O=false;e=(!a.L&&(a.L=new m1),a.L);f=(!a.D&&(a.D=new S0),a.D);zZ(a);G0(f,e.X,e.W,b.Hf(pyb));x0(f,e.Y);f.C?C$(f,false):(f.C=true);V$(f,f.a-1);M0(f,e.L);s2((og(),ng),new a$(a,b))}else{(b.Hf('sheetNames')||b.Hf('sheetIndex'))&&G0(d,c.X,c.W,b.Hf(pyb));if(b.Hf(qyb)||b.Hf(ryb)||b.Hf('colW')||b.Hf('rowH')||b.Hf('rows')||b.Hf('cols')||b.Hf(syb)||b.Hf(tyb)){d.d?(d.d=false):rW(d.V,true);M0((!a.D&&(a.D=new S0),a.D),(!a.L&&(a.L=new m1),a.L).L)}else b.Hf('mergedRegions')&&M0((!a.D&&(a.D=new S0),a.D),(!a.L&&(a.L=new m1),a.L).L);b.Hf('sheetProtected')&&x0(d,c.Y);uZ(a,b)}}
function QW(a,b,c){var d,e,f,g,h,i,j,k,l,m;l=false;f=eV(a);if(b<f&&b>a.ob){k=0;for(e=f-1;e>=b-1&&e>0;e--){k+=K$(a.a,e)}zh(a.zc,qh(a.zc)-k);(b<=a.bb||k>(a.a.j/2|0))&&(l=true)}else{j=jV(a);if(b>j){k=0;g=a.a.i;for(e=j+1;e<=b+1&&e<=g;e++){k+=K$(a.a,e)}zh(a.zc,qh(a.zc)+k);(b>=a.xb||k>(a.a.j/2|0))&&(l=true)}}m=sV(a);if(c<m&&c>a.Tc){k=0;for(e=m-1;e>=c-1&&e>0;e--){k+=Q$(a.a,e)?0:e>=a.W.length?bV(a):a.W[e-1]}i=((a.zc.scrollTop||0)|0)-k;Ah(a.zc,i>0?i:0);(c<=a.db||k>(a.a.L/2|0))&&(l=true)}else{d=VU(a);if(c>d){k=0;h=a.a.O;for(e=d+1;e<=c+1&&e<=h;e++){k+=Q$(a.a,e)?0:e>=a.W.length?bV(a):a.W[e-1]}Ah(a.zc,((a.zc.scrollTop||0)|0)+k);(c>=a.zb||k>(a.a.L/2|0))&&(l=true)}}if(l){cW(a);YV(a)}}
function c2(a,b){var c,d,e,f,g,h,i,j,k,l,m,n;e=false;l=false;d=new T1(b);i=(m=jq(eq(ir,1),Rxb,17,15,[0,0,0,0]),m[0]=Q1(d,Bxb),m[1]=Q1(d,'paddingRight'),m[2]=Q1(d,'paddingBottom'),m[3]=Q1(d,Axb),m);if(!e&&g2(a.d,i)){b2(a.d,i);e=true}if(!l&&h2(a.d,i)){b2(a.d,i);l=true}a.d=i;f=R1(d);if(!e&&g2(a.c,f)){b2(a.c,f);e=true}if(!l&&h2(a.c,f)){b2(a.c,f);l=true}a.c=f;c=(n=jq(eq(ir,1),Rxb,17,15,[0,0,0,0]),n[0]=Q1(d,'borderTopWidth'),n[1]=Q1(d,'borderRightWidth'),n[2]=Q1(d,'borderBottomWidth'),n[3]=Q1(d,'borderLeftWidth'),n);if(!e&&g2(a.a,c)){b2(a.a,c);e=true}if(!l&&h2(a.a,c)){b2(a.a,c);l=true}a.a=c;j=D2(b);g=j+(f[0]+f[2]);d2(a,g)&&(e=true);k=G2(b);h=k+(f[1]+f[3]);e2(a,h)&&(l=true);return new i2}
function gU(a){a.U=Qrb('spreadsheet SheetWidget');a.qc=new qob;a.Gc=Ri($doc);a.zc=Ri($doc);a.N=Ri($doc);a.fb=Ri($doc);a.Ub=Ri($doc);a.Vb=Ri($doc);a.ic=new Wkb;a.jb=new Wkb;a.K=new Wkb;a.ib=new Wkb;a.kc=new Wkb;a.Nc=new Wkb;a.Rc=new Wkb;a.d=new Wkb;a.w=_i($doc);a.Dc=_i($doc);a.Ec=_i($doc);a.$=_i($doc);a.Wb=_i($doc);a.Fb=_i($doc);a.Mb=Ri($doc);a.Oc=Ri($doc);a.Qc=Ri($doc);a.c=Ri($doc);a.I=Ri($doc);a.gc=Ri($doc);a.F=Ri($doc);a.dc=Ri($doc);a.kb=Ri($doc);a.J=Ri($doc);a.hc=Ri($doc);a.D=Ri($doc);a.cc=Ri($doc);a.hb=$i($doc);a.wb=new DT;a.t=new vob;a.u=new vob;a.wc=new vob;a.tc=new vob;a.vc=new vob;a.uc=new vob;a.p=new h6(300,new pY(a));a.Ib=new h6(100,new JY(a));a.mc=new LY(a);a.Jb=new PY(a)}
function hgb(a,b){var c,d,e,f,g,h,i,j,k,l;j=(!a.c&&(a.c=mhb(a.f)),a.c);k=(!b.c&&(b.c=mhb(b.f)),b.c);c=a.e-b.e;g=0;e=1;h=cgb.length-1;if(b.a==0&&b.f!=-1){throw aE(new Cdb('Division by zero'))}if(j.e==0){return Bgb(c)}d=Qgb(j,k);j=Mgb(j,d);k=Mgb(k,d);f=Sgb(k);k=Zgb(k,f);do{l=Ngb(k,cgb[e]);if(l[1].e==0){g+=e;e<h&&++e;k=l[0]}else{if(e==1){break}e=1}}while(true);if(!Ogb(k.e<0?new bhb(1,k.d,k.a):k,(Igb(),Dgb))){throw aE(new Cdb('Non-terminating decimal expansion; no exact representable decimal result'))}k.e<0&&(j=j.e==0?j:new bhb(-j.e,j.d,j.a));i=xgb(c+$wnd.Math.max(f,g));e=f-g;j=e>0?(Uhb(),e<Thb.length?Zhb(j,Thb[e]):e<Rhb.length?Vgb(j,Rhb[e]):Vgb(j,Wgb(Rhb[1],e))):Ygb(j,-e);return new rgb(j,i)}
function PX(a,b){var c,d,e;a.kb.style[kwb]=b+(im(),rwb);c=0;if(a.H>0){d=a.Z?a.H+1:a.H;c=3+d*18}e=0;a.fc>0&&(e=1+(a.fc+1)*15);if(c==0){a.I.style[nvb]=(Ak(),Pub);a.J.style[nvb]=Pub}else{a.I.style[nvb]=(Ak(),tvb);a.J.style[nvb]=tvb}a.Z||(a.J.style[nvb]=(Ak(),Pub),undefined);!!a.ib&&a.H>0?(a.F.style[nvb]=(Ak(),tvb),undefined):(a.F.style[nvb]=(Ak(),Pub),undefined);a.I.style[Qub]=c+rwb;a.I.style[kwb]=b+rwb;a.F.style[Qub]=c+rwb;a.F.style[kwb]=b+rwb;a.J.style[kwb]=b+rwb;a.J.style[Qub]=c+rwb;a.Db&&(a.J.style[Rub]=kV(a)+rwb,undefined);a.J.style[jwb]=e+rwb;a.D.style[kwb]=b+rwb;a.D.style[jwb]=e+rwb;a.D.style[Qub]=c+rwb;a.cc.style[kwb]=b+c+rwb;a.cc.style[jwb]=swb;a.cc.style[Rub]=e+rwb;a.g=e;a.f=c;return c}
function QO(a,b){var c,d;switch((Gh(),b).keyCode|0){case 8:case 46:a.t.Z?qb(new HP(a),100):s2((og(),ng),new JP(a));s2((og(),ng),new LP(a));break;case 27:iL(a.j,a.c);j_(a.t);iP(a);b.stopPropagation();Fh.Xd(b);break;case 13:i_(a.t,(d=gL(a.j),d==null?'':d));iP(a);b.stopPropagation();Fh.Xd(b);break;case 9:m_(a.t,(c=gL(a.j),c==null?'':c),!b.shiftKey);iP(a);b.stopPropagation();break;case 38:if(a.g){UO(a,!!b.shiftKey,true,false,false);Fh.Xd(b)}break;case 39:if(a.g){UO(a,!!b.shiftKey,false,true,false);Fh.Xd(b)}break;case 40:if(a.g){UO(a,!!b.shiftKey,false,false,true);Fh.Xd(b)}break;case 37:if(a.g){UO(a,!!b.shiftKey,false,false,false);Fh.Xd(b)}break;default:JO(a,a.j);}if(a.e){nP(a,false);s2((og(),ng),new xP(a))}}
function xV(a,b,c){var d,e,f,g,h,i,j,k;a.Zb=true;d=c-a.Sb;d<0&&(d=0);vT(a.Wb);d>0?HI(a.Yb,'Height: '+d+'px \u2248 '+igb(zgb(d/a.Lb*72))+'pt'):HI(a.Yb,'Hide row');j='.'+a.Ac+Vxb+a._b+'{height:'+d+Uxb;yT(a.Wb,j);e=0;k=gh(a.zc)-c;for(g=a._b+1;g<=a.zb&&e<k;g++){e+=Q$(a.a,g)?0:g>=a.W.length?bV(a):a.W[g-1]}i=c-a.Tb;i<a.Sb-a.Tb&&(i=a.Sb-a.Tb);j='';for(h=a._b+1;h<=a.zb;h++){j+='.'+a.Ac+Vxb+h;a.zb!=h&&(j+=',')}if(!!a.jb&&a._b>=a.jb.a.length){for(f=1;f<=a.jb.a.length;f++){i+=Q$(a.a,f)?0:f>=a.W.length?bV(a):a.W[f-1]}}i+=a.Pc;(!a.jb||a._b>a.jb.a.length)&&(i-=(a.zc.scrollTop||0)|0);if(j.length!=0){j+='{margin-top:'+i+Uxb;yT(a.Wb,j)}j='.'+a.Ac+'.row-resizing > div.resize-line.rh {margin-top:'+(i-1)+Uxb;yT(a.Wb,j);sX(a,b,c)}
function $V(a,b){var c,d,e,f,g,h,i,j,k,l,m;if(!!(Gh(),b).changedTouches&&b.changedTouches.length>0){k=b.changedTouches;i=Tm(k[k.length-1])}else if(!!b.touches&&b.touches.length>0){k=b.touches;i=Tm(k[k.length-1])}else{i=kY(b)}m=(g=pj($doc),w2(),b.type.indexOf(kxb)!=-1?Sm(b.changedTouches[0])+g:ai(b.clientY||0)+g);l=(f=oj($doc),b.type.indexOf(kxb)!=-1?Rm(b.changedTouches[0])+f:ai(b.clientX||0)+f);if(uU(a,m,l)){return}d=0;e=0;c=null;if(i){c=i.getAttribute($ub)||'';AT(a.wb,c);d=a.wb.a;e=a.wb.b}if(e==0||d==0){return}h=bxb.length;if(!jfb(c.substr(c.length-h,h),bxb)){j=iV(a,l,m,WU(a,d,e));d=j.c;e=j.k}if(d!=a.Kc||e!=a.Lc){d==0&&(l>ih(Kh(i))?(d=jV(a)+1):(d=a.Kc));e==0&&(m>gh(a.zc)?(e=VU(a)+1):(e=a.Lc));v_(a.a,d,e);a.Kc=d;a.Lc=e}}
function $G(a){switch(a){case 'blur':return 4096;case 'change':return 1024;case 'click':return 1;case 'dblclick':return 2;case 'focus':return 2048;case 'keydown':return 128;case 'keypress':return 256;case 'keyup':return 512;case lvb:return Xvb;case 'losecapture':return 8192;case 'mousedown':return 4;case 'mousemove':return 64;case 'mouseout':return 32;case 'mouseover':return 16;case Gvb:return 8;case 'scroll':return Ztb;case 'error':return aub;case Yvb:case 'mousewheel':return Zvb;case mvb:return $vb;case 'paste':return Pvb;case Kvb:return _vb;case 'touchmove':return awb;case 'touchend':return Rtb;case Ivb:return bwb;case 'gesturestart':return cwb;case 'gesturechange':return dwb;case 'gestureend':return ewb;default:return -1;}}
function wW(b,c){var d,e,f,g,h,i,j;h=Ewb+c.col1+Fwb+c.row1;b.Fb.sheet.deleteRule(0);i=Rib(b.Eb,Neb(c.id));j=WU(b,c.col1,c.row1);!!j&&MM(j,i.o,i.b,false);bh(Wib(b.Eb,Neb(c.id)).d);Wib(b.Kb,c);c.col1>=b.yc.e&&c.col2<=b.yc.f&&c.row1>=b.yc.K&&c.row2<=b.yc.L&&cY(b,c.col1,c.col2,c.row1,c.row2,false);f=null;if(!!b.r&&Tib(b.r,h)){try{d=Qkb(Qkb(b.kc,c.row1-b.db),c.col1-b.bb);NM(d);f=d.d}catch(a){a=_D(a);if(!Zq(a,21))throw aE(a)}}if(!!b.tb&&b.tb.contains(h)){try{d=Qkb(Qkb(b.kc,c.row1-b.db),c.col1-b.bb);OM(d);f=d.d}catch(a){a=_D(a);if(!Zq(a,21))throw aE(a)}}if(Tib(b.b,h)&&!!f){e=Sib(b.b,h);UN(e,f,c.row1,c.col1)}if(!!b.T&&Tib(b.T,h)){try{d=Qkb(Qkb(b.kc,c.row1-b.db),c.col1-b.bb);g=Sib(b.T,h);hU(b,d,g)}catch(a){a=_D(a);if(!Zq(a,21))throw aE(a)}}}
function jY(a,b){var c;gU(this);c=P1().toLowerCase();this.vb=c.indexOf('macintosh')!=-1||c.indexOf('mac osx')!=-1||c.indexOf('mac os x')!=-1;this.a=a;this.Sc=b;this.e=new qob;this.b=new qob;this.Bc=new qob;this.Eb=new qob;this.Kb=new qob;this.rb=new d6;re(this.rb,'v-spreadsheet-hyperlink-tooltip-label');this.qb=new EN;re(this.qb,'v-tooltip');this.qb.r=this;vI(this.qb,this.rb);this.Yb=new d6;re(this.Yb,'v-spreadsheet-resize-tooltip-label');this.Xb=new EN;re(this.Xb,'v-tooltip');this.Xb.r=this;vI(this.Xb,this.Yb);this.q=new VN(this,this.zc);IN(this.q);BV(this);Be((JF(),this.Yc),ayb,true);this.yc=new vS(a,this);this.M=new vO(this,new rO(this));Wg(this.Yc,ie(this.M));CV(this);this.lc=new h6(20,new RY(this));this.Rb=new h6(100,new TY(this))}
function fY(a){var b,c,d,e,f,g,h,i;d=a.ob>0?1:0;kh(a.Gc).indexOf('report')!=-1&&(d=0);i=0;a.ic.a.length==0||(i=kV(a));f=0;a.K.a.length==0||(f=$U(a));e=0;if(a.a.u){g=new f2;c2(g,ie(a.a.u));e=er(g.b)}b=PX(a,e);c=QX(a,e);a.kb.style[kwb]=e+(im(),rwb);c==0||b==0?(a.kb.style[nvb]=(Ak(),Pub),undefined):(a.kb.style[nvb]=(Ak(),tvb),undefined);a.kb.style[Qub]=b+rwb;a.kb.style[Rub]=c+rwb;s2((og(),ng),new HY(a));if(!a.Z){i=0;f=0}a.Pc=f+e+b;a.Cb=i+c;h=a.Oc.style;h[Rub]=a.Bb+i+1+rwb;h[Qub]=a.Mc+f+rwb;h[kwb]=e+b+rwb;h[jwb]=c+rwb;h=a.Qc.style;h[jwb]=a.Bb+a.Cb+d+rwb;h[Qub]=a.Mc+f+rwb;h[kwb]=e+b+rwb;h=a.c.style;h[Rub]=a.Bb+i+1+rwb;h[kwb]=a.Mc+a.Pc+rwb;h[jwb]=c+rwb;h=a.zc.style;h[jwb]=a.Bb+a.Cb+d+rwb;h[kwb]=a.Mc+a.Pc+rwb;h=a.N.style;h[kwb]=e+b+rwb;h[jwb]=c+rwb}
function TM(a){var b,c,d,e,f,g,h,i,j,k,l,m;k=kh(a.d).indexOf(' r ')!=-1||ifb(kh(a.d),' r');e=J$(a.n.a,a.c);l=Rib(a.n.qc,new YM(a.o,a.b,a.k,a.c));!l&&(l=Neb(HM(a)));j=l.a-e;if(!k&&j>0){j+=2;c=a.c;m=0;d=a.n.a.g;g=MV(a.n,c);while(c<d.length&&m<j){if(g&&!MV(a.n,c+1)){break}h=WU(a.n,c+1,a.k);if(!!h&&h.o!=null&&h.o.length!=0){break}m+=d[c];++c}m+=e;i=Ri($doc);i.style[Gwb]=Pub;i.style[Rub]=m+(im(),rwb);i.style[uwb]=(rl(),Bvb);i.style['textOverflow']=(Wl(),'ellipsis');b=a.d.childNodes;if(b){for(f=b.length-1;f>=0;f--){i.appendChild(b[f])}}a.d.innerHTML='';Wg(a.d,i);GM(a);a.i=true}else{a.i=false}PV(a.n,Ewb+a.c+Fwb+a.k)&&!Zq(a,146)?(a.d.style[uwb]=(rl(),Bvb),undefined):j>0?(a.d.style[uwb]=(rl(),Avb),undefined):(a.d.style[uwb]=(rl(),Bvb),undefined);a.g=false}
function Bhb(a,b,c,d,e,f){var g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,A;q=gq(ir,Rxb,17,d+1,15,1);r=gq(ir,Rxb,17,f+1,15,1);s=f;i=Keb(e[f-1]);if(i!=0){rhb(r,e,0,i);rhb(q,c,0,i)}else{Sfb(c,0,q,0,d);Sfb(e,0,r,0,f)}j=r[s-1];l=b-1;m=d;while(l>=0){k=0;if(q[m]==j){k=-1}else{t=bE(qE(cE(q[m],yAb),32),cE(q[m-1],yAb));w=Ehb(t,j);k=wE(w);v=wE(rE(w,32));if(k!=0){o=0;A=0;u=false;++k;do{--k;if(u){break}o=nE(cE(k,yAb),cE(r[s-2],yAb));A=bE(qE(v,32),cE(q[m-2],yAb));p=bE(cE(v,yAb),cE(j,yAb));Keb(wE(sE(p,32)))<32?(u=true):(v=wE(p))}while(iE(yE(o,CAb),yE(A,CAb)))}}if(k!=0){g=Hhb(q,m-s,r,s,k);if(g!=0){--k;h=0;for(n=0;n<s;n++){h=bE(h,bE(cE(q[m-s+n],yAb),cE(r[n],yAb)));q[m-s+n]=wE(h);h=sE(h,32)}}}a!=null&&(a[l]=k);--m;--l}if(i!=0){uhb(r,s,q,0,i);return r}Sfb(q,0,r,0,f);return q}
function JN(a){var b,c,d,e,f,g,h,i,j,k,l,m,n,o;h=ih(a.c);e=(a.c.offsetLeft||0)|0;g=(a.c.offsetWidth||0)|0;f=(a.c.offsetTop||0)|0;i=jh(a.c);k=h+15;if(k+a.n>ih(a.o)){o=hh(a.c)-15-a.n;hh(a.o)<o&&(k=o)}l=i-15;m=gh(a.o);if(l+a.k>m){l-=l+a.k-m+5;n=jh(a.o);l<n&&(l=n)}else l<jh(a.o)&&(l+=jh(a.o)-l);lN(a,k,l);a.j!=null&&th(a.i,a.j);a.j=Ewb+a.b+Fwb+a.d;l+=2;k+=2;c=i-l;if(k>h){b=k-h;if(c>0){j=-($wnd.Math.atan(c/b)*Pwb)}else{c=$wnd.Math.abs(c);j=0}}else{k-=2;b=h-(k+a.n);if(c>0){j=-180+$wnd.Math.atan(c/b)*Pwb}else{c=$wnd.Math.abs(c);j=-180}}d=$wnd.Math.sqrt(b*b+c*c)+1;a.i.style[Rub]=d+(im(),rwb);a.i.style[kwb]=f+rwb;a.i.style[jwb]=e+g+rwb;a.i.style['transform']=Qwb+j+'deg)';a.i.style['msTransform']=Qwb+j+'deg)';a.i.style[Rwb]=Qwb+j+'deg)';eh(a.i,a.j);Wg(a.o,a.i)}
function PW(a,b,c,d){var e,f,g,h,i,j,k;j=false;b<=a.Tc&&(b=a.Tc+1);k=sV(a);e=VU(a);if(d){if(b<k){i=0;for(f=k-1;f>=b-1&&f>0;f--){i+=Q$(a.a,f)?0:f>=a.W.length?bV(a):a.W[f-1]}h=((a.zc.scrollTop||0)|0)-i;Ah(a.zc,h>0?h:0);(b<=a.db||i>(a.a.L/2|0))&&(j=true)}else if(b>e){i=0;g=a.a.O;for(f=e+1;f<=b+1&&f<=g;f++){i+=Q$(a.a,f)?0:f>=a.W.length?bV(a):a.W[f-1]}Ah(a.zc,((a.zc.scrollTop||0)|0)+i);(b>=a.zb||i>(a.a.L/2|0))&&(j=true)}}else{if(c>e){i=0;g=a.a.O;for(f=e+1;f<=c+1&&f<=g;f++){i+=Q$(a.a,f)?0:f>=a.W.length?bV(a):a.W[f-1]}Ah(a.zc,((a.zc.scrollTop||0)|0)+i);(c>=a.zb||i>(a.a.L/2|0))&&(j=true)}else if(c<k){i=0;for(f=k-1;f>=c-1&&f>0;f--){i+=Q$(a.a,f)?0:f>=a.W.length?bV(a):a.W[f-1]}h=((a.zc.scrollTop||0)|0)-i;Ah(a.zc,h>0?h:0);(c<=a.db||i>(a.a.L/2|0))&&(j=true)}}return j}
function x5(a){var b,c,d,e,f,g,h,i,j;if(!a.j){p5=null;AM(a.d.a);a.d=null;return}b=a.n+a.a;g=((a.q.scrollHeight||0)|0)-(a.q.clientHeight|0);c=-1;if(b>g){i=g-b;d=g}else if(b<0){i=-b;d=0}else{h=s5(a);Hrb(Qrb((Rdb(Qz),Qz.k)),'pxPerMs'+h);i=er(0.5*h*h/0.002);h<0&&(i=-i);d=b+i;if(d>g+(o5?0:(a.q.clientHeight|0)/3|0)){d=((a.q.scrollHeight||0)|0)-(a.q.clientHeight|0)+(o5?0:(a.q.clientHeight|0)/3|0);e=d-b;i=e}else if(d<-(o5?0:(a.q.clientHeight|0)/3|0)){d=-(o5?0:(a.q.clientHeight|0)/3|0);e=d-b;i=e}else{c=er($wnd.Math.abs(h/0.002))}}c==-1&&(c=350);if(c>1500){Hrb(Qrb((Rdb(Qz),Qz.k)),'Max animation time. '+c);c=1500}a.c=d;if($wnd.Math.abs(i)<3||c<20){Hrb(Qrb((Rdb(Qz),Qz.k)),"Small 'momentum' "+i+' |  '+c+' Skipping animation,');w5(a);return}j=-d+a.n;f=-b+a.n;if(o5){f-=a.n;j-=a.n}D5(a,c,f,j)}
function pP(a,b){FO();var c,d,e;this.r=(JF(),Ri($doc));this.i=new Wkb;this.F=new vob;this.D=new qob;this.G=new qob;this.t=a;this.I=b;this.w=b.sb;this.H=new qT;pT(this.H,b,this);this.j=new oL;df(this.j,2);this.a=new oL;df(this.a,1);re(this.j,'functionfield');re(this.a,'addressfield');this.B=new mJ;re(this.B,'namedrangebox');kJ(this.B);dJ(this.B,'');this.C=new KI;re(this.C,'arrow');ue(this.B,false);ue(this.C,false);d=new uI;c=new uI;e=new uI;c.Yc.className='fixed-left-panel';e.Yc.className='adjusting-right-panel';tI(c,this.a);tI(c,this.C);tI(c,this.B);tI(e,this.j);OH(d,c,d.Yc);OH(d,e,d.Yc);jI(this,d);this.Yc.className='functionbar';dG(ie(this.B),1024);cG(ie(this.B),new rP(this));dG(ie(this.a),6656);cG(ie(this.a),new zP(this));dG(ie(this.j),7048);cG(ie(this.j),new BP(this));this.r.className='formulaoverlay';Wg(this.Yc,this.r)}
function IX(b,c,d){var e,f,g,h,i,j,k;g=(JF(),$G((Gh(),c).type));j=d.getAttribute($ub)||'';if(jfb(j,Cwb)||jfb(j,Dwb)){e=Kh(d);f=e.getAttribute($ub)||'';i=bxb.length;jfb(f.substr(f.length-i,i),bxb)&&(f=rfb(f,byb,''));if(Tib(b.b,f)){return}if(g==16){if(!(YJ(b.q)&&jfb(f,b.j))){AT(b.wb,f);b.k=b.wb.a;b.n=b.wb.b;g6(b.p)}}else{k=Fh.Vd(c);if(!b.o&&!(!!k&&!!k.equals?k.equals(e):k==e)){KN(b.q);b.j=null;b.k=-1;b.n=-1}}}else{i=bxb.length;jfb(j.substr(j.length-i,i),bxb)&&(j=rfb(j,byb,''));if(Tib(b.b,j)){return}if(g==16){if(!(YJ(b.q)&&jfb(j,b.j))){bG(b.zc);AT(b.wb,j);b.k=b.wb.a;b.n=b.wb.b;g6(b.p)}}else if(g==32){k=Fh.Vd(c);if(!b.o&&!!k&&!!Kh(k)){try{if(!(fW(k.getAttribute($ub)||'')&&nf(Kh(k),d))){KN(b.q);b.j=null;b.n=-1;b.k=-1}}catch(a){a=_D(a);if(Zq(a,50)){h=a;Orb(b.U,'SheetWidget:updateCellCommentDisplay: NPE ONMOUSEOUT, '+h.f)}else throw aE(a)}}}}}
function fgb(){fgb=HE;var a,b,c;new pgb(1,0);new pgb(10,0);new pgb(0,0);Yfb=gq(UB,Jtb,36,11,0,1);Zfb=gq(fr,Jtb,17,100,15,1);$fb=jq(eq(gr,1),Jtb,17,15,[1,5,25,125,625,3125,15625,78125,390625,1953125,9765625,48828125,vAb,wAb,6103515625,30517578125,152587890625,762939453125,3814697265625,19073486328125,95367431640625,476837158203125,2384185791015625]);_fb=gq(ir,Rxb,17,$fb.length,15,1);agb=jq(eq(gr,1),Jtb,17,15,[1,10,100,evb,10000,100000,1000000,10000000,100000000,Qvb,10000000000,100000000000,1000000000000,10000000000000,100000000000000,1000000000000000,10000000000000000]);bgb=gq(ir,Rxb,17,agb.length,15,1);dgb=gq(UB,Jtb,36,11,0,1);a=0;for(;a<dgb.length;a++){Yfb[a]=new pgb(a,0);dgb[a]=new pgb(0,a);Zfb[a]=48}for(;a<Zfb.length;a++){Zfb[a]=48}for(c=0;c<_fb.length;c++){_fb[c]=sgb($fb[c])}for(b=0;b<bgb.length;b++){bgb[b]=sgb(agb[b])}Uhb();cgb=Rhb}
function Vob(){function e(){this.obj=this.createObject()}
;e.prototype.createObject=function(a){return Object.create(null)};e.prototype.get=function(a){return this.obj[a]};e.prototype.set=function(a,b){this.obj[a]=b};e.prototype[DAb]=function(a){delete this.obj[a]};e.prototype.keys=function(){return Object.getOwnPropertyNames(this.obj)};e.prototype.entries=function(){var b=this.keys();var c=this;var d=0;return {next:function(){if(d>=b.length)return {done:true};var a=b[d++];return {value:[a,c.get(a)],done:false}}}};if(!Tob()){e.prototype.createObject=function(){return {}};e.prototype.get=function(a){return this.obj[':'+a]};e.prototype.set=function(a,b){this.obj[':'+a]=b};e.prototype[DAb]=function(a){delete this.obj[':'+a]};e.prototype.keys=function(){var a=[];for(var b in this.obj){b.charCodeAt(0)==58&&a.push(b.substring(1))}return a}}return e}
function _T(a){this.k=Ri($doc);this.c=Ri($doc);this.i=Ri($doc);this.n=Ri($doc);this.o=Ri($doc);this.p=Ri($doc);this.q=Ri($doc);this.a=Ri($doc);this.g=fj($doc);this.v=Ri($doc);this.u=[];this.f=Ri($doc);this.e=a;this.n.className='scroll-tabs-beginning';this.o.className='scroll-tabs-end';this.p.className='scroll-tabs-left';this.q.className='scroll-tabs-right';this.a.className='add-new-tab';this.i.className='sheet-tabsheet-options';Wg(this.i,this.n);Wg(this.i,this.p);Wg(this.i,this.q);Wg(this.i,this.o);Wg(this.i,this.a);this.c.className='sheet-tabsheet-container';this.v.className='sheet-tabsheet-temp';Wg(this.k,this.v);this.k.className='sheet-tabsheet';Wg(this.k,this.i);Wg(this.k,this.c);this.f.className='sheet-tabsheet-infolabel';Wg(this.k,this.f);oe(this,this.k);dG(this.k,3);cG(this.k,new aU(this));dG(this.g,4736);cG(this.g,new cU(this));this.g.maxLength=31}
function VN(a,b){TJ();var c;EN.call(this);this.e=ej($doc);this.o=b;this.p=new uI;vI(this,this.p);this.i=Ri($doc);this.i.className=Twb;this.a=new d6;ue(this.a,false);re(this.a,'comment-overlay-author');this.g=new d6;ue(this.g,false);re(this.g,'comment-overlay-label');SJ.ef((JF(),JF(),mh(this.Yc))).className='v-spreadsheet-comment-overlay';Be(SJ.ef((null,mh(this.Yc))),'v-spreadsheet-comment-overlay-shadow',true);this.r=a;this.F=false;this.Yc.style[ovb]=Bvb;!!this.t&&(this.t.style[ovb]=Bvb,undefined);this.i.style[ovb]=(wm(),Bvb);this.Yc.style[pvb]='0';this.f=new d6;ue(this.f,false);re(this.f,'comment-overlay-invalidformula');tI(this.p,this.f);tI(this.p,this.a);tI(this.p,this.g);eh(this.e,'comment-overlay-input');this.e.style[nvb]=(Ak(),Pub);Wg(this.Yc,this.e);this.e.rows=4;this.e.style[Rub]=(im(),'200.0px');c=new YN(this,a);Ie(this.a,c,(ln(),ln(),kn));Ie(this.g,c,(null,kn))}
function zhb(a,b){xhb();var c,d,e,f,g,h,i,j,k,l,m,n,o,p;i=dE(a,0)<0;i&&(a=oE(a));if(dE(a,0)==0){switch(b){case 0:return '0';case 1:return '0.0';case 2:return '0.00';case 3:return '0.000';case 4:return '0.0000';case 5:return '0.00000';case 6:return '0.000000';default:n=new Mfb;b<0?(n.a+='0E+',n):(n.a+='0E',n);n.a+=b==Ytb?'2147483648':''+-b;return n.a;}}k=18;l=gq(fr,Jtb,17,k+1,15,1);c=k;p=a;do{j=p;p=fE(p,10);l[--c]=wE(bE(48,tE(j,nE(p,10))))&bub}while(dE(p,0)!=0);e=tE(tE(tE(k,c),b),1);if(b==0){i&&(l[--c]=45);return Dfb(l,c,k-c)}if(b>0&&dE(e,-6)>=0){if(dE(e,0)>=0){f=c+wE(e);for(h=k-1;h>=f;h--){l[h+1]=l[h]}l[++f]=46;i&&(l[--c]=45);return Dfb(l,c,k-c+1)}for(g=2;kE(g,bE(oE(e),1));g++){l[--c]=48}l[--c]=46;l[--c]=48;i&&(l[--c]=45);return Dfb(l,c,k-c)}o=c+1;d=k;m=new Nfb;i&&(m.a+='-',m);if(d-o>=1){Gfb(m,l[c]);m.a+='.';m.a+=Dfb(l,c+1,k-c-1)}else{m.a+=Dfb(l,c,k-c)}m.a+='E';dE(e,0)>0&&(m.a+='+',m);m.a+=''+xE(e);return m.a}
function RX(a,b,c,d,e,f,g){var h,i,j,k,l,m,n,o,p,q,r,s;n=UV(a);while(n.a<n.c.a.length){q=qlb(n);Zq(q,97)&&Qe(q,null)}_g(b);_g(c);h=0;f&&!a.G?(h=5):!f&&!a.ec&&(h=2);if(g>0){if(f){s=a.gc.clientWidth|0;a.Z&&(s+=kV(a))}else{s=a.I.clientHeight|0;a.Z&&(s+=$U(a))}s+=h;for(l=new slb(d);l.a<l.c.a.length;){k=qlb(l);if(f){p=new iO(k.uniqueIndex,a.a);eO(p,a.G)}else{p=new zR(k.uniqueIndex,a.a);eO(p,a.ec)}r=s;for(m=0;m<k.startIndex;m++){f?(r+=K$(a.a,m+1)):(r+=Q$(a.a,m+1)?0:m+1>=a.W.length?bV(a):a.W[m+1-1])}p.f&&(f?(r-=_U(a,k.startIndex)/2|0):(r-=lV(a,k.startIndex)/2|0));p.pf(r,k.level-1);dO(p,k.collapsed);Wg(b,(JF(),p.Yc));Qe(p,a);o=0;for(j=k.startIndex;j<=k.endIndex;j++){f?(o+=K$(a.a,j+1)):(o+=Q$(a.a,j+1)?0:j+1>=a.W.length?bV(a):a.W[j+1-1])}o-=h;p.f?f?(o+=_U(a,k.startIndex)/2):(o+=lV(a,k.startIndex)/2):f?(o+=_U(a,k.endIndex+2)/2):(o+=lV(a,k.endIndex+2)/2);fO(p,o);if(!!e&&e.a.length>k.startIndex){i=p.lf();Wg(c,i.Yc);Qe(i,a)}}}}
function XF(){var a,b,c;b=$doc.compatMode;a=jq(eq(QB,1),_tb,2,6,[avb]);for(c=0;c<a.length;c++){if(jfb(a[c],b)){return}}a.length==1&&jfb(avb,a[0])&&jfb('BackCompat',b)?"GWT no longer supports Quirks Mode (document.compatMode=' BackCompat').<br>Make sure your application's host HTML page has a Standards Mode (document.compatMode=' CSS1Compat') doctype,<br>e.g. by using &lt;!doctype html&gt; at the start of your application's HTML page.<br><br>To continue using this unsupported rendering mode and risk layout problems, suppress this message by adding<br>the following line to your*.gwt.xml module file:<br>&nbsp;&nbsp;&lt;extend-configuration-property name=\"document.compatMode\" value=\""+b+'"/&gt;':"Your *.gwt.xml module configuration prohibits the use of the current document rendering mode (document.compatMode=' "+b+"').<br>Modify your application's host HTML page doctype, or update your custom "+"'document.compatMode' configuration property settings."}
function jH(a,b){var c=(a.__eventBits||0)^b;a.__eventBits=b;if(!c)return;c&1&&(a.onclick=b&1?fH:null);c&2&&(a.ondblclick=b&2?fH:null);c&4&&(a.onmousedown=b&4?fH:null);c&8&&(a.onmouseup=b&8?fH:null);c&16&&(a.onmouseover=b&16?fH:null);c&32&&(a.onmouseout=b&32?fH:null);c&64&&(a.onmousemove=b&64?fH:null);c&128&&(a.onkeydown=b&128?fH:null);c&256&&(a.onkeypress=b&256?fH:null);c&512&&(a.onkeyup=b&512?fH:null);c&1024&&(a.onchange=b&1024?fH:null);c&2048&&(a.onfocus=b&2048?fH:null);c&4096&&(a.onblur=b&4096?fH:null);c&8192&&(a.onlosecapture=b&8192?fH:null);c&Ztb&&(a.onscroll=b&Ztb?fH:null);c&Xvb&&(a.onload=b&Xvb?gH:null);c&aub&&(a.onerror=b&aub?fH:null);c&Zvb&&(a.onmousewheel=b&Zvb?fH:null);c&$vb&&(a.oncontextmenu=b&$vb?fH:null);c&Pvb&&(a.onpaste=b&Pvb?fH:null);c&_vb&&(a.ontouchstart=b&_vb?fH:null);c&awb&&(a.ontouchmove=b&awb?fH:null);c&Rtb&&(a.ontouchend=b&Rtb?fH:null);c&bwb&&(a.ontouchcancel=b&bwb?fH:null);c&cwb&&(a.ongesturestart=b&cwb?fH:null);c&dwb&&(a.ongesturechange=b&dwb?fH:null);c&ewb&&(a.ongestureend=b&ewb?fH:null)}
function TP(a,b,c,d,e){var f,g,h,i,j,k;if(b==c&&d==e){h=Y0(a,d,b);if(!h){h=new SP;h.col1=d;h.col2=e;h.row1=b;h.row2=c}return h}else{g=Z0(a,d,b);if(!!g&&g.col2>=e&&g.row2>=c){return g}}k=false;f=d;while(f<=e){i=Y0(a,f,b);if(i){f=i.col2+1;if(d>i.col1){d=i.col1;k=true}if(e<i.col2){e=i.col2;k=true}if(b>i.row1){b=i.row1;k=true}}else{++f}}b>c&&(b=c);f=b;while(f<=c){i=Y0(a,e,f);if(i){f=i.row2+1;if(e<i.col2){e=i.col2;k=true}if(b>i.row1){b=i.row1;k=true}if(c<i.row2){c=i.row2;k=true}}else{++f}}e<d&&(e=d);f=d;while(f<=e){i=Y0(a,f,c);if(i){f=i.col2+1;if(d>i.col1){d=i.col1;k=true}if(e<i.col2){e=i.col2;k=true}if(c<i.row2){c=i.row2;k=true}}else{++f}}c<b&&(c=b);f=b;while(f<=c){i=Y0(a,d,f);if(i){f=i.row2+1;if(d>i.col1){d=i.col1;k=true}if(b>i.row1){b=i.row1;k=true}if(c<i.row2){c=i.row2;k=true}}else{++f}}d>e&&(d=e);if(k){return TP(a,b,c,d,e)}else if(b==c&&d==e){h=Y0(a,d,b);if(!h){h=new SP;h.col1=d;h.col2=e;h.row1=b;h.row2=c}return h}else{g=Z0(a,d,b);if(!!g&&g.col2>=e&&g.row2>=c){return g}}j=new SP;j.col1=d;j.col2=e;j.row1=b;j.row2=c;return j}
function nT(a,b){var c,d,e,f,g,h;e=zj(b.a);d=a.b.a;if(a.b._){switch(e){case 8:case 46:wV(a.b);oP(a.a);nP(a.a,true);KO(a.a);HO(a.a);break;case 27:y$(d,d.b,true);$O(d.u);SU(d.V);NO(a.a);break;case 9:a_(d,(h=gL(a.b.sb),h==null?'':h),Aj(b.a));NO(a.a);!!b.a&&Dj(b.a);break;case 38:if(RO(a.a)){$$(d,(g=gL(a.b.sb),g==null?'':g),true);!!b.a&&Dj(b.a)}else if(TO(a.a)){UO(a.a,Aj(b.a),true,false,false);!!b.a&&Dj(b.a)}break;case 40:if(RO(a.a)){$$(d,(g=gL(a.b.sb),g==null?'':g),false);!!b.a&&Dj(b.a)}else if(TO(a.a)){UO(a.a,Aj(b.a),false,false,true);!!b.a&&Dj(b.a)}break;case 37:if(RO(a.a)){a_(d,(g=gL(a.b.sb),g==null?'':g),true);!!b.a&&Dj(b.a)}else if(TO(a.a)){UO(a.a,Aj(b.a),false,false,false);!!b.a&&Dj(b.a)}else if(a.a.v){nP(a.a,true);eL(a.b.sb)==0&&!!b.a&&Dj(b.a)}break;case 39:if(RO(a.a)){a_(d,(g=gL(a.b.sb),g==null?'':g),false);!!b.a&&Dj(b.a)}else if(TO(a.a)){UO(a.a,Aj(b.a),false,true,false);!!b.a&&Dj(b.a)}else if(a.a.v){nP(a.a,true);c=eL(a.b.sb);f=(g=gL(a.b.sb),g==null?'':g).length;c==f&&!!b.a&&Dj(b.a)}}}else{y_(d,b.a,wT(wj(b.a)))}Ej(b.a)}
function X$(a,b,c,d,e,f){var g,h,i,j,k,l,m,n,o;F$(a);if(b==0||c==0){return}j=false;f||(j=c!=a.V.sc||b!=a.V.rc);if(d){m=a.V.rc;n=a.V.sc;g=m>b?b:m;h=m>b?m:b;k=n>c?c:n;l=n>c?n:c;o=TP(a.I,k,l,g,h);if(a.u.f);else if(bS(a.V.yc)){eY(a.V,o.col1,o.col2,o.row1,o.row2);cY(a.V,o.col1,o.col2,o.row1,o.row2,true)}else{cY(a.V,o.col1,o.col2,o.row1,o.row2,false)}if(a.u.f){cP(a.u,a.X,a.Y,b,c,false)}else if(f){bS(a.V.yc)?uab(a.W,o.row1,o.col1,o.row2,o.col2):xab(a.W,o.row1,o.col1,o.row2,o.col2);qb(a.s,200)}}else if(e){if(b==a.V.rc&&c==a.V.sc){return}if(a.u.f){cP(a.u,b,c,b,c,true)}else{a.V.C&&(a.V.C=false,undefined);bS(a.V.yc)&&mX(a.V,false);CX(a.V,b,c);PR(a.Q);j&&O0(a,b,c,null);if(f){sab(a.W,c,b);qb(a.s,200)}}}else{i=Z0(a.I,b,c);if(a.u.f){a.X=b;a.Y=c;cP(a.u,b,c,b,c,false)}else{a.V.C||(a.V.C=true,undefined);if(!bS(a.V.yc)){mX(a.V,true);DU(a.V)}lX(a.V,b,c);if(i){eY(a.V,i.col1,i.col2,i.row1,i.row2);cY(a.V,i.col1,i.col2,i.row1,i.row2,true);TR(a.Q,i.col1);UR(a.Q,i.row1)}else{eY(a.V,b,b,c,c);cY(a.V,b,b,c,c,true)}j&&O0(a,b,c,null);if(f){PR(a.Q);vab(a.W,c,b,true);qb(a.s,200)}}}}
function BV(a){oe(a,a.Gc);Wg(a.Gc,a.zc);eh(a.Gc,'v-spreadsheet');a.zc.className='bottom-right-pane';eh(a.zc,wxb);a.zc.tabIndex=3;a.Qc.className='top-right-pane';eh(a.Qc,wxb);Wg(a.Gc,a.Qc);a.c.className='bottom-left-pane';eh(a.c,wxb);Wg(a.Gc,a.c);a.Oc.className='top-left-pane';eh(a.Oc,wxb);Wg(a.Gc,a.Oc);a.I.className='col-group-pane';Wg(a.Gc,a.I);a.gc.className='row-group-pane';Wg(a.Gc,a.gc);a.F.className='col-group-freeze-pane';Wg(a.Gc,a.F);a.dc.className='row-group-freeze-pane';Wg(a.Gc,a.dc);a.hc.className='row-group-summary';Wg(a.Gc,a.hc);a.J.className='col-group-summary';Wg(a.Gc,a.J);a.D.className='col-group-border';Wg(a.Gc,a.D);a.cc.className='row-group-border';Wg(a.Gc,a.cc);a.kb.className='grouping-corner';Wg(a.Gc,a.kb);a.Ub.className=Wxb;Wg(a.Gc,a.Ub);a.Vb.className=Wxb;Wg(a.zc,a.Vb);a.N.className='corner';Wg(a.Gc,a.N);a.fb.className='floater';a.sb=new XP(a);ve(a.sb,'0');iL(a.sb,'x');ie(a.sb).id='cellinput';JF();Wg(a.zc,TF(ie(a.sb)));LH(a,a.sb);a.Mb.style[Rub]=(im(),'1.0in');a.Mb.style[gvb]=(Cl(),ivb);a.Mb.style[ovb]=(wm(),Bvb);a.Mb.style['padding']=swb;Wg(a.Gc,a.Mb);a.hb.style[ovb]=Bvb;yh(a.hb,Xxb)}
function jgb(a,b){var c,d,e,f,g,h,i,j;c=0;g=0;f=b.length;h=null;j=new Nfb;if(g<f&&(vtb(g,b.length),b.charCodeAt(g)==43)){++g;++c;if(g<f&&(vtb(g,b.length),b.charCodeAt(g)==43||(vtb(g,b.length),b.charCodeAt(g)==45))){throw aE(new cfb(Wtb+b+'"'))}}while(g<f&&(vtb(g,b.length),b.charCodeAt(g)!=46)&&(vtb(g,b.length),b.charCodeAt(g)!=101)&&(vtb(g,b.length),b.charCodeAt(g)!=69)){++g}j.a+=''+b.substr(c,g-c);if(g<f&&(vtb(g,b.length),b.charCodeAt(g)==46)){++g;c=g;while(g<f&&(vtb(g,b.length),b.charCodeAt(g)!=101)&&(vtb(g,b.length),b.charCodeAt(g)!=69)){++g}a.e=g-c;j.a+=''+b.substr(c,g-c)}else{a.e=0}if(g<f&&(vtb(g,b.length),b.charCodeAt(g)==101||(vtb(g,b.length),b.charCodeAt(g)==69))){++g;c=g;if(g<f&&(vtb(g,b.length),b.charCodeAt(g)==43)){++g;g<f&&(vtb(g,b.length),b.charCodeAt(g)!=45)&&++c}h=b.substr(c,f-c);a.e=a.e-peb(h);if(a.e!=er(a.e)){throw aE(new cfb('Scale out of range.'))}}i=j.a;if(i.length<16){a.f=(egb==null&&(egb=new RegExp('^[+-]?\\d*$','i')),egb.test(i)?parseInt(i,10):NaN);if(isNaN(a.f)){throw aE(new cfb(Wtb+b+'"'))}a.a=sgb(a.f)}else{lgb(a,new ehb(i))}a.d=j.a.length;for(e=0;e<j.a.length;++e){d=hfb(j.a,e);if(d!=45&&d!=48){break}--a.d}a.d==0&&(a.d=1)}
function P7(a,b,c){var d,e,f,g,h,i;for(e=jq(eq(QB,1),_tb,2,6,[Uzb,Vzb,'rows','cols',Wzb,Xzb,Yzb,Zzb,$zb,_zb,'defRowH','defColW','rowH','colW',aAb,bAb,cAb,dAb,eAb,fAb,gAb,qyb,ryb,hAb,iAb,jAb,kAb,lAb,mAb,syb,tyb,nAb,oAb,pAb,qAb,rAb,Qub,Rub,Xyb,Yyb,Tyb,Gzb,'id',vzb,ezb,Uyb,Hzb,czb,oyb]),f=0,g=e.length;f<g;++f){d=e[f];if(c.b||S2(c,d)){i=(!b.D&&(b.D=new S0),b.D);h=dQ(a.e);jfb(Uzb,d)&&o0(i,h.P);jfb(Vzb,d)&&S_(i,h.k);jfb('rows',d)&&u0(i,h.V);jfb('cols',d)&&R_(i,h.j);jfb(Wzb,d)&&N_(i,h.e);jfb(Xzb,d)&&p0(i,h.Q);jfb(Yzb,d)&&P_(i,h.g);jfb(Zzb,d)&&r0(i,h.S);jfb($zb,d)&&O_(i,h.f);jfb(_zb,d)&&q0(i,h.R);jfb('defRowH',d)&&X_(i,h.r);jfb('defColW',d)&&W_(i,h.q);jfb('rowH',d)&&s0(i,h.T);jfb('colW',d)&&Q_(i,h.i);jfb(aAb,d)&&L_(i,h.d);jfb(bAb,d)&&t0(i,h.U);jfb(cAb,d)&&T_(i,h.n);jfb(dAb,d)&&l0(i,h.J);jfb(eAb,d)&&m0(i,h.K);jfb(fAb,d)&&y0(i,h.Z);jfb(gAb,d)&&U_(i,h.p);jfb(qyb,d)&&__(i,h.v);jfb(ryb,d)&&a0(i,h.w);jfb(hAb,d)&&C0(i,h._);jfb(iAb,d)&&b0(i,h.A);jfb(jAb,d)&&F0(i,h.db);jfb(kAb,d)&&e0(i,h.C);jfb(lAb,d)&&Y_(i,h.s);jfb(mAb,d)&&Z_(i,h.t);jfb(syb,d)&&D0(i,h.ab);jfb(tyb,d)&&c0(i,h.B);jfb(nAb,d)&&g0(i,h.D);jfb(oAb,d)&&i0(i,h.G);jfb(pAb,d)&&j0(i,h.H);jfb(qAb,d)&&k0(i,h.I);jfb(rAb,d)&&n0(i,h.M);jfb(Qub,d)&&$_(i,h.kb);jfb(Rub,d)&&E0(i,h.ob);jfb('id',d)&&f0(i,h.lb);jfb(oyb,d)&&z0(i,h.$)}}}
function Ahb(a,b){xhb();var c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,A,B,C,D,F,G,H;B=a.e;o=a.d;e=a.a;if(B==0){switch(b){case 0:return '0';case 1:return '0.0';case 2:return '0.00';case 3:return '0.000';case 4:return '0.0000';case 5:return '0.00000';case 6:return '0.000000';default:w=new Mfb;b<0?(w.a+='0E+',w):(w.a+='0E',w);w.a+=-b;return w.a;}}t=o*10+1+7;u=gq(fr,Jtb,17,t+1,15,1);c=t;if(o==1){h=e[0];if(h<0){H=cE(h,yAb);do{p=H;H=fE(H,10);u[--c]=48+wE(tE(p,nE(H,10)))&bub}while(dE(H,0)!=0)}else{H=h;do{p=H;H=H/10|0;u[--c]=48+(p-H*10)&bub}while(H!=0)}}else{D=gq(ir,Rxb,17,o,15,1);G=o;Sfb(e,0,D,0,G);I:while(true){A=0;for(j=G-1;j>=0;j--){F=bE(qE(A,32),cE(D[j],yAb));r=yhb(F);D[j]=wE(r);A=wE(rE(r,32))}s=wE(A);q=c;do{u[--c]=48+s%10&bub}while((s=s/10|0)!=0&&c!=0);d=9-q+c;for(i=0;i<d&&c>0;i++){u[--c]=48}l=G-1;for(;D[l]==0;l--){if(l==0){break I}}G=l+1}while(u[c]==48){++c}}n=B<0;g=t-c-b-1;if(b==0){n&&(u[--c]=45);return Dfb(u,c,t-c)}if(b>0&&g>=-6){if(g>=0){k=c+g;for(m=t-1;m>=k;m--){u[m+1]=u[m]}u[++k]=46;n&&(u[--c]=45);return Dfb(u,c,t-c+1)}for(l=2;l<-g+1;l++){u[--c]=48}u[--c]=46;u[--c]=48;n&&(u[--c]=45);return Dfb(u,c,t-c)}C=c+1;f=t;v=new Nfb;n&&(v.a+='-',v);if(f-C>=1){Gfb(v,u[c]);v.a+='.';v.a+=Dfb(u,c+1,t-c-1)}else{v.a+=Dfb(u,c,t-c)}v.a+='E';g>0&&(v.a+='+',v);v.a+=''+g;return v.a}
function IR(a,b,c,d,e){var f,g,h,i,j,k,l,m;if(b==c&&d==e){h=L$(a.d,d,b);if(!h){h=new SP;h.col1=d;h.col2=e;h.row1=b;h.row2=c}return h}else{g=M$(a.d,d,b);if(!!g&&g.col2>=e&&g.row2>=c){return g}}k=a.c.rc;l=a.c.sc;if(k<d||k>e||l<b||l>c){return L$(a.d,k,a.c.sc)}m=false;f=d;while(f<=e){i=L$(a.d,f,b);if(i){f=i.col2+1;if(b>i.row1){m=true;if(b<c){i.row2>c?(b=i.row2+1):(b=c);f=d}else{if(k<i.col1){e=i.col1-1}else if(k>i.col2){d=i.col2+1}else{d=i.col1;e=i.col2;break}}}}else{++f}}b>c&&(b=c);f=b;while(f<=c){i=L$(a.d,e,f);if(i){f=i.row2+1;if(e<i.col2){m=true;if(e>d){i.col1>d?(e=i.col1-1):(e=d);f=b}else{if(l<i.row1){c=i.row1-1}else if(l>i.row2){b=i.row2+1}else{b=i.row1;c=i.row2;break}}}}else{++f}}e<d&&(e=d);f=d;while(f<=e){i=L$(a.d,f,c);if(i){f=i.col2+1;if(c<i.row2){m=true;if(c>b){b<i.row1?(c=i.row1-1):(c=b);f=d}else{if(k<i.col1){e=i.col1-1}else if(k>i.col2){d=i.col2+1}else{e=i.col1;d=i.col2;break}}}}else{++f}}c<b&&(c=b);f=b;while(f<=c){i=L$(a.d,d,f);if(i){f=i.row2+1;if(d>i.col1){m=true;if(d<e){e>i.col2?(d=i.col2+1):(d=e);f=b}else{if(l<i.row1){c=i.row1-1}else if(l>i.row2){b=i.row2+1}else{b=i.row1;c=i.row2;break}}}}else{++f}}d>e&&(d=e);if(m){return IR(a,b,c,d,e)}else if(b==c&&d==e){h=L$(a.d,d,b);if(!h){h=new SP;h.col1=d;h.col2=e;h.row1=b;h.row2=c}return h}else{g=M$(a.d,d,b);if(!!g&&g.col2>=e&&g.row2>=c){return g}}j=new SP;j.col1=d;j.col2=e;j.row1=b;j.row2=c;return j}
function rW(b,c){var d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,A,B,C,D,F,G;gY(b);D=b.Mc+((b.zc.scrollTop||0)|0);C=qh(b.zc);G=D-b.Pb;l=C-b.Ob;try{if(b.zb>b.a.O){b.zb=b.a.O;while(b.zb-b.db+1<b.kc.a.length){A=Tkb(b.kc,b.kc.a.length-1);for(g=new slb(A);g.a<g.c.a.length;){f=qlb(g);bh(f.d)}bh(Tkb(b.ic,b.ic.a.length-1))}}if(b.xb>b.a.i){b.xb=b.a.i;for(B=new slb(b.kc);B.a<B.c.a.length;){A=qlb(B);while(b.xb-b.bb+1<A.a.length){bh(Tkb(A,A.a.length-1).d)}}while(b.xb-b.bb+1<b.K.a.length){bh(Tkb(b.K,b.K.a.length-1))}}r=1;for(n=1;n<b.db;n++){r+=Q$(b.a,n)?0:n>=b.W.length?bV(b):b.W[n-1];n==b.Tc&&(b.Mc=r)}t=r;for(o=b.db;o<=b.zb;o++){t+=Q$(b.a,o)?0:o>=b.W.length?bV(b):b.W[o-1]}d=b.Mc+D+b.oc+b.a.L;F=r-b.eb;e=t-b.Ab;b.eb=r;b.Ab=t;q=0;for(p=1;p<b.bb;p++){q+=K$(b.a,p);b.ob==p&&(b.Bb=q)}s=q;for(m=b.bb;m<=b.xb;m++){s+=K$(b.a,m)}v=b.Bb+C+b.pc+b.a.j;w=s-b.yb;b.cb=q;b.yb=s;uV(b,C);MX(b,0,-1);if(w<0||l>0||b.xb<b.a.i&&b.yb<v){vV(b,C);MX(b,0,1)}if(F>0||G<0){AV(b,D);MX(b,-1,0)}if(e!=0||G>0||b.zb<b.a.O&&b.Ab<d){zV(b,D);MX(b,1,0)}HW(b);b.Ob=C;b.Pb=D;c&&g6(b.Rb);for(i=(u=(new mkb(b.b)).a.$f().Oe(),new rkb(u));i.a.Ye();){h=(k=i.a.Ze(),k.hg());P$(b.a,h.b)||Q$(b.a,h.d)?(fN(h,false),bh(h.i)):NN(h)}YV(b);eY(b,b.yc.e,b.yc.f,b.yc.K,b.yc.L);NX(b);bY(b);ZX(b,true)}catch(a){a=_D(a);if(Zq(a,21)){j=a;Nrb(b.U,'SheetWidget:relayoutSheet: '+zf(j,j.Hd())+' while relayouting spreadsheet');JW(b,C,D);HW(b);u_(b.a,b.db,b.zb,b.bb,b.xb);DW(b);IW(b);NX(b);bY(b);CW(b);nW(b);YX(b)}else throw aE(a)}}
function y_(a,b,c){var d,e,f,g;if((Gh(),Fh).Td(b)==0&&(b.keyCode|0)!=32||Fh.Td(b)==13){switch(b.keyCode|0){case 8:case 46:A$(a);if(!a.e){rab(a.W.t,jq(eq(KB,1),Jtb,1,5,[]));bP(a.u,'')}break;case 40:b.shiftKey?KR(a.Q,true):LR(a.Q,true);break;case 37:b.shiftKey?JR(a.Q,false):MR(a.Q,true);break;case 9:b.shiftKey?MR(a.Q,Rkb(a.v,Neb(a.V.rc),0)!=-1||Rkb(a.w,Neb(a.V.sc),0)!=-1):NR(a.Q,Rkb(a.v,Neb(a.V.rc),0)!=-1||Rkb(a.w,Neb(a.V.sc),0)!=-1);break;case 39:b.shiftKey?JR(a.Q,true):NR(a.Q,true);break;case 38:b.shiftKey?KR(a.Q,false):OR(a.Q,true);break;case 113:case 13:if(13==(b.keyCode|0)){if(Rkb(a.v,Neb(a.V.rc),0)!=-1||Rkb(a.w,Neb(a.V.sc),0)!=-1){LR(a.Q,true);break}else{if(a.V.yc.e!=a.V.yc.f||a.V.yc.K!=a.V.yc.L){b.shiftKey?OR(a.Q,false):LR(a.Q,false);break}}}A$(a);if(!RV(a.V)&&!a.B&&!a.e&&!a.o){a.b=pV(a.V);GO(a.u);a.t=false;a.B=true;uX(a.V,true,(f=gL(a.u.j),f==null?'':f));a.u.u=true;hP(a.u)}else a.o&&RU(a.V);}}else{if(!(Rkb(a.v,Neb(a.V.rc),0)!=-1||Rkb(a.w,Neb(a.V.sc),0)!=-1)){A$(a);if(!RV(a.V)&&!a.B&&!a.e&&!a.o){a.B=true;a.b=pV(a.V);hP(a.u);if(ifb(a.b,'%')||SV(a.V)){(g=new vob,Vib(g.a,'0',g),Vib(g.a,'1',g),Vib(g.a,'2',g),Vib(g.a,'3',g),Vib(g.a,'4',g),Vib(g.a,'5',g),Vib(g.a,'6',g),Vib(g.a,'7',g),Vib(g.a,'8',g),Vib(g.a,'9',g),Vib(g.a,'-',g),Vib(g.a,'+',g),Tib(g.a,c))&&(c=c+'%');uX(a.V,true,c)}else{uX(a.V,true,c);GO(a.u)}bP(a.u,c)}else if(!!a.p&&d$(a.p,oV(a.V))){e=c$(a.p,oV(a.V));if(Zq(e,119)){d=e.a;d.focus();(F1(),!E1&&(E1=new O1),F1(),E1).a.g&&(d[xwb]=c,undefined)}}}}}
function KR(a,b){var c,d,e,f,g,h,i,j,k,l,m,n,o;o=a.c.yc.K;h=o;f=a.c.yc.e;d=a.c.yc.L;g=d;j=a.c.yc.f;m=a.c.sc;l=a.c.rc;i=M$(a.d,l,m);c=false;if(a.c.C){!!i&&(b&&i.row1!=o||!b&&i.row2==d)&&(m=i.row2);n=null;if(m==o){if(b&&d+1<=a.d.O){++d;while(!!a.d.w&&Rkb(a.d.w,Neb(d),0)!=-1&&d<a.d.O){++d}n=TP(a.d.I,o,d,f,j)}else if(!b){if(o!=d){--d;while(!!a.d.w&&Rkb(a.d.w,Neb(d),0)!=-1&&d>o){--d}n=IR(a,o,d,f,j)}else if(o-1>0){c=true;--o;while(!!a.d.w&&Rkb(a.d.w,Neb(o),0)!=-1&&o>1){--o}n=TP(a.d.I,o,d,f,j)}}}else if(m==d){if(b){if(o!=d){c=true;++o;while(!!a.d.w&&Rkb(a.d.w,Neb(o),0)!=-1&&o<d){++o}n=IR(a,o,d,f,j)}else if(d+1<=a.d.O){++d;while(!!a.d.w&&Rkb(a.d.w,Neb(d),0)!=-1&&d<a.d.O){++d}n=TP(a.d.I,o,d,f,j)}}else if(!b&&o-1>0){c=true;--o;while(!!a.d.w&&Rkb(a.d.w,Neb(o),0)!=-1&&o>1){--o}n=TP(a.d.I,o,d,f,j)}}else{if(b){if(d+1<=a.d.O){++d;while(!!a.d.w&&Rkb(a.d.w,Neb(d),0)!=-1&&d<a.d.O){++d}n=TP(a.d.I,o,d,f,j)}}else{c=true;if(o-1>0){--o;while(!!a.d.w&&Rkb(a.d.w,Neb(o),0)!=-1&&o>1){--o}n=TP(a.d.I,o,d,f,j)}}}if(!n){return}eY(a.c,n.col1,n.col2,n.row1,n.row2);zW(a.c,n.col1,n.col2,n.row1,n.row2);AW(a.c,n.row1,n.row2,n.col1,n.col2);PW(a.c,n.row1,n.row2,c)}else{if(i){k=i.row2;e=i.col2}else{k=m;e=l}if(b){++k;while(!!a.d.w&&Rkb(a.d.w,Neb(k),0)!=-1&&k<a.d.O){++k}}else{--m;while(!!a.d.w&&Rkb(a.d.w,Neb(m),0)!=-1&&m>1){--m}}if(m>0&&k<=a.d.O){n=TP(a.d.I,m,k,l,e);if(n){a.c.C=true;mX(a.c,true);DU(a.c);eY(a.c,n.col1,n.col2,n.row1,n.row2);cY(a.c,n.col1,n.col2,n.row1,n.row2,true)}}RW(a.c)}if(f!=a.c.yc.e||j!=a.c.yc.f||h!=a.c.yc.K||g!=a.c.yc.L){uab(a.d.W,a.c.yc.K,a.c.yc.e,a.c.yc.L,a.c.yc.f);qb(a.d.s,200)}}
function JR(a,b){var c,d,e,f,g,h,i,j,k,l,m,n,o;o=a.c.yc.K;f=a.c.yc.e;g=f;j=a.c.yc.f;h=j;d=a.c.yc.L;l=a.c.rc;m=a.c.sc;i=Z0(a.d.I,l,m);c=false;if(a.c.C){!!i&&(b&&i.col1!=f||!b&&i.col2==j)&&(l=i.col2);n=null;if(l==f){if(b&&j+1<=a.d.i){++j;while(!!a.d.v&&Rkb(a.d.v,Neb(j),0)!=-1&&j<a.d.i){++j}n=TP(a.d.I,o,d,f,j)}else if(!b){if(j!=f){--j;while(!!a.d.v&&Rkb(a.d.v,Neb(j),0)!=-1&&j>f){--j}n=IR(a,o,d,f,j)}else if(f-1>0){c=true;--f;while(!!a.d.v&&Rkb(a.d.v,Neb(f),0)!=-1&&f>1){--f}n=TP(a.d.I,o,d,f,j)}}}else if(l==j){if(b){if(j!=f){c=true;++f;while(!!a.d.v&&Rkb(a.d.v,Neb(f),0)!=-1&&f<j){++f}n=IR(a,o,d,f,j)}else if(j+1<=a.d.i){++j;while(!!a.d.v&&Rkb(a.d.v,Neb(j),0)!=-1&&j<a.d.i){++j}n=TP(a.d.I,o,d,f,j)}}else if(!b&&f-1>0){c=true;--f;while(!!a.d.v&&Rkb(a.d.v,Neb(f),0)!=-1&&f>1){--f}n=TP(a.d.I,o,d,f,j)}}else{if(b){if(j+1<=a.d.i){++j;while(!!a.d.v&&Rkb(a.d.v,Neb(j),0)!=-1&&j<a.d.i){++j}n=TP(a.d.I,o,d,f,j)}}else{c=true;if(f-1>0){--f;while(!!a.d.v&&Rkb(a.d.v,Neb(f),0)!=-1&&f>1){--f}n=TP(a.d.I,o,d,f,j)}}}if(!n){return}eY(a.c,n.col1,n.col2,n.row1,n.row2);zW(a.c,n.col1,n.col2,n.row1,n.row2);AW(a.c,n.row1,n.row2,n.col1,n.col2);OW(a.c,n.col1,n.col2,c)}else{if(i){k=i.row2;e=i.col2}else{k=m;e=l}if(b){++e;while(!!a.d.v&&Rkb(a.d.v,Neb(e),0)!=-1&&e<a.d.i){++e}}else{--l;while(!!a.d.v&&Rkb(a.d.v,Neb(l),0)!=-1&&l>1){--l}}if(l>0&&e<a.d.i){n=TP(a.d.I,m,k,l,e);if(n){a.c.C=true;mX(a.c,true);DU(a.c);eY(a.c,n.col1,n.col2,n.row1,n.row2);cY(a.c,n.col1,n.col2,n.row1,n.row2,true)}}RW(a.c)}if(g!=a.c.yc.e||h!=a.c.yc.f||o!=a.c.yc.K||d!=a.c.yc.L){uab(a.d.W,a.c.yc.K,a.c.yc.e,a.c.yc.L,a.c.yc.f);qb(a.d.s,200)}}
function MW(a,b,c,d,e,f,g,h,i){var j,k,l,m,n,o,p,q,r,s,t,u,v,w,A;o=Qkb((otb(0,h.a.length),h.a[0]),0).k;s=Qkb(Qkb(h,h.a.length-1),0).k;n=Qkb((otb(0,h.a.length),h.a[0]),0).c;r=Qkb((otb(0,h.a.length),h.a[0]),(otb(0,h.a.length),h.a[0]).a.length-1).c;A=new Wkb;for(q=new slb(h);q.a<q.c.a.length;){u=qlb(q);t=(otb(0,u.a.length),u.a[0]).k;if(b>0){if(t<d){if(s<e){t=++s;rlb(q);A.a[A.a.length]=u}else{for(l=new slb(u);l.a<l.c.a.length;){k=qlb(l);bh(k.d)}rlb(q);continue}}}else if(b<0){if(t>e){if(o>d){t=--o;rlb(q);A.a[A.a.length]=u}else{for(l=new slb(u);l.a<l.c.a.length;){k=qlb(l);bh(k.d)}rlb(q);continue}}}n=(otb(0,u.a.length),u.a[0]).c;r=Qkb(u,u.a.length-1).c;w=new Wkb;for(m=new slb(u);m.a<m.c.a.length;){k=qlb(m);j=k.c;if(c>0){if(j<f){if(r<g){j=++r;rlb(m);w.a[w.a.length]=k}else{bh(k.d);rlb(m);continue}}}else if(c<0){if(j>g){if(n>f){j=--n;rlb(m);w.a[w.a.length]=k}else{bh(k.d);rlb(m);continue}}}(j!=k.c||t!=k.k)&&QM(k,j,t,Sib(a.e,Ewb+j+Fwb+t))}if(c>0){for(l=new slb(w);l.a<l.c.a.length;){k=qlb(l);u.a[u.a.length]=k}while(r<g){++r;k=new VM(a,r,t,Sib(a.e,Ewb+r+Fwb+t));Wg(i,k.d);u.a[u.a.length]=k}}else if(c<0){for(l=new slb(w);l.a<l.c.a.length;){k=qlb(l);rtb(0,u.a.length);htb(u.a,0,k)}while(n>f){--n;k=new VM(a,n,t,Sib(a.e,Ewb+n+Fwb+t));Wg(i,k.d);rtb(0,u.a.length);htb(u.a,0,k)}}}if(b>0){for(v=new slb(A);v.a<v.c.a.length;){u=qlb(v);h.a[h.a.length]=u}}else{for(v=new slb(A);v.a<v.c.a.length;){u=qlb(v);rtb(0,h.a.length);htb(h.a,0,u)}}if(b>0){while(s<e){u=new Xkb(g-f+1);++s;for(p=f;p<=g;p++){k=new VM(a,p,s,Sib(a.e,Ewb+p+Fwb+s));u.a[u.a.length]=k;Wg(i,k.d)}h.a[h.a.length]=u}}else if(b<0){while(o>d){u=new Wkb;--o;for(p=f;p<=g;p++){k=new VM(a,p,o,Sib(a.e,Ewb+p+Fwb+o));u.a[u.a.length]=k;Wg(i,k.d)}rtb(0,h.a.length);htb(h.a,0,u)}}ZX(a,false)}
function _V(b,c){var d,e,f,g,h,i,j,k,l,m,n,o;k=kY(c);d=(Gh(),k).getAttribute($ub)||'';if(b.o&&d.indexOf('comment-overlay')==-1){b.o=false;QN(b.Q,false);if(M(b.Q,b.q)){KN(b.q);b.j=null;b.k=-1;b.n=-1}}if(d.indexOf(wxb)!=-1||jfb(k.tagName,'input')||jfb(d,'floater')){return}o=b.Sc&&(jfb('s-top',d)||jfb(rxb,d)||jfb(txb,d)||jfb(sxb,d));if(OV(b,c)||o){jfb(mvb,c.type)?c_(b.a,c,b.rc,b.sc):b.xc&&BX(b,c)}else if(d.indexOf(xxb)!=-1){jfb(d,Cwb)||jfb(d,Dwb)?AT(b.wb,kh(Kh(k))):AT(b.wb,d);m=b.wb.a;n=b.wb.b;try{j=bxb.length;if(!jfb(d.substr(d.length-j,j),bxb)){e=(h=oj($doc),w2(),c.type.indexOf(kxb)!=-1?Rm(c.changedTouches[0])+h:ai(c.clientX||0)+h);f=(i=pj($doc),c.type.indexOf(kxb)!=-1?Sm(c.changedTouches[0])+i:ai(c.clientY||0)+i);l=iV(b,e,f,WU(b,m,n));k=l.d;m=l.c;n=l.k}}catch(a){a=_D(a);if(Zq(a,82)){Nrb(b.U,'SheetWidget:onSheetMouseDown - JSE while trying to find real event target, className:'+d)}else if(Zq(a,33)){Nrb(b.U,'SheetWidget:onSheetMouseDown - IOOBE while trying to find real event target, className:'+d)}else throw aE(a)}c.stopPropagation();Fh.Xd(c);if(jfb(mvb,c.type)){aG(b.zc);c_(b.a,c,m,n)}else{b.zc.focus();b._&&!$g(ie(b.sb),k)&&Z$(b.a,(g=gL(b.sb),g==null?'':g));if(!!c.ctrlKey||!!c.metaKey||!!c.shiftKey){X$(b.a,m,n,(Fh.be(k),!!c.shiftKey),!!c.metaKey||!!c.ctrlKey,true);b.Kc=-1;b.Lc=-1}else{if(!!b.s&&Tib(b.s,Ewb+b.wb.a+Fwb+b.wb.b)){o_(b.a,m,n)}else{X$(b.a,m,n,(Fh.be(k),!!c.shiftKey),!!c.metaKey||!!c.ctrlKey,false);b.xc=true;b.Kc=m;b.Lc=n;b.Ic=m<=b.ob&&n<=b.Tc;b.Jc=m>b.ob&&m<=b.xb&&n<=b.Tc;b.Hc=n>b.Tc&&n<=b.zb&&m<=b.ob;b.O=!b.Ic&&!b.Jc;b.P=!b.Ic&&!b.Hc;b.A=(h=oj($doc),w2(),c.type.indexOf(kxb)!=-1?Rm(c.changedTouches[0])+h:ai(c.clientX||0)+h);b.B=(i=pj($doc),c.type.indexOf(kxb)!=-1?Sm(c.changedTouches[0])+i:ai(c.clientY||0)+i);bG(b.zc)}}}}}
function Qd(){Qd=HE;Ic=new Hb;Hc=new Gb;Jc=new Ib;Kc=new Ob;Lc=new Pb;Mc=new Qb;Nc=new Rb;Oc=new Sb;Pc=new Tb;Qc=new Ub;Rc=new Vb;Sc=new Wb;Tc=new Xb;Uc=new Yb;Vc=new Zb;Wc=new $b;Yc=new ac;Xc=new _b;Zc=new bc;$c=new cc;_c=new fc;ad=new gc;cd=new ic;dd=new jc;bd=new hc;ed=new kc;fd=new lc;gd=new mc;hd=new nc;kd=new qc;md=new sc;nd=new tc;ld=new rc;jd=new oc;od=new uc;pd=new vc;qd=new wc;rd=new xc;sd=new Ac;ud=new Fc;td=new Ec;vd=new Gc;yd=new Sd;zd=new Td;xd=new Rd;Ad=new Ud;Bd=new Vd;Cd=new Wd;Dd=new Xd;Ed=new Yd;Fd=new Zd;Hd=new _d;Id=new ae;Gd=new $d;Jd=new be;Kd=new ce;Ld=new de;Md=new ee;Od=new ge;Pd=new he;Nd=new fe;wd=new qob;Vib(wd,'region',vd);Vib(wd,'alert',Hc);Vib(wd,'dialog',Tc);Vib(wd,yub,Ic);Vib(wd,zub,Jc);Vib(wd,'document',Vc);Vib(wd,'article',Kc);Vib(wd,'banner',Lc);Vib(wd,Aub,Mc);Vib(wd,'checkbox',Nc);Vib(wd,'gridcell',Yc);Vib(wd,Bub,Oc);Vib(wd,'group',Zc);Vib(wd,'combobox',Pc);Vib(wd,Cub,Qc);Vib(wd,Dub,Rc);Vib(wd,Eub,Sc);Vib(wd,'list',bd);Vib(wd,'directory',Uc);Vib(wd,'form',Wc);Vib(wd,'grid',Xc);Vib(wd,'heading',$c);Vib(wd,'img',_c);Vib(wd,Fub,ad);Vib(wd,'listbox',cd);Vib(wd,'listitem',dd);Vib(wd,'log',ed);Vib(wd,'main',fd);Vib(wd,'marquee',gd);Vib(wd,'math',hd);Vib(wd,'menu',jd);Vib(wd,'menubar',kd);Vib(wd,'menuitem',ld);Vib(wd,Gub,md);Vib(wd,Jub,qd);Vib(wd,'radio',td);Vib(wd,Hub,nd);Vib(wd,Iub,od);Vib(wd,'note',pd);Vib(wd,Kub,rd);Vib(wd,Lub,sd);Vib(wd,Mub,ud);Vib(wd,'row',xd);Vib(wd,'rowgroup',yd);Vib(wd,'rowheader',zd);Vib(wd,'search',Bd);Vib(wd,'separator',Cd);Vib(wd,'scrollbar',Ad);Vib(wd,'slider',Dd);Vib(wd,Nub,Ed);Vib(wd,'status',Fd);Vib(wd,'tab',Gd);Vib(wd,'tablist',Hd);Vib(wd,'tabpanel',Id);Vib(wd,'textbox',Jd);Vib(wd,'timer',Kd);Vib(wd,'toolbar',Ld);Vib(wd,'tooltip',Md);Vib(wd,'tree',Nd);Vib(wd,'treegrid',Od);Vib(wd,'treeitem',Pd)}
function ucb(b){var c,d,e;b=xfb(b,(Epb(),Cpb));this.i=b.indexOf('gecko')!=-1&&b.indexOf(Gyb)==-1&&b.indexOf(sAb)==-1;b.indexOf(' presto/')!=-1;this.r=b.indexOf(sAb)!=-1;this.s=!this.r&&b.indexOf('applewebkit')!=-1;this.e=b.indexOf(' chrome/')!=-1||b.indexOf(' crios/')!=-1;this.o=b.indexOf('opera')!=-1;this.j=b.indexOf('msie')!=-1&&!this.o&&b.indexOf('webtv')==-1;this.j=this.j||this.r;this.p=b.indexOf('phantomjs/')!=-1;this.g=b.indexOf(' firefox/')!=-1||b.indexOf('fxios/')!=-1;this.q=!this.e&&!this.j&&!this.p&&!this.g&&b.indexOf('safari')!=-1;if(b.indexOf(' edge/')!=-1){this.f=true;this.e=false;this.o=false;this.j=false;this.q=false;this.g=false;this.s=false;this.i=false;this.p=false}b.indexOf('chromeframe')!=-1;try{if(this.i){d=b.indexOf('rv:');if(d>=0){e=b.substr(d+3);e=tfb(e,'(\\.[0-9]+).+','$1');this.a=xeb(e)}}else if(this.s){e=vfb(b,b.indexOf('webkit/')+7);e=tfb(e,'([0-9]+)[^0-9].+','$1');this.a=xeb(e)}else if(this.r){e=vfb(b,b.indexOf(sAb)+8);e=tfb(e,'([0-9]+\\.[0-9]+).*','$1');this.a=xeb(e);this.a>7&&(this.a=7)}else this.f&&(this.a=0)}catch(a){a=_D(a);if(Zq(a,21)){Rfb()}else throw aE(a)}try{if(this.j){if(b.indexOf('msie')==-1){d=b.indexOf('rv:');if(d>=0){c=d+3;this.d=lcb(b,c);rcb(this,this.d)}}else if(this.r){tcb(this,er(this.a)+4)}else{c=b.indexOf('msie ')+5;this.d=lcb(b,c);rcb(this,this.d)}}else if(this.g){c=b.indexOf(' firefox/');c!=-1?(c+=9):(c=b.indexOf(' fxios/')+7);this.d=lcb(b,c);rcb(this,this.d)}else if(this.e){c=b.indexOf(' chrome/');c!=-1?(c+=8):(c=b.indexOf(' crios/')+7);this.d=lcb(b,c);rcb(this,this.d)}else if(this.q){c=b.indexOf(' version/')+9;this.d=lcb(b,c);rcb(this,this.d)}else if(this.o){c=b.indexOf(' version/');c!=-1?(c+=9):(c=b.indexOf('opera/')+6);this.d=lcb(b,c);rcb(this,this.d)}else if(this.f){c=b.indexOf(' edge/')+6;this.d=lcb(b,c);rcb(this,this.d)}else if(this.p){c=b.indexOf(' phantomjs/')+11;this.d=lcb(b,c);rcb(this,this.d)}}catch(a){a=_D(a);if(Zq(a,21)){Rfb()}else throw aE(a)}if(b.indexOf('windows ')!=-1){this.t=1;b.indexOf('windows phone')!=-1}else if(b.indexOf('android')!=-1){this.t=5;mcb(this,b)}else if(b.indexOf('linux')!=-1){this.t=3}else if(b.indexOf('macintosh')!=-1||b.indexOf('mac osx')!=-1||b.indexOf('mac os x')!=-1){this.k=b.indexOf('ipad')!=-1;this.n=b.indexOf('iphone')!=-1;if(this.k||b.indexOf('ipod')!=-1||this.n){this.t=4;pcb(this,b)}else{this.t=2}}else if(b.indexOf('; cros ')!=-1){this.t=6;ncb(this,b)}}
function n3(a){N4(a.b,KB,null);N4(a.b,dB,KB);N4(a.b,XA,KB);N4(a.b,gB,KB);N4(a.b,hB,KB);N4(a.b,iB,KB);N4(a.b,jB,KB);N4(a.b,kB,KB);N4(a.b,lB,KB);N4(a.b,mB,KB);N4(a.b,PA,XA);N4(a.b,ZA,PA);N4(a.b,nB,ZA);H4(a.b,fA);I4(a.b,bA,new t3);I4(a.b,fA,new S3);I4(a.b,dB,new b4);I4(a.b,nB,new d4);I4(a.b,gB,new f4);I4(a.b,hB,new h4);I4(a.b,iB,new j4);I4(a.b,jB,new l4);I4(a.b,kB,new n4);I4(a.b,lB,new v3);I4(a.b,mB,new x3);L4(a.b,fA,'getWidget',new A4(bA));L4(a.b,fA,'getState',new A4(nB));J4(a.b,Hz,Nyb,new A3);J4(a.b,Hz,Oyb,new C3);J4(a.b,fA,Pyb,new E3);J4(a.b,fA,Qyb,new G3);J4(a.b,fA,Ryb,new I3);o3(a.b);K4(a.b,kB,Syb,new A4(vB));K4(a.b,hB,'am',new A4(QB));K4(a.b,PA,Tyb,new A4(QB));K4(a.b,PA,Uyb,new A4(vB));K4(a.b,mB,Vyb,new A4(EB));K4(a.b,hB,Wyb,new A4(QB));K4(a.b,hB,'dayNames',new A4(eq(QB,1)));K4(a.b,PA,Xyb,new A4(QB));K4(a.b,PA,Yyb,new A4($A));K4(a.b,lB,Zyb,new A4(EB));K4(a.b,lB,$yb,new A4(vB));K4(a.b,lB,_yb,new A4(QB));K4(a.b,lB,azb,new A4(QB));K4(a.b,nB,bzb,new A4(vB));K4(a.b,XA,czb,new A4(vB));K4(a.b,PA,dzb,new A4(aB));K4(a.b,PA,ezb,new A4(QB));K4(a.b,hB,fzb,new A4(EB));K4(a.b,gB,gzb,new A4(EB));K4(a.b,dB,hzb,new A4(vB));K4(a.b,PA,Qub,new A4(QB));K4(a.b,hB,izb,new A4(QB));K4(a.b,PA,'id',new A4(QB));K4(a.b,nB,jzb,new A4(GB));K4(a.b,nB,kzb,new A4(gB));K4(a.b,iB,lzb,new B4(mzb,jq(eq(zz,1),Jtb,5,0,[new A4(hB)])));K4(a.b,nB,nzb,new A4(iB));K4(a.b,mB,'maxWidth',new A4(EB));K4(a.b,kB,'mode',new A4(WA));K4(a.b,hB,ozb,new A4(eq(QB,1)));K4(a.b,hB,lyb,new A4(QB));K4(a.b,nB,pzb,new B4(qzb,jq(eq(zz,1),Jtb,5,0,[new A4(QB),new A4(jB)])));K4(a.b,jB,rzb,new A4(cB));K4(a.b,mB,'openDelay',new A4(EB));K4(a.b,nB,szb,new A4(QB));K4(a.b,nB,'pageState',new A4(dB));K4(a.b,kB,tzb,new B4(qzb,jq(eq(zz,1),Jtb,5,0,[new A4(QB),new A4(QB)])));K4(a.b,hB,'pm',new A4(QB));K4(a.b,nB,uzb,new A4(EB));K4(a.b,jB,'postfix',new A4(QB));K4(a.b,jB,'prefix',new A4(QB));K4(a.b,PA,vzb,new A4(QB));K4(a.b,nB,wzb,new A4(kB));K4(a.b,kB,'pushUrl',new A4(QB));K4(a.b,mB,xzb,new A4(EB));K4(a.b,mB,yzb,new A4(EB));K4(a.b,lB,zzb,new A4(EB));K4(a.b,nB,Azb,new A4(lB));K4(a.b,lB,Bzb,new A4(EB));K4(a.b,XA,Czb,new B4('java.util.Set',jq(eq(zz,1),Jtb,5,0,[new A4(QB)])));K4(a.b,XA,'resources',new B4(qzb,jq(eq(zz,1),Jtb,5,0,[new A4(QB),new A4(YA)])));K4(a.b,gB,Dzb,new A4(EB));K4(a.b,hB,Ezb,new A4(eq(QB,1)));K4(a.b,hB,Fzb,new A4(eq(QB,1)));K4(a.b,PA,Gzb,new B4(mzb,jq(eq(zz,1),Jtb,5,0,[new A4(QB)])));K4(a.b,nB,Hzb,new A4(EB));K4(a.b,nB,'theme',new A4(QB));K4(a.b,gB,Izb,new A4(EB));K4(a.b,nB,Jzb,new A4(vB));K4(a.b,dB,'title',new A4(QB));K4(a.b,nB,Kzb,new A4(mB));K4(a.b,hB,Lzb,new A4(vB));K4(a.b,PA,Rub,new A4(QB));M4(a.b,WA,new K3);M4(a.b,YA,new N3);M4(a.b,$A,new P3);M4(a.b,aB,new U3);M4(a.b,cB,new X3);M4(a.b,eq(QB,1),new $3);G4(a.b,fA,new x4(Hz,Nyb,jq(eq(QB,1),_tb,2,6,[ezb,dzb])));G4(a.b,fA,new x4(Hz,Oyb,jq(eq(QB,1),_tb,2,6,[Czb])));G4(a.b,fA,new y4(Pyb,jq(eq(QB,1),_tb,2,6,['theme'])));G4(a.b,fA,new y4(Qyb,jq(eq(QB,1),_tb,2,6,[Jzb])));G4(a.b,fA,new y4(Ryb,jq(eq(QB,1),_tb,2,6,[jzb])))}
function p3(c){var d={setter:function(a,b){a.a=Idb(b)},getter:function(a){return Ldb(a.a)}};c.Kf(kB,Syb,d);var d={setter:function(a,b){a.a=b},getter:function(a){return a.a}};c.Kf(hB,'am',d);var d={setter:function(a,b){a.eb=b},getter:function(a){return a.eb}};c.Kf(PA,Tyb,d);var d={setter:function(a,b){a.fb=Idb(b)},getter:function(a){return Ldb(a.fb)}};c.Kf(PA,Uyb,d);var d={setter:function(a,b){a.a=b.Xf()},getter:function(a){return Neb(a.a)}};c.Kf(mB,Vyb,d);var d={setter:function(a,b){a.b=b},getter:function(a){return a.b}};c.Kf(hB,Wyb,d);var d={setter:function(a,b){a.c=b},getter:function(a){return a.c}};c.Kf(hB,'dayNames',d);var d={noLayout:1,setter:function(a,b){a.gb=b},getter:function(a){return a.gb}};c.Kf(PA,Xyb,d);var d={noLayout:1,setter:function(a,b){a.hb=b},getter:function(a){return a.hb}};c.Kf(PA,Yyb,d);var d={setter:function(a,b){a.a=b.Xf()},getter:function(a){return Neb(a.a)}};c.Kf(lB,Zyb,d);var d={setter:function(a,b){a.b=Idb(b)},getter:function(a){return Ldb(a.b)}};c.Kf(lB,$yb,d);var d={setter:function(a,b){a.c=b},getter:function(a){return a.c}};c.Kf(lB,_yb,d);var d={setter:function(a,b){a.d=b},getter:function(a){return a.d}};c.Kf(lB,azb,d);var d={setter:function(a,b){a.a=Idb(b)},getter:function(a){return Ldb(a.a)}};c.Kf(nB,bzb,d);var d={setter:function(a,b){a.pb=Idb(b)},getter:function(a){return Ldb(a.pb)}};c.Kf(XA,czb,d);var d={setter:function(a,b){a.ib=b},getter:function(a){return a.ib}};c.Kf(PA,dzb,d);var d={setter:function(a,b){a.jb=b},getter:function(a){return a.jb}};c.Kf(PA,ezb,d);var d={setter:function(a,b){a.d=b.Xf()},getter:function(a){return Neb(a.d)}};c.Kf(hB,fzb,d);var d={setter:function(a,b){a.a=b.Xf()},getter:function(a){return Neb(a.a)}};c.Kf(gB,gzb,d);var d={setter:function(a,b){a.a=Idb(b)},getter:function(a){return Ldb(a.a)}};c.Kf(dB,hzb,d);var d={setter:function(a,b){a.kb=b},getter:function(a){return a.kb}};c.Kf(PA,Qub,d);var d={setter:function(a,b){a.e=b},getter:function(a){return a.e}};c.Kf(hB,izb,d);var d={setter:function(a,b){a.lb=b},getter:function(a){return a.lb}};c.Kf(PA,'id',d);var d={setter:function(a,b){a.b=b.Yf()},getter:function(a){return Xeb(a.b)}};c.Kf(nB,jzb,d);var d={setter:function(a,b){a.c=b},getter:function(a){return a.c}};c.Kf(nB,kzb,d);var d={setter:function(a,b){a.a=b},getter:function(a){return a.a}};c.Kf(iB,lzb,d);var d={setter:function(a,b){a.d=b},getter:function(a){return a.d}};c.Kf(nB,nzb,d);var d={setter:function(a,b){a.b=b.Xf()},getter:function(a){return Neb(a.b)}};c.Kf(mB,'maxWidth',d);var d={setter:function(a,b){a.b=b},getter:function(a){return a.b}};c.Kf(kB,'mode',d);var d={setter:function(a,b){a.f=b},getter:function(a){return a.f}};c.Kf(hB,ozb,d);var d={setter:function(a,b){a.g=b},getter:function(a){return a.g}};c.Kf(hB,lyb,d);var d={setter:function(a,b){a.e=b},getter:function(a){return a.e}};c.Kf(nB,pzb,d);var d={setter:function(a,b){a.a=b},getter:function(a){return a.a}};c.Kf(jB,rzb,d);var d={setter:function(a,b){a.c=b.Xf()},getter:function(a){return Neb(a.c)}};c.Kf(mB,'openDelay',d);var d={setter:function(a,b){a.f=b},getter:function(a){return a.f}};c.Kf(nB,szb,d);var d={setter:function(a,b){a.g=b},getter:function(a){return a.g}};c.Kf(nB,'pageState',d);var d={setter:function(a,b){a.c=b},getter:function(a){return a.c}};c.Kf(kB,tzb,d);var d={setter:function(a,b){a.i=b},getter:function(a){return a.i}};c.Kf(hB,'pm',d);var d={setter:function(a,b){a.i=b.Xf()},getter:function(a){return Neb(a.i)}};c.Kf(nB,uzb,d);var d={setter:function(a,b){a.b=b},getter:function(a){return a.b}};c.Kf(jB,'postfix',d);var d={setter:function(a,b){a.c=b},getter:function(a){return a.c}};c.Kf(jB,'prefix',d);var d={setter:function(a,b){a.mb=b},getter:function(a){return a.mb}};c.Kf(PA,vzb,d);var d={setter:function(a,b){a.j=b},getter:function(a){return a.j}};c.Kf(nB,wzb,d);var d={setter:function(a,b){a.d=b},getter:function(a){return a.d}};c.Kf(kB,'pushUrl',d);var d={setter:function(a,b){a.d=b.Xf()},getter:function(a){return Neb(a.d)}};c.Kf(mB,xzb,d);var d={setter:function(a,b){a.e=b.Xf()},getter:function(a){return Neb(a.e)}};c.Kf(mB,yzb,d);var d={setter:function(a,b){a.e=b.Xf()},getter:function(a){return Neb(a.e)}};c.Kf(lB,zzb,d);var d={setter:function(a,b){a.k=b},getter:function(a){return a.k}};c.Kf(nB,Azb,d);var d={setter:function(a,b){a.f=b.Xf()},getter:function(a){return Neb(a.f)}};c.Kf(lB,Bzb,d);var d={noLayout:1,setter:function(a,b){a.qb=b},getter:function(a){return a.qb}};c.Kf(XA,Czb,d);var d={setter:function(a,b){a.rb=b},getter:function(a){return a.rb}};c.Kf(XA,'resources',d);var d={setter:function(a,b){a.b=b.Xf()},getter:function(a){return Neb(a.b)}};c.Kf(gB,Dzb,d);var d={setter:function(a,b){a.j=b},getter:function(a){return a.j}};c.Kf(hB,Ezb,d);var d={setter:function(a,b){a.k=b},getter:function(a){return a.k}};c.Kf(hB,Fzb,d);var d={setter:function(a,b){a.nb=b},getter:function(a){return a.nb}};c.Kf(PA,Gzb,d);var d={noLayout:1,setter:function(a,b){a.n=b.Xf()},getter:function(a){return Neb(a.n)}};c.Kf(nB,Hzb,d);var d={setter:function(a,b){a.o=b},getter:function(a){return a.o}};c.Kf(nB,'theme',d);var d={setter:function(a,b){a.c=b.Xf()},getter:function(a){return Neb(a.c)}};c.Kf(gB,Izb,d);var d={setter:function(a,b){a.p=Idb(b)},getter:function(a){return Ldb(a.p)}};c.Kf(nB,Jzb,d)}
var Gtb='object',Htb='anonymous',Itb='fnStack',Jtb={3:1},Ktb='Unknown',Ltb='boolean',Mtb='number',Ntb='string',Otb=2147483647,Ptb=-17592186044416,Qtb=17592186044416,Rtb=4194304,Stb=1048575,Ttb='__noinit__',Utb={3:1,21:1,24:1,20:1},Vtb={3:1,21:1,33:1,24:1,20:1},Wtb='For input string: "',Xtb='null',Ytb=-2147483648,Ztb=16384,$tb='\\$',_tb={3:1,44:1},aub=65536,bub=65535,cub={38:1,81:1},dub={38:1,102:1},eub={101:1},fub={3:1,38:1,81:1,180:1},gub={3:1,151:1},hub={3:1,38:1,102:1},iub='fromIndex: 0, toIndex: ',jub=', length: ',kub='Index: ',lub=', Size: ',mub='fromIndex: ',nub=', toIndex: ',oub='java.lang',pub='com.google.gwt.core.client',qub='com.google.gwt.core.client.impl',rub='java.util',tub='java.util.stream',uub=3.141592653589793,vub='com.google.gwt.animation.client',wub='com.google.gwt.user.client',xub='com.google.gwt.aria.client',yub='alertdialog',zub='application',Aub='button',Bub='columnheader',Cub='complementary',Dub='contentinfo',Eub='definition',Fub='link',Gub='menuitemcheckbox',Hub='menuitemradio',Iub='navigation',Jub='option',Kub='presentation',Lub='progressbar',Mub='radiogroup',Nub='spinbutton',Oub='offsetWidth',Pub='none',Qub='height',Rub='width',Sub='Null widget handle. If you are creating a composite, ensure that initWidget() has been called.',Tub='Style names cannot be empty',Uub='aria-hidden',Vub='com.google.gwt.user.client.ui',Wub={18:1,14:1,9:1,16:1,19:1,12:1,13:1},Xub='disabled',Yub={18:1,14:1,9:1,60:1,73:1,16:1,19:1,12:1,13:1},Zub='com.google.gwt.canvas.client',$ub='class',_ub={116:1},avb='CSS1Compat',bvb='com.google.gwt.dom.client',cvb='MouseEvents',dvb='DOMImplStandard',evb=1000,fvb='DOMImplMozilla',gvb='position',hvb='fixed',ivb='absolute',jvb='DOMImplStandardBase',kvb='DOMImplWebkit',lvb='load',mvb='contextmenu',nvb='display',ovb='visibility',pvb='zIndex',qvb={55:1,15:1,3:1,6:1,4:1},rvb='HIDDEN',svb={23:1,15:1,3:1,6:1,4:1},tvb='block',uvb={15:1,62:1,3:1,6:1,4:1},vvb={15:1,63:1,3:1,6:1,4:1},wvb={15:1,64:1,3:1,6:1,4:1},xvb={15:1,95:1,3:1,6:1,4:1},yvb={34:1,3:1,6:1,4:1},zvb={15:1,96:1,3:1,6:1,4:1},Avb='visible',Bvb='hidden',Cvb={15:1,56:1,3:1,6:1,4:1},Dvb='com.google.web.bindery.event.shared',Evb='com.google.gwt.event.shared',Fvb='com.google.gwt.event.dom.client',Gvb='mouseup',Hvb='TouchEvent',Ivb='touchcancel',Jvb='ontouchstart',Kvb='touchstart',Lvb='com.google.gwt.event.logical.shared',Mvb={90:1,3:1,21:1,24:1,20:1},Nvb='UmbrellaException',Ovb=4194303,Pvb=524288,Qvb=1000000000,Rvb='java.util.logging',Svb='com.google.gwt.logging.client',Tvb='com.google.gwt.logging.impl',Uvb='java.io',Vvb='com.google.gwt.safehtml.shared',Wvb='com.google.gwt.text.shared.testing',Xvb=32768,Yvb='DOMMouseScroll',Zvb=131072,$vb=262144,_vb=1048576,awb=2097152,bwb=8388608,cwb=16777216,dwb=33554432,ewb=67108864,fwb={115:1},gwb='com.google.gwt.user.client.impl',hwb='__gwtLastUnhandledEvent',iwb={18:1,14:1,9:1,16:1,31:1,19:1,12:1,13:1},jwb='left',kwb='top',lwb={18:1,14:1,9:1,16:1,179:1,19:1,12:1,13:1},mwb={25:1},nwb='bidiwrapped',owb='selected',pwb='subMenuIcon-selected',qwb='offsetHeight',rwb='px',swb='0.0px',twb={27:1,182:1},uwb='overflow',vwb={18:1,14:1,9:1,16:1,31:1,19:1,135:1,12:1,13:1},wwb={674:1,27:1},xwb='value',ywb={65:1,3:1,6:1,4:1},zwb='com.google.gwt.user.client.ui.impl',Awb={147:1,149:1},Bwb='auto',Cwb='cell-comment-triangle',Dwb='cell-invalidformula-triangle',Ewb='col',Fwb=' row',Gwb='pointerEvents',Hwb='com.vaadin.addon.spreadsheet.client',Iwb='animate-in',Jwb='animate-out',Kwb='marginLeft',Lwb='marginTop',Mwb='com.vaadin.client.widgets',Nwb='com.vaadin.client.ui',Owb='spreadsheet-overlays',Pwb=57.29577951308232,Qwb='rotate(',Rwb='webkitTransform',Swb='comment-overlay-separator',Twb='comment-overlay-line',Uwb={181:1,27:1},Vwb={18:1,14:1,9:1,16:1,31:1,19:1,12:1,13:1,97:1},Wwb={9:1},Xwb='backgroundColor',Ywb='border',Zwb='2px solid ',$wb='borderRight',_wb='borderBottom',axb='textAlign',bxb='merged-cell',cxb='com.vaadin.shared',dxb='There is no information about the state for ',exb='. Did you remember to compile the right widgetset?',fxb={27:1,100:1,123:1,3:1},gxb='active',hxb='com.vaadin.shared.communication',ixb={67:1,75:1,3:1},jxb='popupbutton',kxb='touch',lxb='fill',mxb='bottom-left',nxb='top-left',oxb='v-contextmenu',pxb='bottom-right',qxb='sheet-selection',rxb='s-left',sxb='s-right',txb='s-bottom',uxb='square',vxb='fill-touch-square',wxb='sheet',xxb='cell',yxb=16022015,zxb='sheet-image',Axb='paddingLeft',Bxb='paddingTop',Cxb='marginRight',Dxb='selected-tab',Exb='.col',Fxb='.v-spreadsheet.',Gxb='cell-range',Hxb='selected-cell-highlight',Ixb='selected-row-header',Jxb='selected-column-header',Kxb=' .sheet .cell.cell-range {',Lxb=' .sheet .col',Mxb='display:none;',Nxb=' .sheet .row',Oxb='.notusedselector',Pxb='custom-editor-cell',Qxb=', .v-spreadsheet.',Rxb={22:1,3:1},Sxb='Hide column',Txb=' > div.ch.col',Uxb='px;}',Vxb=' > div.rh.row',Wxb='resize-line',Xxb='5555555555',Yxb='text/css',Zxb='ch col',$xb='<div class="header-resize-dnd-first" ><\/div><div class="header-resize-dnd-second" ><\/div>',_xb='rh row',ayb='notfocused',byb=' merged-cell',cyb=' while creating the cell styles',dyb='expandbutton',eyb='lineHeight',fyb='inactive',gyb='row-resizing',hyb='col-resizing',iyb='header-resize-dnd-first',jyb='header-resize-dnd-second',kyb={150:1,27:1},lyb='name',myb={723:1,27:1},nyb={25:1,726:1,127:1},oyb='showCustomEditorOnFocus',pyb='workbookChangeToggle',qyb='hiddenColumnIndexes',ryb='hiddenRowIndexes',syb='verticalSplitPosition',tyb='horizontalSplitPosition',uyb='custom-editor-',vyb='com.vaadin.client',wyb='lock-format-columns',xyb='lock-format-rows',yyb='com.vaadin.addon.spreadsheet.shared',zyb='com.vaadin.shared.ui',Ayb='-webkit-animation-name',Byb='animation-name',Cyb='-moz-animation-name',Dyb='-o-animation-name',Eyb='fakeelement',Fyb='animationend',Gyb='webkit',Hyb='com.vaadin.client.communication',Iyb='head',Jyb='stylesheet',Kyb='com.vaadin.client.metadata',Lyb={250:1,27:1},Myb='com.vaadin.ui.UI',Nyb='setErrorLevel',Oyb='handleContextClickListenerChange',Pyb='onThemeChange',Qyb='onThoroughSizeChckChange',Ryb='signalRoundTripCompleted',Syb='alwaysUseXhrForServerRequests',Tyb='caption',Uyb='captionAsHtml',Vyb='closeTimeout',Wyb='dateFormat',Xyb='description',Yyb='descriptionContentMode',Zyb='dialogGracePeriod',$yb='dialogModal',_yb='dialogText',azb='dialogTextGaveUp',bzb='enableMobileHTML5DnD',czb='enabled',dzb='errorLevel',ezb='errorMessage',fzb='firstDayOfWeek',gzb='firstDelay',hzb='hasResizeListeners',izb='hourMinuteDelimiter',jzb='latestDelayedCallbackID',kzb='loadingIndicatorConfiguration',lzb='localeData',mzb='java.util.List',nzb='localeServiceState',ozb='monthNames',pzb='notificationConfigurations',qzb='java.util.Map',rzb='notificationRole',szb='overlayContainerLabel',tzb='parameters',uzb='pollInterval',vzb='primaryStyleName',wzb='pushConfiguration',xzb='quickOpenDelay',yzb='quickOpenTimeout',zzb='reconnectAttempts',Azb='reconnectDialogConfiguration',Bzb='reconnectInterval',Czb='registeredEventListeners',Dzb='secondDelay',Ezb='shortDayNames',Fzb='shortMonthNames',Gzb='styles',Hzb='tabIndex',Izb='thirdDelay',Jzb='thoroughSizeCheck',Kzb='tooltipConfiguration',Lzb='twelveHourClock',Mzb={32:1},Nzb={150:1,251:1,252:1,27:1},Ozb='_vScrollTop',Pzb='v-scrollable',Qzb='v-label-undef-w',Rzb='/favicon.ico',Szb='com.vaadin.client.ui.ui',Tzb='com.vaadin.component.spreadsheet.client.js',Uzb='rowBufferSize',Vzb='columnBufferSize',Wzb='colGroupingData',Xzb='rowGroupingData',Yzb='colGroupingMax',Zzb='rowGroupingMax',$zb='colGroupingInversed',_zb='rowGroupingInversed',aAb='cellStyleToCSSStyle',bAb='rowIndexToStyleIndex',cAb='columnIndexToStyleIndex',dAb='lockedColumnIndexes',eAb='lockedRowIndexes',fAb='shiftedCellBorderStyles',gAb='conditionalFormattingStyles',hAb='verticalScrollPositions',iAb='horizontalScrollPositions',jAb='workbookProtected',kAb='hyperlinksTooltips',lAb='displayGridlines',mAb='displayRowColHeadings',nAb='infoLabelValue',oAb='invalidFormulaErrorMessage',pAb='lockFormatColumns',qAb='lockFormatRows',rAb='namedRanges',sAb='trident/',tAb='WARNING',uAb='com.vaadin.shared.ui.ui',vAb=244140625,wAb=1220703125,xAb=0.3010299956639812,yAb=4294967295,zAb='BigInteger divide by zero',AAb=4294967296,BAb=1073741824,CAb={l:0,m:0,h:524288},DAb='delete',EAb={3:1,727:1},FAb='locale',GAb='default',HAb='user.agent';var _,EE,zE,VD=-1;$wnd.goog=$wnd.goog||{};$wnd.goog.global=$wnd.goog.global||$wnd;FE();GE(1,null,{},K);_.Zc=function L(a){return J(this,a)};_.$c=function N(){return this.qg};_._c=function P(){return xtb(this)};_.ad=function R(){var a;return Sdb(O(this))+'@'+(a=Q(this)>>>0,a.toString(16))};_.equals=function(a){return this.Zc(a)};_.hashCode=function(){return this._c()};_.toString=function(){return this.ad()};var Fg;GE(693,1,{});GE(311,693,{},Ng);_.Md=function Og(a){var b={},j;var c=[];a[Itb]=c;var d=arguments.callee.caller;while(d){var e=(Gg(),d.name||(d.name=Jg(d.toString())));c.push(e);var f=':'+e;var g=b[f];if(g){var h,i;for(h=0,i=g.length;h<i;h++){if(g[h]===d){return}}}(g||(b[f]=[])).push(d);d=d.caller}};_.Nd=function Pg(a){var b,c,d,e;d=(Gg(),a&&a[Itb]?a[Itb]:[]);c=d.length;e=gq(MB,Jtb,74,c,0,1);for(b=0;b<c;b++){e[b]=new dfb(d[b],null,-1)}return e};GE(694,693,{});_.Md=function Rg(a){};_.Od=function Sg(a,b,c,d){return new dfb(b,a+'@'+d,c<0?-1:c)};_.Nd=function Tg(a){var b,c,d,e,f,g;e=Lg(a);f=gq(MB,Jtb,74,0,0,1);b=0;d=e.length;if(d==0){return f}g=Qg(this,e[0]);jfb(g.d,Htb)||(f[b++]=g);for(c=1;c<d;c++){f[b++]=Qg(this,e[c])}return f};GE(312,694,{},Ug);_.Od=function Vg(a,b,c,d){return new dfb(b,a,-1)};var Uq,Vq,Wq;GE(20,1,{3:1,20:1});_.Dd=function Af(a){return new Error(a)};_.Ed=function Cf(){return this.backingJsObject};_.Fd=function Df(){var a;return a=tsb(usb(Elb((this.j==null&&(this.j=gq(SB,Jtb,20,0,0,1)),this.j)),new Ufb),Rrb(new asb,new $rb,new csb,jq(eq(xD,1),Jtb,87,0,[(Vrb(),Trb)]))),a.Zf(Csb(a.size()))};_.Gd=function Ef(){return this.e};_.Hd=function Ff(){return this.f};_.Id=function Gf(){yf(this,Bf(this.Dd(zf(this,this.f))));Hg(this)};_.ad=function If(){return zf(this,this.Hd())};_.backingJsObject=Ttb;_.g=false;_.k=true;GE(21,20,{3:1,21:1,20:1});GE(24,21,Utb,Lf,Mf);GE(33,24,Vtb,Ddb,Edb);GE(185,33,Vtb,Fdb);Uq={3:1,309:1,6:1};GE(183,1,{},Udb);_.Rf=function Vdb(a){var b;b=new Udb;b.f=4;a>1?(b.c=aeb(this,a-1)):(b.c=this);return b};_.Sf=function _db(){Rdb(this);return this.b};_.Tf=function beb(){return Sdb(this)};_.Uf=function deb(){return Tdb(this)};_.Vf=function feb(){return (this.f&4)!=0};_.Wf=function geb(){return (this.f&1)!=0};_.ad=function jeb(){return ((this.f&2)!=0?'interface ':(this.f&1)!=0?'':'class ')+(Rdb(this),this.k)};_.f=0;var Qdb=1;GE(83,1,{3:1,83:1});var meb;Vq={3:1,6:1,310:1,83:1};GE(4,1,{3:1,6:1,4:1});_.je=function Qj(a){return this.c-a.c};_.compareTo=function Pj(a){return this.c-a.c};_.equals=function Rj(a){return this===a};_.Zc=function(a){return this.equals(a)};_.hashCode=function Sj(){return xtb(this)};_._c=function(){return this.hashCode()};_.name=function Tj(){return this.b!=null?this.b:''+this.c};_.ordinal=function Uj(){return this.c};_.toString=function Vj(){return Nj(this)};_.ad=function(){return this.toString()};_.c=0;GE(68,24,Utb,zeb);GE(37,24,Utb,Aeb,Beb,Ceb);GE(91,83,{3:1,6:1,91:1,83:1},Eeb);_.je=function Geb(a){return Feb(this.a,a.a)};_.Zc=function Heb(a){return Deb(this,a)};_._c=function Ieb(){return this.a};_.Xf=function Jeb(){return this.a};_.ad=function Meb(){return ''+this.a};_.a=0;GE(103,24,Utb,Nf);GE(824,1,{});GE(50,103,{3:1,21:1,50:1,24:1,20:1},$eb,_eb,afb);_.Dd=function bfb(a){return new TypeError(a)};Wq={3:1,184:1,6:1,2:1};GE(216,33,Vtb,Pfb);GE(254,1,{},Ufb);_.Of=function Vfb(a){return Tfb(a)};GE(29,24,Utb,Wfb,Xfb);GE(695,1,{38:1});_.add=function hib(a){throw aE(new Xfb('Add not supported on this collection'))};_.addAll=function iib(a){return cib(this,a)};_.clear=function jib(){var a;for(a=this.Oe();a.Ye();){a.Ze();a.$e()}};_.contains=function kib(a){return dib(this,a,false)};_.containsAll=function lib(a){return eib(this,a)};_.isEmpty=function mib(){return this.size()==0};_.remove=function nib(a){return dib(this,a,true)};_.removeAll=function oib(a){var b,c,d;ptb(a);b=false;for(c=this.Oe();c.Ye();){d=c.Ze();if(a.contains(d)){c.$e();b=true}}return b};_.retainAll=function pib(a){var b,c,d;ptb(a);b=false;for(c=this.Oe();c.Ye();){d=c.Ze();if(!a.contains(d)){c.$e();b=true}}return b};_.toArray=function qib(){return this.Zf(gq(KB,Jtb,1,this.size(),5,1))};_.Zf=function rib(a){return fib(this,a)};_.ad=function sib(){return gib(this)};GE(696,695,cub);_.addAtIndex=function xjb(a,b){throw aE(new Xfb('Add not supported on this list'))};_.add=function yjb(a){this.addAtIndex(this.size(),a);return true};_.addAllAtIndex=function zjb(a,b){var c,d,e;ptb(b);c=false;for(e=b.Oe();e.Ye();){d=e.Ze();this.addAtIndex(a++,d);c=true}return c};_.clear=function Ajb(){this.bg(0,this.size())};_.Zc=function Bjb(a){var b,c,d,e,f;if(a===this){return true}if(!Zq(a,81)){return false}f=a;if(this.size()!=f.size()){return false}e=f.Oe();for(c=this.Oe();c.Ye();){b=c.Ze();d=e.Ze();if(!(dr(b)===dr(d)||b!=null&&M(b,d))){return false}}return true};_._c=function Cjb(){return Tlb(this)};_.indexOf=function Djb(a){return wjb(this,a)};_.Oe=function Ejb(){return new Njb(this)};_.lastIndexOf=function Fjb(a){var b;for(b=this.size()-1;b>-1;--b){if(Lpb(a,this.getAtIndex(b))){return b}}return -1};_._f=function Gjb(){return this.ag(0)};_.ag=function Hjb(a){return new Rjb(this,a)};_.removeAtIndex=function Ijb(a){throw aE(new Xfb('Remove not supported on this list'))};_.bg=function Jjb(a,b){var c,d;d=this.ag(a);for(c=a;c<b;++c){d.Ze();d.$e()}};_.setAtIndex=function Kjb(a,b){throw aE(new Xfb('Set not supported on this list'))};_.subList=function Ljb(a,b){return new Xjb(this,a,b)};GE(202,1,{},Njb);_.Ye=function Ojb(){return this.b<this.d.size()};_.Ze=function Pjb(){ntb(this.b<this.d.size());return this.d.getAtIndex(this.c=this.b++)};_.$e=function Qjb(){Mjb(this)};_.b=0;_.c=-1;GE(313,202,{},Rjb);_.$e=function Vjb(){Mjb(this)};_.cg=function Sjb(a){this.a.addAtIndex(this.b,a);++this.b;this.c=-1};_.dg=function Tjb(){return this.b>0};_.eg=function Ujb(){ntb(this.b>0);return this.a.getAtIndex(this.c=--this.b)};_.fg=function Wjb(a){ttb(this.c!=-1);this.a.setAtIndex(this.c,a)};GE(314,696,cub,Xjb);_.addAtIndex=function Yjb(a,b){rtb(a,this.b);this.c.addAtIndex(this.a+a,b);++this.b};_.getAtIndex=function Zjb(a){otb(a,this.b);return this.c.getAtIndex(this.a+a)};_.removeAtIndex=function $jb(a){var b;otb(a,this.b);b=this.c.removeAtIndex(this.a+a);--this.b;return b};_.setAtIndex=function _jb(a,b){otb(a,this.b);return this.c.setAtIndex(this.a+a,b)};_.size=function akb(){return this.b};_.a=0;_.b=0;GE(697,1,{151:1});_.getOrDefault=function Dib(a,b){var c;return c=this.get(a),c==null&&!this.containsKey(a)?b:c};_.putIfAbsent=function Jib(a,b){var c;return c=this.get(a),c!=null?c:this.put(a,b)};_.replace=function Lib(a,b){return this.containsKey(a)?this.put(a,b):null};_.clear=function xib(){this.$f().clear()};_.containsKey=function yib(a){return !!uib(this,a,false)};_.containsValue=function zib(a){var b,c,d;for(c=this.$f().Oe();c.Ye();){b=c.Ze();d=b.hg();if(dr(a)===dr(d)||a!=null&&M(a,d)){return true}}return false};_.Zc=function Aib(a){var b,c,d;if(a===this){return true}if(!Zq(a,151)){return false}d=a;if(this.size()!=d.size()){return false}for(c=d.$f().Oe();c.Ye();){b=c.Ze();if(!tib(this,b)){return false}}return true};_.get=function Bib(a){return Cib(uib(this,a,false))};_._c=function Eib(){return Slb(this.$f())};_.isEmpty=function Fib(){return this.size()==0};_.keySet=function Gib(){return new bkb(this)};_.put=function Hib(a,b){throw aE(new Xfb('Put not supported on this map'))};_.putAll=function Iib(a){vib(this,a)};_.remove=function Kib(a){return Cib(uib(this,a,true))};_.size=function Mib(){return this.$f().size()};_.ad=function Nib(){var a,b,c;c=new xqb('{','}');for(b=this.$f().Oe();b.Ye();){a=b.Ze();wqb(c,wib(this,a.gg())+'='+wib(this,a.hg()))}return !c.a?c.c:c.e.length==0?c.a.a:c.a.a+(''+c.e)};_.values=function Oib(){return new mkb(this)};GE(698,695,dub);_.Zc=function gjb(a){var b;if(a===this){return true}if(!Zq(a,102)){return false}b=a;if(b.size()!=this.size()){return false}return eib(this,b)};_._c=function hjb(){return Slb(this)};_.removeAll=function ijb(a){var b,c,d,e;ptb(a);e=this.size();if(e<a.size()){for(b=this.Oe();b.Ye();){c=b.Ze();a.contains(c)&&b.$e()}}else{for(d=a.Oe();d.Ye();){c=d.Ze();this.remove(c)}}return e!=this.size()};GE(58,698,dub,bkb);_.clear=function ckb(){this.a.clear()};_.contains=function dkb(a){return this.a.containsKey(a)};_.Oe=function ekb(){var a;return a=this.a.$f().Oe(),new hkb(a)};_.remove=function fkb(a){if(this.a.containsKey(a)){this.a.remove(a);return true}return false};_.size=function gkb(){return this.a.size()};GE(61,1,{},hkb);_.Ye=function ikb(){return this.a.Ye()};_.Ze=function jkb(){var a;return a=this.a.Ze(),a.gg()};_.$e=function kkb(){this.a.$e()};GE(39,695,{38:1},mkb);_.clear=function nkb(){this.a.clear()};_.contains=function okb(a){return lkb(this,a)};_.Oe=function pkb(){var a;return a=this.a.$f().Oe(),new rkb(a)};_.size=function qkb(){return this.a.size()};GE(52,1,{},rkb);_.Ye=function skb(){return this.a.Ye()};_.Ze=function tkb(){var a;return a=this.a.Ze(),a.hg()};_.$e=function ukb(){this.a.$e()};GE(321,1,eub);_.Zc=function vkb(a){var b;if(!Zq(a,101)){return false}b=a;return Lpb(this.a,b.gg())&&Lpb(this.b,b.hg())};_.gg=function wkb(){return this.a};_.hg=function xkb(){return this.b};_._c=function ykb(){return Mpb(this.a)^Mpb(this.b)};_.ig=function zkb(a){var b;b=this.b;this.b=a;return b};_.ad=function Akb(){return this.a+'='+this.b};GE(210,321,eub,Bkb);GE(10,696,{3:1,10:1,38:1,81:1,180:1},Wkb,Xkb,Ykb);_.addAtIndex=function Zkb(a,b){Mkb(this,a,b)};_.add=function $kb(a){return Nkb(this,a)};_.addAllAtIndex=function _kb(a,b){var c,d;rtb(a,this.a.length);c=b.toArray();d=c.length;if(d==0){return false}itb(this.a,a,c);return true};_.addAll=function alb(a){return Okb(this,a)};_.clear=function blb(){this.a=gq(KB,Jtb,1,0,5,1)};_.contains=function clb(a){return Rkb(this,a,0)!=-1};_.getAtIndex=function dlb(a){return Qkb(this,a)};_.indexOf=function elb(a){return Rkb(this,a,0)};_.isEmpty=function flb(){return this.a.length==0};_.Oe=function glb(){return new slb(this)};_.lastIndexOf=function hlb(a){return Skb(this,a,this.a.length-1)};_.removeAtIndex=function ilb(a){return Tkb(this,a)};_.remove=function jlb(a){return Ukb(this,a)};_.bg=function klb(a,b){var c;stb(a,b,this.a.length);c=b-a;jtb(this.a,a,c)};_.setAtIndex=function llb(a,b){var c;c=(otb(a,this.a.length),this.a[a]);this.a[a]=b;return c};_.size=function mlb(){return this.a.length};_.toArray=function nlb(){return ftb(this.a,this.a.length)};_.Zf=function olb(a){return Vkb(this,a)};GE(7,1,{},slb);_.Ye=function tlb(){return plb(this)};_.Ze=function ulb(){return qlb(this)};_.$e=function vlb(){rlb(this)};_.a=0;_.b=-1;var Olb,Plb,Qlb;GE(389,696,fub,Vlb);_.contains=function Wlb(a){return false};_.getAtIndex=function Xlb(a){return otb(a,0),null};_.Oe=function Ylb(){return Rlb(),amb(),_lb};_._f=function Zlb(){return Rlb(),amb(),_lb};_.size=function $lb(){return 0};GE(390,1,{},cmb);_.cg=function dmb(a){throw aE(new Wfb)};_.Ye=function emb(){return false};_.dg=function fmb(){return false};_.Ze=function gmb(){return bmb()};_.eg=function hmb(){throw aE(new Kpb)};_.$e=function imb(){throw aE(new Aeb)};_.fg=function jmb(a){throw aE(new Aeb)};var _lb;GE(392,697,gub,kmb);_.containsKey=function lmb(a){return false};_.containsValue=function mmb(a){return false};_.$f=function nmb(){return Rlb(),Qlb};_.get=function omb(a){return null};_.keySet=function pmb(){return Rlb(),Qlb};_.size=function qmb(){return 0};_.values=function rmb(){return Rlb(),Olb};GE(391,698,hub,smb);_.contains=function tmb(a){return false};_.Oe=function umb(){return Rlb(),amb(),_lb};_.size=function vmb(){return 0};GE(66,24,{3:1,21:1,24:1,20:1,66:1},Kpb);GE(154,1,{});_.lg=function _pb(a){Npb(this,a)};_.jg=function Zpb(){return this.c};_.kg=function $pb(){return this.d};_.c=0;_.d=0;GE(186,154,{});GE(261,1,{});_.lg=function lqb(a){Npb(this,a)};_.jg=function jqb(){return this.b};_.kg=function kqb(){return this.d-this.c};_.b=0;_.c=0;_.d=0;GE(191,261,{},nqb);_.lg=function oqb(a){hqb(this,a)};_.mg=function pqb(a){return iqb(this,a)};GE(258,1,{},zqb);_.Of=function Aqb(a){return a};GE(87,4,{3:1,6:1,4:1,87:1},Wrb);var Srb,Trb,Urb;GE(667,1,{},Yrb);GE(256,1,{},$rb);_.Df=function _rb(a,b){Zrb(a,b)};GE(255,1,{},asb);_.Pf=function bsb(){return new Wkb};GE(257,1,{},csb);GE(171,1,{});_.c=false;GE(79,171,{},zsb);var qsb;GE(533,186,{},Tsb);_.mg=function Usb(a){return this.b.mg(new Vsb(this,a))};GE(538,1,{},Vsb);_.Qf=function Wsb(a){Ssb(this.a,this.b,a)};GE(537,1,{},Ysb);_.Qf=function Zsb(a){Xsb(this,a)};GE(542,1,{},$sb);_.Qf=function _sb(a){rsb()};GE(543,1,{},btb);GE(544,1,{},dtb);_.Qf=function etb(a){ctb(this,a)};GE(826,1,{});GE(822,1,{});var wtb=0;var ytb,ztb=0,Atb;var KB=Xdb(oub,'Object',1);var Js=Xdb(pub,'JavaScriptObject$',0);var Ts=Xdb(qub,'StackTraceCreator/Collector',693);var Qs=Xdb(qub,'StackTraceCreator/CollectorLegacy',311);var Ss=Xdb(qub,'StackTraceCreator/CollectorModern',694);var Rs=Xdb(qub,'StackTraceCreator/CollectorModernNoSourceMap',312);var SB=Xdb(oub,'Throwable',20);var zB=Xdb(oub,'Exception',21);var LB=Xdb(oub,'RuntimeException',24);var DB=Xdb(oub,'IndexOutOfBoundsException',33);var uB=Xdb(oub,'ArrayIndexOutOfBoundsException',185);var vB=Xdb(oub,'Boolean',309);var wB=Xdb(oub,'Class',183);var JB=Xdb(oub,'Number',83);var xB=Xdb(oub,'Double',310);var yB=Xdb(oub,'Enum',4);var BB=Xdb(oub,'IllegalArgumentException',68);var CB=Xdb(oub,'IllegalStateException',37);var EB=Xdb(oub,'Integer',91);var FB=Xdb(oub,'JsException',103);var HB=Xdb(oub,'NullPointerException',50);var QB=Xdb(oub,'String',2);var PB=Xdb(oub,'StringIndexOutOfBoundsException',216);var RB=Xdb(oub,'Throwable/lambda$0$Type',254);var TB=Xdb(oub,'UnsupportedOperationException',29);var WB=Xdb(rub,'AbstractCollection',695);var QC=Zdb(rub,'List');var bC=Xdb(rub,'AbstractList',696);var $B=Xdb(rub,'AbstractList/IteratorImpl',202);var _B=Xdb(rub,'AbstractList/ListIteratorImpl',313);var aC=Xdb(rub,'AbstractList/SubList',314);var UC=Zdb(rub,'Map');var jC=Xdb(rub,'AbstractMap',697);var WC=Zdb(rub,'Set');var lC=Xdb(rub,'AbstractSet',698);var dC=Xdb(rub,'AbstractMap/1',58);var cC=Xdb(rub,'AbstractMap/1/1',61);var fC=Xdb(rub,'AbstractMap/2',39);var eC=Xdb(rub,'AbstractMap/2/1',52);var gC=Xdb(rub,'AbstractMap/AbstractEntry',321);var hC=Xdb(rub,'AbstractMap/SimpleEntry',210);var nC=Xdb(rub,'ArrayList',10);var mC=Xdb(rub,'ArrayList/1',7);var qC=Xdb(rub,'Collections/EmptyList',389);var pC=Xdb(rub,'Collections/EmptyListIterator',390);var rC=Xdb(rub,'Collections/EmptyMap',392);var sC=Xdb(rub,'Collections/EmptySet',391);var VC=Xdb(rub,'NoSuchElementException',66);var eD=Xdb(rub,'Spliterators/BaseSpliterator',154);var bD=Xdb(rub,'Spliterators/AbstractSpliterator',186);var dD=Xdb(rub,'Spliterators/BaseArraySpliterator',261);var cD=Xdb(rub,'Spliterators/ArraySpliterator',191);var hD=Xdb('java.util.function','Function/lambda$0$Type',258);var xD=Ydb(tub,'Collector/Characteristics',87,Xrb);var yD=Xdb(tub,'CollectorImpl',667);var zD=Xdb(tub,'Collectors/20methodref$add$Type',256);var AD=Xdb(tub,'Collectors/21methodref$ctor$Type',255);var BD=Xdb(tub,'Collectors/lambda$42$Type',257);var TD=Xdb(tub,'TerminatableStream',171);var SD=Xdb(tub,'StreamImpl',79);var ND=Xdb(tub,'StreamImpl/MapToObjSpliterator',533);var MD=Xdb(tub,'StreamImpl/MapToObjSpliterator/lambda$0$Type',538);var OD=Xdb(tub,'StreamImpl/ValueConsumer',537);var PD=Xdb(tub,'StreamImpl/lambda$0$Type',542);var QD=Xdb(tub,'StreamImpl/lambda$4$Type',543);var RD=Xdb(tub,'StreamImpl/lambda$5$Type',544);GE(133,1,{});_.bd=function X(a){return (1+$wnd.Math.cos(uub+a*uub))/2};_.cd=function Y(){this.u&&this.dd()};_.dd=function Z(){this.fd(this.bd(1))};_.ed=function $(){this.fd(this.bd(0))};_.k=-1;_.o=false;_.p=false;_.r=-1;_.t=-1;_.u=false;var rr=Xdb(vub,'Animation',133);GE(330,1,{},bb);_.gd=function cb(a){ab(this,a)};var jr=Xdb(vub,'Animation/1',330);GE(714,1,{});var db;var qr=Xdb(vub,'AnimationScheduler',714);GE(175,1,{175:1});var kr=Xdb(vub,'AnimationScheduler/AnimationHandle',175);GE(242,714,{},fb);_.hd=function hb(a,b){var c;c=ib(a,b);return new jb(c)};var mr=Xdb(vub,'AnimationSchedulerImplStandard',242);GE(580,175,{175:1},jb);_.jd=function kb(){gb(this.a)};var lr=Xdb(vub,'AnimationSchedulerImplStandard/1',580);GE(243,714,{},nb);_.hd=function ob(a,b){var c;c=new Bb(this,a);Nkb(this.a,c);this.a.a.length==1&&qb(this.b,16);return c};var pr=Xdb(vub,'AnimationSchedulerImplTimer',243);GE(45,1,{});_.kd=function wb(a){if(a!=this.g){return}this.i||(this.j=null);this.ld()};_.g=0;_.i=false;_.j=null;var dv=Xdb(wub,'Timer',45);GE(581,45,{},zb);_.ld=function Ab(){mb(this.a)};var nr=Xdb(vub,'AnimationSchedulerImplTimer/1',581);GE(176,175,{175:1,176:1},Bb);_.jd=function Cb(){lb(this.b,this)};var or=Xdb(vub,'AnimationSchedulerImplTimer/AnimationHandleImpl',176);GE(8,1,{});var ks=Xdb(xub,'RoleImpl',8);GE(586,8,{},Gb);var sr=Xdb(xub,'AlertRoleImpl',586);GE(585,8,{},Hb);var tr=Xdb(xub,'AlertdialogRoleImpl',585);GE(587,8,{},Ib);var ur=Xdb(xub,'ApplicationRoleImpl',587);GE(249,1,{});var xr=Xdb(xub,'Attribute',249);GE(57,249,{},Mb);_.md=function Nb(a){return a.a};var vr=Xdb(xub,'AriaValueAttribute',57);GE(588,8,{},Ob);var wr=Xdb(xub,'ArticleRoleImpl',588);GE(589,8,{},Pb);var yr=Xdb(xub,'BannerRoleImpl',589);GE(590,8,{},Qb);var zr=Xdb(xub,'ButtonRoleImpl',590);GE(591,8,{},Rb);var Ar=Xdb(xub,'CheckboxRoleImpl',591);GE(592,8,{},Sb);var Br=Xdb(xub,'ColumnheaderRoleImpl',592);GE(593,8,{},Tb);var Cr=Xdb(xub,'ComboboxRoleImpl',593);GE(594,8,{},Ub);var Dr=Xdb(xub,'ComplementaryRoleImpl',594);GE(595,8,{},Vb);var Er=Xdb(xub,'ContentinfoRoleImpl',595);GE(596,8,{},Wb);var Fr=Xdb(xub,'DefinitionRoleImpl',596);GE(597,8,{},Xb);var Gr=Xdb(xub,'DialogRoleImpl',597);GE(598,8,{},Yb);var Hr=Xdb(xub,'DirectoryRoleImpl',598);GE(599,8,{},Zb);var Ir=Xdb(xub,'DocumentRoleImpl',599);GE(600,8,{},$b);var Jr=Xdb(xub,'FormRoleImpl',600);GE(602,8,{},_b);var Kr=Xdb(xub,'GridRoleImpl',602);GE(601,8,{},ac);var Lr=Xdb(xub,'GridcellRoleImpl',601);GE(603,8,{},bc);var Mr=Xdb(xub,'GroupRoleImpl',603);GE(604,8,{},cc);var Nr=Xdb(xub,'HeadingRoleImpl',604);GE(177,1,{745:1,177:1},ec);var Or=Xdb(xub,'Id',177);GE(605,8,{},fc);var Pr=Xdb(xub,'ImgRoleImpl',605);GE(606,8,{},gc);var Qr=Xdb(xub,'LinkRoleImpl',606);GE(609,8,{},hc);var Rr=Xdb(xub,'ListRoleImpl',609);GE(607,8,{},ic);var Sr=Xdb(xub,'ListboxRoleImpl',607);GE(608,8,{},jc);var Tr=Xdb(xub,'ListitemRoleImpl',608);GE(610,8,{},kc);var Ur=Xdb(xub,'LogRoleImpl',610);GE(611,8,{},lc);var Vr=Xdb(xub,'MainRoleImpl',611);GE(612,8,{},mc);var Wr=Xdb(xub,'MarqueeRoleImpl',612);GE(613,8,{},nc);var Xr=Xdb(xub,'MathRoleImpl',613);GE(618,8,{},oc);var Yr=Xdb(xub,'MenuRoleImpl',618);GE(614,8,{},qc);var Zr=Xdb(xub,'MenubarRoleImpl',614);GE(617,8,{},rc);var $r=Xdb(xub,'MenuitemRoleImpl',617);GE(615,8,{},sc);var _r=Xdb(xub,'MenuitemcheckboxRoleImpl',615);GE(616,8,{},tc);var as=Xdb(xub,'MenuitemradioRoleImpl',616);GE(619,8,{},uc);var bs=Xdb(xub,'NavigationRoleImpl',619);GE(620,8,{},vc);var cs=Xdb(xub,'NoteRoleImpl',620);GE(621,8,{},wc);var ds=Xdb(xub,'OptionRoleImpl',621);GE(622,8,{},xc);var es=Xdb(xub,'PresentationRoleImpl',622);GE(47,249,{},yc);_.md=function zc(a){return a==null?Xtb:KE(a)};var fs=Xdb(xub,'PrimitiveValueAttribute',47);GE(623,8,{},Ac);var gs=Xdb(xub,'ProgressbarRoleImpl',623);var Bc,Cc;GE(625,8,{},Ec);var hs=Xdb(xub,'RadioRoleImpl',625);GE(624,8,{},Fc);var is=Xdb(xub,'RadiogroupRoleImpl',624);GE(626,8,{},Gc);var js=Xdb(xub,'RegionRoleImpl',626);var Hc,Ic,Jc,Kc,Lc,Mc,Nc,Oc,Pc,Qc,Rc,Sc,Tc,Uc,Vc,Wc,Xc,Yc,Zc,$c,_c,ad,bd,cd,dd,ed,fd,gd,hd,jd,kd,ld,md,nd,od,pd,qd,rd,sd,td,ud,vd,wd,xd,yd,zd,Ad,Bd,Cd,Dd,Ed,Fd,Gd,Hd,Id,Jd,Kd,Ld,Md,Nd,Od,Pd;GE(629,8,{},Rd);var ls=Xdb(xub,'RowRoleImpl',629);GE(627,8,{},Sd);var ms=Xdb(xub,'RowgroupRoleImpl',627);GE(628,8,{},Td);var ns=Xdb(xub,'RowheaderRoleImpl',628);GE(630,8,{},Ud);var os=Xdb(xub,'ScrollbarRoleImpl',630);GE(631,8,{},Vd);var ps=Xdb(xub,'SearchRoleImpl',631);GE(632,8,{},Wd);var qs=Xdb(xub,'SeparatorRoleImpl',632);GE(633,8,{},Xd);var rs=Xdb(xub,'SliderRoleImpl',633);GE(634,8,{},Yd);var ss=Xdb(xub,'SpinbuttonRoleImpl',634);GE(635,8,{},Zd);var ts=Xdb(xub,'StatusRoleImpl',635);GE(638,8,{},$d);var us=Xdb(xub,'TabRoleImpl',638);GE(636,8,{},_d);var vs=Xdb(xub,'TablistRoleImpl',636);GE(637,8,{},ae);var ws=Xdb(xub,'TabpanelRoleImpl',637);GE(639,8,{},be);var xs=Xdb(xub,'TextboxRoleImpl',639);GE(640,8,{},ce);var ys=Xdb(xub,'TimerRoleImpl',640);GE(641,8,{},de);var zs=Xdb(xub,'ToolbarRoleImpl',641);GE(642,8,{},ee);var As=Xdb(xub,'TooltipRoleImpl',642);GE(645,8,{},fe);var Bs=Xdb(xub,'TreeRoleImpl',645);GE(643,8,{},ge);var Cs=Xdb(xub,'TreegridRoleImpl',643);GE(644,8,{},he);var Ds=Xdb(xub,'TreeitemRoleImpl',644);GE(12,1,{16:1,12:1});_.nd=function xe(){return ke(this)};_.od=function ze(){throw aE(new Wfb)};_.pd=function Ae(a){qe(this,a)};_.qd=function Ce(a,b){se(this,a,b)};_.rd=function Fe(a){ve(this,a)};_.ad=function Ge(){if(!this.Yc){return '(null handle)'}return rh((JF(),this.Yc))};var bw=Xdb(Vub,'UIObject',12);GE(13,12,Wub);_.sd=function Se(){};_.td=function Te(){};_.ud=function Ue(a){Ke(this,a)};_.vd=function Ve(){return this.Uc};_.wd=function We(){Le(this)};_.xd=function Xe(a){Me(this,a)};_.yd=function Ye(){Ne(this)};_.zd=function Ze(){};_.Ad=function $e(){};_.Uc=false;_.Vc=0;var lw=Xdb(Vub,'Widget',13);var xv=Zdb(Vub,'Focusable');GE(211,13,Yub);_.wd=function ff(){var a;Le(this);a=WL((JF(),this.Yc));-1==a&&(this.Yc.tabIndex=0,undefined)};_.Bd=function gf(a){bf(this,a)};_.Cd=function hf(a){df(this,a)};var _e;var wv=Xdb(Vub,'FocusWidget',211);GE(664,211,Yub,kf);var jf;var Gs=Xdb(Zub,'Canvas',664);GE(720,1,{});var Fs=Xdb(Zub,'Canvas/CanvasElementSupportDetector',720);GE(665,720,{},mf);var Es=Xdb(Zub,'Canvas/CanvasElementSupportDetectedMaybe',665);GE(213,1,{},pf);_.a=0;var Hs=Xdb(pub,'Duration',213);var qf=null;GE(355,103,Utb);var Ms=Xdb(qub,'JavaScriptExceptionBase',355);GE(82,355,{82:1,3:1,21:1,24:1,20:1},Rf);_.Hd=function Sf(){Qf(this);return this.c};_.Jd=function Tf(){return dr(this.b)===dr(Of)?null:this.b};var Of;var Is=Xdb(pub,'JavaScriptException',82);var Ks=Zdb(pub,'RunAsyncCallback');GE(675,1,{});var Ls=Xdb(pub,'Scheduler',675);var Yf=0,Zf=false,$f,_f=0,ag=-1;GE(356,675,{});_.e=false;_.j=false;var ng;var Ps=Xdb(qub,'SchedulerImpl',356);GE(357,1,{},Bg);_.Kd=function Cg(){this.a.e=true;rg(this.a);this.a.e=false;return this.a.j=sg(this.a)};var Ns=Xdb(qub,'SchedulerImpl/Flusher',357);GE(358,1,{},Dg);_.Kd=function Eg(){this.a.e&&Ag(this.a.f,1);return this.a.j};var Os=Xdb(qub,'SchedulerImpl/Rescuer',358);GE(116,1,_ub);_.Sd=function Qh(a){return a.button|0};_.Ud=function Rh(a){return a.currentTarget};_.Yd=function Sh(a){return ai(Nh(a))};_.Zd=function Th(a){return ai(Oh(a))};_.$d=function Uh(a){return 0};_._d=function Vh(a){return 0};_.ae=function Wh(a){return jfb(a.compatMode,avb)?a.documentElement:a.body};_.be=function Xh(a){var b='',c=a.firstChild;while(c){c.nodeType==1?(b+=this.be(c)):c.nodeValue&&(b+=c.nodeValue);c=c.nextSibling}return b};_.ce=function Yh(a){return ai(a.scrollLeft||0)};_.de=function Zh(a){return a.tabIndex};_.fe=function $h(a,b){while(a.firstChild){a.removeChild(a.firstChild)}b!=null&&a.appendChild(a.ownerDocument.createTextNode(b))};_.ge=function _h(a,b){a.scrollLeft=b};_.he=function bi(a){return a.outerHTML};var Fh;var Ys=Xdb(bvb,'DOMImpl',116);GE(706,116,_ub);_.Pd=function ci(a,b,c,d){var e=a.createEvent('HTMLEvents');e.initEvent(b,c,d);return e};_.Qd=function di(a,b,c,d,e,f,g,h,i,j,k,l,m,n,o){n==1?(n=0):n==4?(n=1):(n=2);var p=a.createEvent(cvb);p.initMouseEvent(b,c,d,null,e,f,g,h,i,j,k,l,m,n,o);return p};_.Rd=function ei(a,b){a.dispatchEvent(b)};_.Sd=function fi(a){var b=a.button;if(b==1){return 4}else if(b==2){return 2}return 1};_.Td=function gi(a){return a.charCode||0};_.Vd=function hi(a){return a.relatedTarget};_.Wd=function ii(a){return a.target};_.Xd=function ji(a){a.preventDefault()};_.ae=function ki(a){if(a.scrollingElement){return a.scrollingElement}return this.ie(a)};_.be=function li(a){return a.textContent};_.ie=function mi(a){return jfb(a.compatMode,avb)?a.documentElement:a.body};_.ee=function ni(a,b){return a.contains(b)};_.fe=function oi(a,b){a.textContent=b||''};var Ws=Xdb(bvb,dvb,706);GE(480,706,_ub,ti);_.Vd=function ui(b){var c=b.relatedTarget;if(!c){return null}try{var d=c.nodeName;return c}catch(a){return null}};_.Yd=function vi(a){return qi(rj(a.ownerDocument),a)};_.Zd=function wi(a){return ri(rj(a.ownerDocument),a)};_.$d=function xi(a){var b=$wnd.getComputedStyle(a.documentElement,null);if(b==null){return 0}return parseInt(b.marginLeft,10)+parseInt(b.borderLeftWidth,10)};_._d=function yi(a){var b=$wnd.getComputedStyle(a.documentElement,null);if(b==null){return 0}return parseInt(b.marginTop,10)+parseInt(b.borderTopWidth,10)};_.ce=function Ai(a){var b;b=(pi==-2&&(pi=zi()),pi);if(!(b!=-1&&b>=1009000)&&si(a)){return ai(a.scrollLeft||0)-(((a.scrollWidth||0)|0)-(a.clientWidth|0))}return ai(a.scrollLeft||0)};_.ee=function Bi(a,b){return a===b||!!(a.compareDocumentPosition(b)&16)};_.ge=function Ci(a,b){var c;c=(pi==-2&&(pi=zi()),pi);!(c!=-1&&c>=1009000)&&si(a)&&(b+=((a.scrollWidth||0)|0)-(a.clientWidth|0));a.scrollLeft=b};_.he=function Di(a){var b=a.ownerDocument;var c=a.cloneNode(true);var d=b.createElement('DIV');d.appendChild(c);outer=d.innerHTML;c.innerHTML='';return outer};var pi=-2;var Us=Xdb(bvb,fvb,480);GE(707,706,_ub);_.Ud=function Fi(a){return a.currentTarget||$wnd};_.Yd=function Gi(a){var b,c;c=a.getBoundingClientRect&&a.getBoundingClientRect();b=c?c.left+Lh(this,a.ownerDocument):Hi(a);return Gh(),b|0};_.Zd=function Ii(a){var b,c,d;b=a.getBoundingClientRect&&a.getBoundingClientRect();c=b?b.top+Mh(this,a.ownerDocument):Ji(a);return Gh(),c|0};_.ce=function Ki(a){if(!kfb('body',(Gh(),a).tagName)&&Ei(a)){return ai(a.scrollLeft||0)-(((a.scrollWidth||0)|0)-(a.clientWidth|0))}return ai(a.scrollLeft||0)};_.de=function Li(a){return typeof a.tabIndex!='undefined'?a.tabIndex:-1};_.ge=function Mi(a,b){!kfb('body',(Gh(),a).tagName)&&Ei(a)&&(b+=((a.scrollWidth||0)|0)-(a.clientWidth|0));a.scrollLeft=b};var Vs=Xdb(bvb,jvb,707);GE(479,707,_ub,Ni);_.Wd=function Oi(a){var b=a.target;b&&b.nodeType==3&&(b=b.parentNode);return b};_.ie=function Pi(a){return a.body};var Xs=Xdb(bvb,kvb,479);GE(55,4,qvb);var Wj,Xj,Yj,Zj,$j;var ct=Ydb(bvb,'Style/BorderStyle',55,bk);GE(432,55,qvb,ck);var Zs=Ydb(bvb,'Style/BorderStyle/1',432,null);GE(433,55,qvb,dk);var $s=Ydb(bvb,'Style/BorderStyle/2',433,null);GE(434,55,qvb,ek);var _s=Ydb(bvb,'Style/BorderStyle/3',434,null);GE(435,55,qvb,fk);var at=Ydb(bvb,'Style/BorderStyle/4',435,null);GE(436,55,qvb,gk);var bt=Ydb(bvb,'Style/BorderStyle/5',436,null);GE(23,4,svb);var hk,ik,jk,kk,lk,mk,nk,ok,pk,qk,rk,sk,tk,uk,vk,wk,xk,yk,zk;var wt=Ydb(bvb,'Style/Display',23,Ck);GE(437,23,svb,Dk);_.ke=function Ek(){return Pub};var nt=Ydb(bvb,'Style/Display/1',437,null);GE(446,23,svb,Fk);_.ke=function Gk(){return 'table-column-group'};var dt=Ydb(bvb,'Style/Display/10',446,null);GE(447,23,svb,Hk);_.ke=function Ik(){return 'table-header-group'};var et=Ydb(bvb,'Style/Display/11',447,null);GE(448,23,svb,Jk);_.ke=function Kk(){return 'table-footer-group'};var ft=Ydb(bvb,'Style/Display/12',448,null);GE(449,23,svb,Lk);_.ke=function Mk(){return 'table-row-group'};var gt=Ydb(bvb,'Style/Display/13',449,null);GE(450,23,svb,Nk);_.ke=function Ok(){return 'table-cell'};var ht=Ydb(bvb,'Style/Display/14',450,null);GE(451,23,svb,Pk);_.ke=function Qk(){return 'table-column'};var it=Ydb(bvb,'Style/Display/15',451,null);GE(452,23,svb,Rk);_.ke=function Sk(){return 'table-row'};var jt=Ydb(bvb,'Style/Display/16',452,null);GE(453,23,svb,Tk);_.ke=function Uk(){return 'initial'};var kt=Ydb(bvb,'Style/Display/17',453,null);GE(454,23,svb,Vk);_.ke=function Wk(){return 'flex'};var lt=Ydb(bvb,'Style/Display/18',454,null);GE(455,23,svb,Xk);_.ke=function Yk(){return 'inline-flex'};var mt=Ydb(bvb,'Style/Display/19',455,null);GE(438,23,svb,Zk);_.ke=function $k(){return tvb};var ot=Ydb(bvb,'Style/Display/2',438,null);GE(439,23,svb,_k);_.ke=function al(){return 'inline'};var pt=Ydb(bvb,'Style/Display/3',439,null);GE(440,23,svb,bl);_.ke=function cl(){return 'inline-block'};var qt=Ydb(bvb,'Style/Display/4',440,null);GE(441,23,svb,dl);_.ke=function el(){return 'inline-table'};var rt=Ydb(bvb,'Style/Display/5',441,null);GE(442,23,svb,fl);_.ke=function gl(){return 'list-item'};var st=Ydb(bvb,'Style/Display/6',442,null);GE(443,23,svb,hl);_.ke=function il(){return 'run-in'};var tt=Ydb(bvb,'Style/Display/7',443,null);GE(444,23,svb,jl);_.ke=function kl(){return 'table'};var ut=Ydb(bvb,'Style/Display/8',444,null);GE(445,23,svb,ll);_.ke=function ml(){return 'table-caption'};var vt=Ydb(bvb,'Style/Display/9',445,null);GE(62,4,uvb);var nl,ol,pl,ql;var Bt=Ydb(bvb,'Style/Overflow',62,tl);GE(456,62,uvb,ul);var xt=Ydb(bvb,'Style/Overflow/1',456,null);GE(457,62,uvb,vl);var yt=Ydb(bvb,'Style/Overflow/2',457,null);GE(458,62,uvb,wl);var zt=Ydb(bvb,'Style/Overflow/3',458,null);GE(459,62,uvb,xl);var At=Ydb(bvb,'Style/Overflow/4',459,null);GE(63,4,vvb);var yl,zl,Al,Bl;var Gt=Ydb(bvb,'Style/Position',63,El);GE(460,63,vvb,Fl);var Ct=Ydb(bvb,'Style/Position/1',460,null);GE(461,63,vvb,Gl);var Dt=Ydb(bvb,'Style/Position/2',461,null);GE(462,63,vvb,Hl);var Et=Ydb(bvb,'Style/Position/3',462,null);GE(463,63,vvb,Il);var Ft=Ydb(bvb,'Style/Position/4',463,null);GE(64,4,wvb);var Jl,Kl,Ll,Ml;var Lt=Ydb(bvb,'Style/TextAlign',64,Pl);GE(464,64,wvb,Ql);var Ht=Ydb(bvb,'Style/TextAlign/1',464,null);GE(465,64,wvb,Rl);var It=Ydb(bvb,'Style/TextAlign/2',465,null);GE(466,64,wvb,Sl);var Jt=Ydb(bvb,'Style/TextAlign/3',466,null);GE(467,64,wvb,Tl);var Kt=Ydb(bvb,'Style/TextAlign/4',467,null);GE(95,4,xvb);var Ul,Vl;var Ot=Ydb(bvb,'Style/TextOverflow',95,Yl);GE(468,95,xvb,Zl);var Mt=Ydb(bvb,'Style/TextOverflow/1',468,null);GE(469,95,xvb,$l);var Nt=Ydb(bvb,'Style/TextOverflow/2',469,null);GE(34,4,yvb);var _l,am,bm,cm,dm,em,fm,gm,hm;var Yt=Ydb(bvb,'Style/Unit',34,km);GE(423,34,yvb,lm);var Pt=Ydb(bvb,'Style/Unit/1',423,null);GE(424,34,yvb,mm);var Qt=Ydb(bvb,'Style/Unit/2',424,null);GE(425,34,yvb,nm);var Rt=Ydb(bvb,'Style/Unit/3',425,null);GE(426,34,yvb,om);var St=Ydb(bvb,'Style/Unit/4',426,null);GE(427,34,yvb,pm);var Tt=Ydb(bvb,'Style/Unit/5',427,null);GE(428,34,yvb,qm);var Ut=Ydb(bvb,'Style/Unit/6',428,null);GE(429,34,yvb,rm);var Vt=Ydb(bvb,'Style/Unit/7',429,null);GE(430,34,yvb,sm);var Wt=Ydb(bvb,'Style/Unit/8',430,null);GE(431,34,yvb,tm);var Xt=Ydb(bvb,'Style/Unit/9',431,null);GE(96,4,zvb);var um,vm;var _t=Ydb(bvb,'Style/Visibility',96,ym);GE(470,96,zvb,zm);_.ke=function Am(){return Avb};var Zt=Ydb(bvb,'Style/Visibility/1',470,null);GE(471,96,zvb,Bm);_.ke=function Cm(){return Bvb};var $t=Ydb(bvb,'Style/Visibility/2',471,null);GE(56,4,Cvb);var Dm,Em,Fm,Gm,Hm;var fu=Ydb(bvb,'Style/WhiteSpace',56,Km);GE(472,56,Cvb,Lm);var au=Ydb(bvb,'Style/WhiteSpace/1',472,null);GE(473,56,Cvb,Mm);var bu=Ydb(bvb,'Style/WhiteSpace/2',473,null);GE(474,56,Cvb,Nm);var cu=Ydb(bvb,'Style/WhiteSpace/3',474,null);GE(475,56,Cvb,Om);var du=Ydb(bvb,'Style/WhiteSpace/4',475,null);GE(476,56,Cvb,Pm);var eu=Ydb(bvb,'Style/WhiteSpace/5',476,null);GE(689,1,{});_.ad=function Um(){return 'An event type'};var uw=Xdb(Dvb,'Event',689);GE(690,689,{});_.ne=function Wm(){return this.me()};_.oe=function Xm(){this.e=false;this.f=null};_.e=false;var Iu=Xdb(Evb,'GwtEvent',690);GE(701,690,{});_.me=function an(){return this.pe()};_.ne=function bn(){return this.pe()};var Ym;var ku=Xdb(Fvb,'DomEvent',701);GE(551,701,{},en);_.le=function fn(a){a.qe(this)};_.me=function hn(){return cn};_.ne=function jn(){return cn};_.pe=function gn(){return cn};var cn;var gu=Xdb(Fvb,'BlurEvent',551);GE(702,701,{});var mu=Xdb(Fvb,'HumanInputEvent',702);GE(703,702,{});var uu=Xdb(Fvb,'MouseEvent',703);GE(422,703,{},mn);_.le=function nn(a){a.re(this)};_.me=function pn(){return kn};_.ne=function qn(){return kn};_.pe=function on(){return kn};var kn;var hu=Xdb(Fvb,'ClickEvent',422);GE(486,701,{},tn);_.le=function un(a){a.se(this)};_.me=function wn(){return rn};_.ne=function xn(){return rn};_.pe=function vn(){return rn};var rn;var iu=Xdb(Fvb,'ContextMenuEvent',486);GE(297,1,{});_._c=function zn(){return this.c};_.ad=function An(){return 'Event type'};_.c=0;var yn=0;var sw=Xdb(Dvb,'Event/Type',297);GE(51,297,{},Bn);var Hu=Xdb(Evb,'GwtEvent/Type',51);GE(42,51,{42:1},Cn);var ju=Xdb(Fvb,'DomEvent/Type',42);GE(550,701,{},Fn);_.le=function Gn(a){mT(a,this)};_.me=function In(){return Dn};_.ne=function Jn(){return Dn};_.pe=function Hn(){return Dn};var Dn;var lu=Xdb(Fvb,'FocusEvent',550);GE(712,701,{});var pu=Xdb(Fvb,'KeyEvent',712);GE(713,712,{});var nu=Xdb(Fvb,'KeyCodeEvent',713);GE(549,713,{},Mn);_.le=function Nn(a){nT(a,this)};_.me=function Pn(){return Kn};_.ne=function Qn(){return Kn};_.pe=function On(){return Kn};var Kn;var ou=Xdb(Fvb,'KeyDownEvent',549);GE(658,712,{},Tn);_.le=function Un(a){oT(a,this)};_.me=function Wn(){return Rn};_.ne=function Xn(){return Rn};_.pe=function Vn(){return Rn};var Rn;var qu=Xdb(Fvb,'KeyPressEvent',658);GE(661,713,{},$n);_.le=function _n(a){a.te(this)};_.me=function bo(){return Yn};_.ne=function co(){return Yn};_.pe=function ao(){return Yn};var Yn;var ru=Xdb(Fvb,'KeyUpEvent',661);GE(660,701,{},go);_.le=function ho(a){g6(a.a.b)};_.me=function jo(){return eo};_.ne=function ko(){return eo};_.pe=function io(){return eo};var eo;var su=Xdb(Fvb,'LoadEvent',660);GE(548,703,{},no);_.le=function oo(a){a.ue(this)};_.me=function qo(){return lo};_.ne=function ro(){return lo};_.pe=function po(){return lo};var lo;var tu=Xdb(Fvb,'MouseDownEvent',548);GE(659,703,{},uo);_.le=function vo(a){a.ve(this)};_.me=function xo(){return so};_.ne=function yo(){return so};_.pe=function wo(){return so};var so;var vu=Xdb(Fvb,'MouseUpEvent',659);GE(561,1,{},Bo);var wu=Xdb(Fvb,'PrivateMap',561);GE(717,702,{});var Co;var Au=Xdb(Fvb,Hvb,717);GE(657,717,{},Fo);_.le=function Go(a){!!a.b&&pb(a.b)};_.me=function Io(){return Do};_.ne=function Jo(){return Do};_.pe=function Ho(){return Do};var Do;var xu=Xdb(Fvb,'TouchCancelEvent',657);GE(655,717,{},Mo);_.le=function No(a){a.we(this)};_.me=function Po(){return Ko};_.ne=function Qo(){return Ko};_.pe=function Oo(){return Ko};var Ko;var yu=Xdb(Fvb,'TouchEndEvent',655);GE(560,1,{},Ro);_.a=false;var zu=Xdb(Fvb,'TouchEvent/TouchSupportDetector',560);GE(656,717,{},Uo);_.le=function Vo(a){a.xe(this)};_.me=function Xo(){return So};_.ne=function Yo(){return So};_.pe=function Wo(){return So};var So;var Bu=Xdb(Fvb,'TouchMoveEvent',656);GE(654,717,{},_o);_.le=function ap(a){a.ye(this)};_.me=function cp(){return Zo};_.ne=function dp(){return Zo};_.pe=function bp(){return Zo};var Zo;var Cu=Xdb(Fvb,'TouchStartEvent',654);GE(308,690,{},fp);_.le=function gp(a){a.ze(this)};_.ne=function jp(){return ep};_.me=function ip(){return ep};_.a=false;var ep;var Du=Xdb(Lvb,'AttachEvent',308);GE(552,690,{},lp);_.le=function mp(a){a.Ae(this)};_.ne=function pp(){return kp};_.me=function op(){return kp};var kp;var Eu=Xdb(Lvb,'CloseEvent',552);GE(565,690,{},rp);_.le=function sp(a){a.Be(this)};_.ne=function vp(){return qp};_.me=function up(){return qp};var qp;var Fu=Xdb(Lvb,'ResizeEvent',565);GE(584,690,{},xp);_.le=function yp(a){a.a.v&&a.a.Ue()};_.ne=function Bp(){return wp};_.me=function Ap(){return wp};var wp;var Gu=Xdb(Lvb,'ValueChangeEvent',584);GE(76,1,{14:1},Fp,Gp);_.ud=function Hp(a){Dp(this,a)};var Ku=Xdb(Evb,'HandlerManager',76);GE(699,1,{});var tw=Xdb(Dvb,'EventBus',699);GE(361,699,{});_.b=0;_.c=false;var yw=Xdb(Dvb,'SimpleEventBus',361);GE(362,361,{},Sp);var Ju=Xdb(Evb,'HandlerManager/Bus',362);GE(557,1,{},Tp);var Lu=Xdb(Evb,'LegacyHandlerWrapper',557);GE(90,24,Mvb,Up);var zw=Xdb(Dvb,Nvb,90);GE(198,90,Mvb,Wp);var Mu=Xdb(Evb,Nvb,198);GE(110,4,{110:1,3:1,6:1,4:1},cq);var $p,_p,aq;var Nu=Ydb('com.google.gwt.i18n.client','HasDirection/Direction',110,dq);var lq;var Pq,Qq,Rq,Sq;GE(111,1,{111:1});var jD=Xdb(Rvb,'Handler',111);GE(268,111,{111:1},QE);_.Ce=function RE(a){var b,c;if(!window.console||(NE(this),Ytb>a.a.Xf())){return}b=aF(this.a,a);c=a.a.Xf();c>=(Kqb(),evb)?(window.console.error(b),undefined):c>=900?(window.console.warn(b),undefined):c>=800?(window.console.info(b),undefined):(window.console.log(b),undefined)};var Ou=Xdb(Svb,'ConsoleLogHandler',268);GE(269,111,{111:1},SE);_.Ce=function TE(a){return};var Pu=Xdb(Svb,'DevelopmentModeLogHandler',269);var UE;var Su=Xdb(Svb,'LogConfiguration',null);GE(267,1,{},XE);var Qu=Xdb(Svb,'LogConfiguration/1',267);GE(266,1,{},_E);var Ru=Xdb(Svb,'LogConfiguration/LogConfigurationImplRegular',266);GE(715,1,{});var iD=Xdb(Rvb,'Formatter',715);GE(716,715,{});var Uu=Xdb(Tvb,'FormatterImpl',716);GE(246,716,{},bF);_.a=false;var Tu=Xdb(Svb,'TextLogFormatter',246);GE(677,1,{});var qB=Xdb(Uvb,'OutputStream',677);GE(192,677,{},cF);var pB=Xdb(Uvb,'FilterOutputStream',192);GE(155,192,{},dF);_.De=function eF(a){};var rB=Xdb(Uvb,'PrintStream',155);GE(648,155,{},fF);_.De=function gF(a){Kfb(this.a,a);Kfb(this.a,'\n')};var Vu=Xdb(Tvb,'StackTracePrintStream',648);GE(571,1,{},kF);var Wu=Xdb(Vvb,'SafeHtmlBuilder',571);GE(148,1,{740:1,148:1,3:1},lF);_.Zc=function mF(a){if(!Zq(a,148)){return false}return jfb(this.a,a.a)};_._c=function nF(){return Dtb(this.a)};_.ad=function oF(){return 'safe: "'+this.a+'"'};var Xu=Xdb(Vvb,'SafeHtmlString',148);var pF,qF,rF,sF,tF,uF;GE(121,1,{744:1,121:1},xF);_.Zc=function yF(a){if(!Zq(a,121)){return false}return jfb(this.a,a.a)};_._c=function zF(){return Dtb(this.a)};_.ad=function AF(){return 'safe: "'+this.a+'"'};var Yu=Xdb(Vvb,'SafeUriString',121);GE(719,1,{});var Zu=Xdb('com.google.gwt.text.shared','AbstractRenderer',719);GE(663,1,{},DF);var CF;var $u=Xdb(Wvb,'PassthroughParser',663);GE(662,719,{},FF);var EF;var _u=Xdb(Wvb,'PassthroughRenderer',662);var GF=null,HF,IF;var YF;GE(331,690,{},iG);_.le=function jG(a){a.Ee(this);fG.c=false};_.ne=function mG(){return eG};_.me=function lG(){return eG};_.oe=function nG(){gG(this)};_.a=false;_.b=false;_.c=false;var eG,fG;var av=Xdb(wub,'Event/NativePreviewEvent',331);var oG,pG;GE(555,1,{14:1},vG);_.ud=function wG(a){Dp(this.a,a)};var bv=Xdb(wub,'History/HistoryEventSource',555);GE(556,1,{},xG);var cv=Xdb(wub,'History/HistoryImpl',556);var zG=false,AG,BG,CG=0,DG=0,EG=false;GE(360,690,{},PG);_.le=function QG(a){null.tg()};_.ne=function SG(){return NG};_.me=function RG(){return NG};var NG;var ev=Xdb(wub,'Window/ClosingEvent',360);var TG='',UG;GE(161,76,{14:1},YG);var fv=Xdb(wub,'Window/WindowHandlers',161);GE(115,1,fwb);var ZG=false;var kv=Xdb(gwb,'DOMImpl',115);GE(704,115,fwb);_.Fe=function pH(a,b){var c=0,d=a.firstChild;while(d){if(d.nodeType==1){if(b==c)return d;++c}d=d.nextSibling}return null};_.Ge=function qH(a){var b=0,c=a.firstChild;while(c){c.nodeType==1&&++b;c=c.nextSibling}return b};_.He=function sH(){iH()};_.Ie=function tH(a,b,c){var d=0,e=a.firstChild,f=null;while(e){if(e.nodeType==1){if(d==c){f=e;break}++d}e=e.nextSibling}a.insertBefore(b,f)};_.Je=function uH(a){_G(this);dH==a&&(dH=null)};_.Ke=function vH(a){_G(this);dH=a};_.Le=function wH(a,b){var c,d;_G(this);c=cH;d=c[b]||c['_default_'];a.addEventListener(b,d,false)};_.Me=function xH(a,b){_G(this);jH(a,b)};var cH,dH,eH,fH,gH;var iv=Xdb(gwb,dvb,704);GE(477,704,fwb,AH);_.He=function BH(){iH();zH()};_.Me=function CH(a,b){_G(this);jH(a,b);b&Zvb&&a.addEventListener(Yvb,(hH(),fH),false)};var gv=Xdb(gwb,fvb,477);GE(705,704,fwb);var hv=Xdb(gwb,jvb,705);GE(478,705,fwb,DH);var jv=Xdb(gwb,kvb,478);GE(172,1,{172:1},HH);_.Ne=function IH(){return $wnd.location.hash};var mv=Xdb(gwb,'WindowImpl',172);GE(558,172,{172:1},JH);_.Ne=function KH(){var a=$wnd.location.href;var b=a.indexOf('#');return b>0?a.substring(b):''};var lv=Xdb(gwb,'WindowImplMozilla',558);GE(688,13,iwb);_.sd=function MH(){aI(this,(ZH(),XH))};_.td=function NH(){aI(this,(ZH(),YH))};var Nv=Xdb(Vub,'Panel',688);GE(156,688,iwb);_.Oe=function RH(){return new NL(this.o)};_.Pe=function SH(a){return PH(this,a)};var sv=Xdb(Vub,'ComplexPanel',156);GE(353,156,iwb);_.Pe=function WH(a){return UH(this,a)};var nv=Xdb(Vub,'AbsolutePanel',353);GE(280,198,Mvb,_H);var XH,YH;var qv=Xdb(Vub,'AttachDetachException',280);GE(281,1,{},bI);_.Qe=function cI(a){a.wd()};var ov=Xdb(Vub,'AttachDetachException/1',281);GE(282,1,{},dI);_.Qe=function eI(a){a.yd()};var pv=Xdb(Vub,'AttachDetachException/2',282);GE(481,156,iwb);var rv=Xdb(Vub,'CellPanel',481);GE(692,13,lwb);_.vd=function lI(){return kI(this)};_.wd=function mI(){iI(this);if(this.Vc!=-1){Re(this.bb,this.Vc);this.Vc=-1}this.bb.wd();JF();this.Yc.__listener=this;hp(this,true)};_.xd=function nI(a){Me(this,a);this.bb.xd(a)};_.yd=function oI(){try{hp(this,false)}finally{this.bb.yd()}};_.od=function pI(){oe(this,this.bb.od());return JF(),this.Yc};var tv=Xdb(Vub,'Composite',692);GE(647,1,{},sI);_.c=false;var uv=Xdb(Vub,'DirectionalTextHelper',647);GE(89,156,iwb,uI);var vv=Xdb(Vub,'FlowPanel',89);GE(132,688,iwb);_.Re=function AI(){return JF(),this.Yc};_.Oe=function BI(){return new aL(this)};_.Pe=function CI(a){return wI(this,a)};_.Se=function DI(a){xI(this,a)};var Zv=Xdb(Vub,'SimplePanel',132);var EI;var LI,MI,NI;GE(239,13,Wub);var Gv=Xdb(Vub,'LabelBase',239);GE(559,239,Wub);var Hv=Xdb(Vub,'Label',559);GE(145,559,Wub,JI,KI);var yv=Xdb(Vub,'HTML',145);var RI;GE(708,1,{});var zv=Xdb(Vub,'HasHorizontalAlignment/AutoHorizontalAlignmentConstant',708);GE(142,708,{},QI);var Av=Xdb(Vub,'HasHorizontalAlignment/HorizontalAlignmentConstant',142);GE(169,1,{},TI);var Bv=Xdb(Vub,'HasVerticalAlignment/VerticalAlignmentConstant',169);GE(244,13,Wub,XI);_.xd=function YI(a){JF();$G((Gh(),a).type)==Xvb&&!!this.a&&(this.Yc[hwb]='',undefined);Me(this,a)};_.zd=function ZI(){$I(this.a,this)};var Fv=Xdb(Vub,'Image',244);GE(582,1,{});_.a=null;var Dv=Xdb(Vub,'Image/State',582);GE(583,1,mwb,_I);_.Ld=function aJ(){var a;if(this.b.a!=this.a||this!=this.a.a){return}this.a.a=null;if(!this.b.Uc){bJ(this.b)[hwb]=lvb;return}a=Wi($doc);fh(bJ(this.b),a)};var Cv=Xdb(Vub,'Image/State/1',583);GE(245,582,{},cJ);var Ev=Xdb(Vub,'Image/UnclippedState',245);GE(527,211,Yub,mJ);var Iv=Xdb(Vub,'ListBox',527);GE(139,13,Wub,EJ);_.Te=function GJ(){(FI(),EI).bf((JF(),this.Yc))};_.xd=function HJ(a){var b,c;b=rJ(this,(JF(),(Gh(),Fh).Wd(a)));switch($G(a.type)){case 1:{(FI(),EI).bf(this.Yc);!!b&&qJ(this,b,true);break}case 16:{!!b&&uJ(this,b,true);break}case 32:{!!b&&uJ(this,null,false);break}case 2048:{zJ(this);break}case 128:{c=a.keyCode|0;c=c;switch(c){case 37:yJ(this);a.stopPropagation();Fh.Xd(a);break;case 39:xJ(this);a.stopPropagation();Fh.Xd(a);break;case 38:wJ(this);a.stopPropagation();Fh.Xd(a);break;case 40:vJ(this);a.stopPropagation();Fh.Xd(a);break;case 27:AJ(this,null);a.stopPropagation();Fh.Xd(a);break;case 9:AJ(this,null);break;case 13:if(!zJ(this)){qJ(this,this.g,true);a.stopPropagation();Fh.Xd(a)}}break}}Me(this,a)};_.yd=function IJ(){Ne(this)};_.c=false;_.e=true;_.i=false;var Lv=Xdb(Vub,'MenuBar',139);GE(407,1,mwb,JJ);_.Ld=function KJ(){this.a.Ld()};var Jv=Xdb(Vub,'MenuBar/1',407);GE(408,1,{728:1,27:1},LJ);_.qe=function MJ(a){AJ(this.a,null)};var Kv=Xdb(Vub,'MenuBar/2',408);GE(98,12,{16:1,98:1,12:1},PJ,QJ);_.Bd=function RJ(a){a?se(this,ye((JF(),this.Yc))+'-'+Xub,false):se(this,ye((JF(),this.Yc))+'-'+Xub,true);this.b=a};_.b=true;var Mv=Xdb(Vub,'MenuItem',98);GE(109,132,iwb);_.Re=function iK(){return SJ.df(NF((JF(),this.Yc)))};_.nd=function jK(){return SJ.ef((JF(),JF(),mh(this.Yc)))};_.Ue=function kK(){this.Ve(false)};_.Ve=function lK(a){XJ(this)};_.Ad=function mK(){this.M&&BK(this.L,false,true)};_.pd=function nK(a){_J(this,a)};_.We=function oK(a,b){aK(this,a,b)};_.Se=function pK(a){cK(this,a)};_.rd=function qK(a){dK(this,a)};_.u=false;_.v=false;_.F=false;_.G=false;_.H=0;_.I=false;_.K=false;_.M=false;_.N=0;var SJ;var Tv=Xdb(Vub,'PopupPanel',109);GE(326,1,{724:1,27:1},sK);_.Be=function tK(a){rK()};var Ov=Xdb(Vub,'PopupPanel/1',326);GE(327,1,twb,uK);_.Ee=function vK(a){$J(this.a,a)};var Pv=Xdb(Vub,'PopupPanel/3',327);GE(328,1,{734:1,27:1},wK);var Qv=Xdb(Vub,'PopupPanel/4',328);GE(324,133,{},CK);_.dd=function DK(){yK(this)};_.ed=function EK(){this.d=VJ(this.a);this.e=WJ(this.a);ie(this.a).style[uwb]=Bvb;AK(this,(1+$wnd.Math.cos(uub))/2)};_.fd=function FK(a){AK(this,a)};_.a=null;_.b=false;_.c=false;_.d=0;_.e=-1;_.i=false;var Sv=Xdb(Vub,'PopupPanel/ResizeAnimation',324);GE(325,45,{},GK);_.ld=function HK(){this.a.g=null;T(this.a,200,Xf())};var Rv=Xdb(Vub,'PopupPanel/ResizeAnimation/1',325);GE(135,353,vwb,RK);var NK,OK,PK;var Xv=Xdb(Vub,'RootPanel',135);GE(354,1,{},WK);_.Qe=function XK(a){a.vd()&&a.yd()};var Uv=Xdb(Vub,'RootPanel/1',354);GE(215,1,wwb,YK);_.Ae=function ZK(a){TK()};var Vv=Xdb(Vub,'RootPanel/2',215);GE(214,135,vwb,$K);var Wv=Xdb(Vub,'RootPanel/DefaultRootPanel',214);GE(329,1,{},aL);_.Ze=function cL(){return _K(this)};_.Ye=function bL(){return this.a};_.$e=function dL(){!!this.b&&wI(this.c,this.b)};_.a=false;_.b=null;var Yv=Xdb(Vub,'SimplePanel/1',329);GE(528,211,Yub);_.xd=function kL(a){var b;b=(JF(),$G((Gh(),a).type));(b&896)!=0?Me(this,a):Me(this,a)};_.zd=function lL(){};var hw=Xdb(Vub,'ValueBoxBase',528);GE(236,528,Yub);var _v=Xdb(Vub,'TextBoxBase',236);GE(574,236,Yub);var $v=Xdb(Vub,'TextArea',574);GE(144,236,Yub,oL);var aw=Xdb(Vub,'TextBox',144);GE(65,4,ywb);var qL,rL,sL,tL;var gw=Ydb(Vub,'ValueBoxBase/TextAlignment',65,wL);GE(529,65,ywb,xL);var cw=Ydb(Vub,'ValueBoxBase/TextAlignment/1',529,null);GE(530,65,ywb,yL);var dw=Ydb(Vub,'ValueBoxBase/TextAlignment/2',530,null);GE(531,65,ywb,zL);var ew=Ydb(Vub,'ValueBoxBase/TextAlignment/3',531,null);GE(532,65,ywb,AL);var fw=Ydb(Vub,'ValueBoxBase/TextAlignment/4',532,null);GE(482,481,iwb,DL);_.Pe=function EL(a){return CL(this,a)};var iw=Xdb(Vub,'VerticalPanel',482);GE(517,1,{},KL);_.Oe=function LL(){return new NL(this)};_.c=0;var kw=Xdb(Vub,'WidgetCollection',517);GE(235,1,{},NL);_.Ze=function PL(){return ML(this)};_.Ye=function OL(){return this.b<this.c.c};_.$e=function QL(){if(!this.a){throw aE(new Aeb)}this.c.b.Pe(this.a);--this.b;this.a=null};_.b=0;var jw=Xdb(Vub,'WidgetCollection/WidgetIterator',235);GE(147,1,{147:1},XL);_._e=function YL(a){a.blur()};_.af=function ZL(){var a;a=Ri($doc);a.tabIndex=0;return a};_.bf=function $L(a){a.focus()};var TL,UL;var ow=Xdb(zwb,'FocusImpl',147);GE(149,147,Awb,bM);_.af=function cM(){return dM(_L?_L:(_L=aM()))};var _L;var nw=Xdb(zwb,'FocusImplStandard',149);GE(649,149,Awb,eM);_._e=function fM(a){$wnd.setTimeout(function(){a.blur()},0)};_.bf=function gM(a){$wnd.setTimeout(function(){a.focus()},0)};var mw=Xdb(zwb,'FocusImplSafari',649);GE(173,1,{173:1},hM);_.cf=function iM(){return Ri($doc)};_.df=function jM(a){return a};_.ef=function kM(a){return Kh((Gh(),a))};_.ff=function lM(a,b){a.style['clip']=b};var rw=Xdb(zwb,'PopupImpl',173);GE(563,173,{173:1},oM);_.cf=function pM(){var a;a=(JF(),Ri($doc));if(mM){a.innerHTML='<div><\/div>';s2((og(),ng),new vM(a))}return a};_.df=function qM(a){return mM?Jh((Gh(),a)):a};_.ef=function rM(a){return mM?a:Kh((Gh(),a))};_.ff=function uM(a,b){a.style['clip']=b;a.style[nvb]=(Ak(),Pub);a.style[nvb]=''};var mM=false;var qw=Xdb(zwb,'PopupImplMozilla',563);GE(564,1,mwb,vM);_.Ld=function wM(){this.a.style[uwb]=(rl(),Bwb)};var pw=Xdb(zwb,'PopupImplMozilla/1',564);GE(363,1,{},BM);var vw=Xdb(Dvb,'SimpleEventBus/1',363);GE(364,1,{725:1},CM);_.Ld=function DM(){Kp(this.a,this.d,this.c,this.b)};var ww=Xdb(Dvb,'SimpleEventBus/2',364);GE(365,1,{725:1},EM);_.Ld=function FM(){Mp(this.a,this.d,this.c,this.b)};var xw=Xdb(Dvb,'SimpleEventBus/3',365);GE(54,1,{54:1},UM,VM);_.gf=function WM(){return J$(this.n.a,this.c)};_.hf=function XM(){vh(this.d,Ewb+this.c+Fwb+this.k+' cell '+this.b)};_.b='cs0';_.c=0;_.f=false;_.g=true;_.i=false;_.k=0;var Ew=Xdb(Hwb,'Cell',54);GE(113,1,{113:1},YM);_.Zc=function ZM(a){var b;if(this===a){return true}if(a==null){return false}if(!Zq(a,113)){return false}b=a;return this.d==b.d&&this.a==b.a&&Lpb(Neb(this.c),Neb(b.c))&&Lpb(Neb(this.b),Neb(b.b))};_._c=function $M(){return xlb(jq(eq(KB,1),Jtb,1,5,[this.d,this.a,Neb(this.c),Neb(this.b)]))};_.b=0;_.c=0;var Aw=Xdb(Hwb,'Cell/CellValueStyleKey',113);GE(162,109,iwb);_.Ue=function rN(){fN(this,false)};_.Ve=function sN(a){fN(this,a)};_.wd=function tN(){var a,b;b=aN;if(b){a=b.kf();Wg(a,(JF(),this.Yc))}Le(this)};_.yd=function uN(){Ne(this);!!this.t&&bh(this.t)};_.pd=function vN(a){jN(this,a)};_.We=function wN(a,b){lN(this,a,b)};_.jf=function xN(a){(JF(),this.Yc).style[ovb]=a?Avb:Bvb;!!this.t&&(this.t.style[ovb]=a?Avb:Bvb,undefined)};_.rd=function yN(a){mN(this,a)};var _M=20000,aN,bN=-1,cN=-1;var lA=Xdb(Mwb,'Overlay',162);GE(136,162,iwb);_.kf=function BN(){zN(this);Orb(Qrb(Tdb(this.qg)),'Could not determine ApplicationConnection for Overlay. Overlay will be attached directly to the root panel');return ie((QK(),UK()))};var $z=Xdb(Nwb,'VOverlay',136);GE(114,136,iwb,EN,FN,GN);_.kf=function HN(){var a;return a=(JF(),$doc.getElementById(Owb)),!a?(QK(),$doc.body):a};var oy=Xdb(Hwb,'SpreadsheetOverlay',114);GE(167,114,{18:1,14:1,9:1,16:1,31:1,19:1,12:1,13:1,167:1},VN);_.Ue=function WN(){KN(this)};_.jf=function XN(a){(JF(),this.Yc).style[ovb]=a?Avb:Bvb;!!this.t&&(this.t.style[ovb]=a?Avb:Bvb,undefined);this.i.style[ovb]=(a?(wm(),vm):(wm(),um)).ke()};_.b=0;_.d=0;_.k=0;_.n=0;var Cw=Xdb(Hwb,'CellComment',167);GE(399,1,Uwb,YN);_.re=function ZN(a){ZV(this.b,this.a)};var Bw=Xdb(Hwb,'CellComment/1',399);GE(193,1,{193:1,3:1},$N);_.equals=function _N(a){var b;if(this===a){return true}if(a==null){return false}if(Dw!=O(a)){return false}b=a;if(this.col!=b.col){return false}if(this.row!=b.row){return false}return true};_.Zc=function(a){return this.equals(a)};_.hashCode=function aO(){var a;a=this.row+((this.col+1)/2|0);return 31*(this.col+a*a)};_._c=function(){return this.hashCode()};_.toString=function bO(){return Kfb(Kfb(Kfb(Hfb(Kfb(Hfb(Kfb(new Mfb,'r'),this.row),'c'),this.col),this.cellStyle),'|'),this.value).a};_.ad=function(){return this.toString()};_.cellStyle='cs0';_.col=0;_.isPercentage=false;_.locked=false;_.needsMeasure=false;_.row=0;var Dw=Xdb(Hwb,'CellData',193);GE(97,89,Vwb);_.xd=function hO(a){(Gh(),Fh).Xd(a);a.stopPropagation();if(Fh.Sd(a)==1){this.c.rf(this.mf(),this.e,!this.b);dO(this,!this.b)}};_.b=false;_.d=0;_.e=0;_.f=false;_.g=0;_.i=0;_.j=0;_.k=0;_.n=0;var Yw=Xdb(Hwb,'GroupingWidget',97);GE(240,97,Vwb,iO);_.lf=function jO(){var a;a=new iO(this.e,this.c);cO(this,a);return a};_.mf=function kO(){return true};_.nf=function lO(a){(JF(),this.Yc).style[Kwb]=a+(im(),rwb);this.i=a};_.pf=function mO(a,b){this.k=9+b*18;this.g=a;(JF(),this.Yc).style[kwb]=this.k+(im(),rwb);this.Yc.style[jwb]=a+rwb};_.qf=function nO(a){(JF(),this.Yc).style[Rub]=a+(im(),rwb);this.n=a};var Fw=Xdb(Hwb,'ColumnGrouping',240);GE(650,1,{},rO);var Gw=Xdb(Hwb,'CopyPasteHandlerImpl',650);GE(575,574,{18:1,27:1,14:1,182:1,9:1,60:1,73:1,16:1,19:1,12:1,13:1},vO);_.Ee=function wO(a){var b;b=a.d;_F((Gh(),b).type)==128&&tO(this,b)};var Jw=Xdb(Hwb,'CopyPasteTextBox',575);GE(576,1,{},xO);_.Kd=function yO(){SU(this.a.c);ie(this.a).style[jwb]=(im(),'-1000.0px');zj(this.b)==67?this.a.a:zj(this.b)==88&&pO(this.a.a);return false};var Hw=Xdb(Hwb,'CopyPasteTextBox/1',576);GE(577,1,{},zO);_.Kd=function AO(){var a,b;a=(b=gL(this.a),b==null?'':b);qO(this.a.a,a);SU(this.a.c);return false};var Iw=Xdb(Hwb,'CopyPasteTextBox/2',577);GE(579,1,Wwb,CO);_.xd=function DO(a){var b,c,d,e;switch(JF(),$G((Gh(),a).type)){case 128:e=this.c.V;switch(a.keyCode|0){case 9:Fh.Xd(a);SU(this.c.V);y_(this.c.V.a,a,'');break;case 27:s2((og(),ng),new BY(e));}break;case 2048:c=this.c.V.wb;AT(c,this.a);b=c.a;d=c.b;lX(this.c.V,b,d);eY(this.c.V,b,b,d,d);cY(this.c.V,b,b,d,d,true);vab(this.c.W,d,b,true);this.b.b=true;break;case 4096:this.b.b=false;}};var Kw=Xdb(Hwb,'CustomEditorEventListener',579);GE(381,692,lwb,pP);_.f=false;_.g=false;_.k=-1;_.n=-1;_.o=-1;_.p=-1;_.q=-1;_.s=-1;_.u=false;_.v=false;_.A=null;var EO;var Xw=Xdb(Hwb,'FormulaBarWidget',381);GE(382,1,Wwb,rP);_.xd=function sP(a){iL(this.a.a,iJ(this.a.B));kP(this.a)};var Ow=Xdb(Hwb,'FormulaBarWidget/1',382);GE(387,1,mwb,tP);_.Ld=function uP(){if(this.a.f){this.a.s=-1;this.a.q=eL(this.a.e);IO(this.a)}};var Lw=Xdb(Hwb,'FormulaBarWidget/10',387);GE(388,45,{},vP);_.ld=function wP(){this.a.d=null};var Mw=Xdb(Hwb,'FormulaBarWidget/11',388);GE(222,1,mwb,xP);_.Ld=function yP(){var a;if(!this.a.f){return}WO(this.a,(a=gL(this.a.e),a==null?'':a));IO(this.a)};var Nw=Xdb(Hwb,'FormulaBarWidget/12',222);GE(383,1,Wwb,zP);_.xd=function AP(a){var b,c,d;d=(JF(),$G((Gh(),a).type));if(d==512){b=a.keyCode|0;if(b==13){kP(this.a);lP(this.a,(c=gL(this.a.a),c==null?'':c));cf(this.a.a,false)}else if(b==27){ZO(this.a);SU(this.a.t.V)}}else if(d==2048){w0(this.a.t,true);ie(this.a.a).style[axb]=(Nl(),jwb)}else{w0(this.a.t,false);ie(this.a.a).style[axb]=''}};var Pw=Xdb(Hwb,'FormulaBarWidget/2',383);GE(384,1,Wwb,BP);_.xd=function CP(a){var b;switch(JF(),$G((Gh(),a).type)){case 2048:if(this.a.f&&this.a.e==this.a.w){this.a.f=false;JO(this.a,this.a.j)}else{w0(this.a.t,true);this.a.c=(b=gL(this.a.j),b==null?'':b);l_(this.a.t);JO(this.a,this.a.j)}break;case 4096:if(!this.a.f){w0(this.a.t,false);k_(this.a.t,(b=gL(this.a.j),b==null?'':b))}break;case 128:QO(this.a,a);break;case Pvb:case 256:KO(this.a);nP(this.a,true);_O(this.a);break;case 8:this.a.f&&nP(this.a,true);}};var Qw=Xdb(Hwb,'FormulaBarWidget/3',384);GE(218,1,mwb,DP);_.Ld=function EP(){var a,b,c,d;if(!this.a.f){return}d=(c=gL(this.a.e),c==null?'':c);b=eL(this.a.e);a=b>0?(vtb(b-1,d.length),d.charCodeAt(b-1)):0;this.a.g=false;a==40||a==43||a==45||a==47||a==42?(this.a.g=true):a==61&&d.length==1&&(this.a.g=true)};var Rw=Xdb(Hwb,'FormulaBarWidget/4',218);GE(385,1,mwb,FP);_.Ld=function GP(){var a;cf(this.a.e,true);gP(ie(this.a.e),this.a.q,0);WO(this.a,(a=gL(this.a.e),a==null?'':a));IO(this.a)};var Sw=Xdb(Hwb,'FormulaBarWidget/5',385);GE(219,45,{},HP);_.ld=function IP(){var a;n_(this.a.t,(a=gL(this.a.j),a==null?'':a))};var Tw=Xdb(Hwb,'FormulaBarWidget/6',219);GE(220,1,mwb,JP);_.Ld=function KP(){var a;n_(this.a.t,(a=gL(this.a.j),a==null?'':a))};var Uw=Xdb(Hwb,'FormulaBarWidget/7',220);GE(221,1,mwb,LP);_.Ld=function MP(){var a;!!this.a.e&&(a=gL(this.a.e),a==null?'':a).length==0&&(this.a.e==this.a.w?jP(this.a):iP(this.a))};var Vw=Xdb(Hwb,'FormulaBarWidget/8',221);GE(386,1,mwb,NP);_.Ld=function OP(){var a,b;if(!this.a.f){b=(a=gL(this.b),a==null?'':a);if(jfb(b.substr(0,1),'=')||jfb(b.substr(0,1),'+')){this.a.f=true;this.a.e=this.b;WO(this.a,b);IO(this.a)}}};var Ww=Xdb(Hwb,'FormulaBarWidget/9',386);GE(146,54,{54:1,146:1},PP);_.gf=function QP(){return this.d.clientWidth|0};_.hf=function RP(){vh(this.d,Ewb+this.c+Fwb+this.k+' cell '+this.b+' '+bxb)};var Zw=Xdb(Hwb,'MergedCell',146);GE(71,1,{71:1,3:1},SP);_.col1=0;_.col2=0;_.id=0;_.row1=0;_.row2=0;var $w=Xdb(Hwb,'MergedRegion',71);GE(194,1,{194:1,3:1},WP);_.col=0;_.dx=0;_.dy=0;_.height=0;_.row=0;_.type='IMAGE';_.width=0;var UP='COMPONENT',VP='IMAGE';var _w=Xdb(Hwb,'OverlayInfo',194);GE(572,144,Yub,XP);_.xd=function YP(a){var b;JF();$G((Gh(),a).type)==Pvb&&s2((og(),ng),new ZP(this));b=$G(a.type);(b&896)!=0?Me(this,a):Me(this,a)};var bx=Xdb(Hwb,'PasteAwareTextBox',572);GE(573,1,mwb,ZP);_.Ld=function $P(){wV(this.a.a)};var ax=Xdb(Hwb,'PasteAwareTextBox/1',573);var cx=Zdb(Hwb,'PopupButtonClientRpc');var QA=Zdb(cxb,'Connector');GE(271,1,fxb);_.sf=function jQ(){return _P(this)};_.tf=function kQ(a){return cQ(this,a)};_.uf=function lQ(){return dQ(this)};_.vf=function nQ(a){gQ(this,a)};_.wf=function oQ(a){iQ(this,a)};_.I=true;var Jz=Xdb(Nwb,'AbstractConnector',271);GE(272,271,fxb);_.uf=function EQ(){return this.yf()};_.xf=function CQ(){return qQ(this)};_.yf=function DQ(){return !this.L&&(this.L=this.sf()),this.L};_.zf=function FQ(){return rQ(this)};_.Af=function GQ(){var a,b,c;if(!this.o&&(b=this.uf().qb,!!b&&b.contains('cClick'))){this.o=Ie(this.zf(),new f5(this),(sn(),sn(),rn));(F1(),!E1&&(E1=new O1),F1(),E1).b&&(c=this.zf(),this.A=Ie(c,new h5(this,c),($o(),$o(),Zo)),this.w=Ie(c,new d5(this),(To(),To(),So)),this.v=Ie(c,new j5(this),(Lo(),Lo(),Ko)),undefined)}else if(!!this.o&&(a=this.uf().qb,!(!!a&&a.contains('cClick')))){AM(this.o.a);this.o=null;zQ(this)}};_.Bf=function HQ(){var a;a=this.yf();if(a.gb!=null&&a.gb.length!=0){return true}return a.jb!=null&&a.jb.length!=0};_.vf=function IQ(a){vQ(this,a)};_.Cf=function JQ(){var a;a=this.zf();L2((JF(),a.Yc),ye(a.nd())+'-error',this.yf().ib)};_.wf=function KQ(a){iQ(this,a);xQ(this,fQ(this))};_.n=20;_.o=null;_.p='';_.q='';_.s=false;_.u=false;_.B=0;_.C=0;var Hz=Xdb(Nwb,'AbstractComponentConnector',272);GE(157,272,fxb);var Kz=Xdb(Nwb,'AbstractHasComponentsConnector',157);GE(332,157,{181:1,674:1,27:1,100:1,123:1,3:1},NQ);_.yf=function QQ(){return !this.L&&(this.L=_P(this)),this.L};_.uf=function RQ(){return !this.L&&(this.L=_P(this)),this.L};_.zf=function SQ(){return !this.D&&(this.D=new rR),this.D};_.xf=function OQ(){return new rR};_.tf=function PQ(a){return this.b};_.re=function TQ(a){var b;b=(!this.D&&(this.D=new rR),this.D);Hab(this.b,b.k,b.b)};_.Ae=function UQ(a){var b;b=(!this.D&&(this.D=new rR),this.D);b.Uc&&Iab(this.b,b.k,b.b)};_.vf=function VQ(a){var b,c;c=(!this.D&&(this.D=new rR),this.D);b=(!this.L&&(this.L=_P(this)),this.L);(a.Hf(Ewb)||a.Hf('row'))&&s2((og(),ng),new XQ(c,b));a.Hf(gxb)&&hR(c,b.active);a.Hf('popupHeight')&&mR(c,b.popupHeight);a.Hf('popupWidth')&&nR(c,b.popupWidth);a.Hf('headerHidden')&&lR(c,b.headerHidden)};var fx=Xdb(Hwb,'PopupButtonConnector',332);GE(333,1,Jtb,WQ);var dx=Xdb(Hwb,'PopupButtonConnector/1',333);GE(334,1,mwb,XQ);_.Ld=function YQ(){pR(this.b,this.a.row,this.a.col)};var ex=Xdb(Hwb,'PopupButtonConnector/2',334);GE(359,13,Wub,bR);_.xd=function cR(a){if(nf(kY(a),this.b)){fN(this.c,false);SU(this.e)}else{Me(this,a)}};var gx=Xdb(Hwb,'PopupButtonHeader',359);var hx=Zdb(Hwb,'PopupButtonServerRpc');GE(75,1,{75:1,3:1});_.pb=true;var XA=Xdb(hxb,'SharedState',75);GE(67,75,ixb);_.eb=null;_.fb=false;_.gb='';_.ib=null;_.jb=null;_.kb='';_.lb=null;_.mb=null;_.nb=null;_.ob='';var PA=Xdb(cxb,'AbstractComponentState',67);GE(195,67,{195:1,67:1,75:1,3:1},eR);_.active=false;_.col=0;_.headerHidden=false;_.popupHeight=null;_.popupWidth=null;_.row=0;var ix=Xdb(Hwb,'PopupButtonState',195);GE(70,211,{181:1,18:1,27:1,14:1,9:1,60:1,73:1,16:1,19:1,12:1,13:1,70:1},rR);_.re=function sR(a){iR(this);Ej(a.a)};_.yd=function tR(){fN(this.e,false);gR(this);Ne(this)};_.qd=function uR(a,b){Be((JF(),this.Yc),a,b);se(this.e,a,b)};_.b=0;_.k=0;var lx=Xdb(Hwb,'PopupButtonWidget',70);GE(322,1,{},vR);_.Xe=function wR(a,b){var c,d,e,f,g;f=Xg(this.a.j);c=(Gh(),Fh).Zd(f)+((f.offsetHeight||0)|0);d=Fh.Yd(f)+((f.offsetWidth||0)|0);e=d-a;e<hh(this.a.n)&&(e=d);g=c;g+b>gh(this.a.n)&&(g=Fh.Zd(f)-b);g<jh(this.a.n)&&(g=jh(this.a.n));lN(this.a.e,e,g)};var jx=Xdb(Hwb,'PopupButtonWidget/1',322);GE(323,1,mwb,xR);_.Ld=function yR(){bK(this.a.e,this.a.a)};var kx=Xdb(Hwb,'PopupButtonWidget/2',323);GE(241,97,Vwb,zR);_.lf=function AR(){var a;a=new zR(this.e,this.c);cO(this,a);return a};_.mf=function BR(){return false};_.nf=function CR(a){(JF(),this.Yc).style[Lwb]=a+(im(),rwb);this.j=a};_.pf=function DR(a,b){this.g=6+b*15;this.k=a;(JF(),this.Yc).style[jwb]=this.g+(im(),rwb);this.Yc.style[kwb]=a+rwb};_.qf=function ER(a){(JF(),this.Yc).style[Qub]=a+(im(),rwb);this.d=a};var mx=Xdb(Hwb,'RowGrouping',241);GE(398,1,{},VR);_.a=0;_.b=0;var nx=Xdb(Hwb,'SelectionHandler',398);GE(410,692,lwb,vS);_.pd=function wS(a){};_.rd=function xS(a){};_.c=0;_.d=0;_.e=0;_.f=0;_.g=false;_.i=false;_.j=false;_.k=0;_.n=0;_.o=false;_.p=false;_.r=0;_.s=false;_.t=0;_.u=0;_.v=0;_.w=0;_.C=false;_.G=0;_.H=0;_.I=0;_.J=0;_.K=0;_.L=0;_.N=false;_.O=0;_.P=0;_.R=false;_.S=false;_.T=false;_.U=0;_.V=0;_.Y=0;_.Z=0;_._=false;_.ab=0;var vx=Xdb(Hwb,'SelectionWidget',410);GE(412,45,{},yS);_.ld=function zS(){var a,b,c,d,e,f,g,h,i;Ah(this.a.Q.zc,((this.a.Q.zc.scrollTop||0)|0)+(this.a.n/2|0));zh(this.a.Q.zc,qh(this.a.Q.zc)+(this.a.k/2|0));dW(this.a.Q);g=this.a.c;h=this.a.d;this.a.k<0?(g=hh(this.a.Q.zc)+5):this.a.k>0&&(g=ih(this.a.Q.zc)-25);this.a.n<0?(h=jh(this.a.Q.zc)+5):this.a.n>0&&(h=gh(this.a.Q.zc)-25);if(this.a.n!=0&&((this.a.Q.zc.scrollTop||0)|0)==0){e=new f2;c2(e,this.a.Q.Gc);d=jh(this.a.Q.Gc)+e.d[0]+5;this.a.d>d?(h=this.a.d):(h=d)}if(this.a.k!=0&&qh(this.a.Q.zc)==0){e=new f2;c2(e,this.a.Q.Gc);c=hh(this.a.Q.Gc)+e.d[3]+5;this.a.c>c?(g=this.a.c):(g=c)}if(this.a.C){aS(this.a,g,h)}else{i=y2(g,h);if(i){a=(Gh(),i).getAttribute($ub)||'';AT(this.a.Q.wb,a);b=this.a.Q.wb.a;f=this.a.Q.wb.b;b!=0&&f!=0&&v_(this.a.Q.a,b,f)}}};var ox=Xdb(Hwb,'SelectionWidget/1',412);GE(413,1,mwb,AS);_.Ld=function BS(){eS(this.a,true);fN(this.a.$,false)};var px=Xdb(Hwb,'SelectionWidget/2',413);GE(414,1,mwb,CS);_.Ld=function DS(){bK(this.a.$,new ES(this));oN(this.a.$)};var rx=Xdb(Hwb,'SelectionWidget/3',414);GE(415,1,{},ES);_.Xe=function FS(a,b){var c,d,e,f,g;f=0;d=0;c=0;g=0;e=0;if(!!this.a.a.X&&le(this.a.a.X)){f=jh(this.a.a.X.G);d=hh(this.a.a.X.G);g=this.a.a.X.G.clientWidth|0;c=gh(this.a.a.X.a)+5;le(this.a.a.W)&&(g+=this.a.a.W.G.clientWidth|0);le(this.a.a.b)&&(c=gh(this.a.a.b.a)+5)}else if(!!this.a.a.W&&le(this.a.a.W)){f=jh(this.a.a.W.G);d=hh(this.a.a.W.G);g=this.a.a.W.G.clientWidth|0;c=gh(this.a.a.W.a)+5;le(this.a.a.a)&&(c=gh(this.a.a.a.a)+5)}else if(!!this.a.a.a&&le(this.a.a.a)){f=jh(this.a.a.a.G);d=hh(this.a.a.a.G);g=this.a.a.a.G.clientWidth|0;c=gh(this.a.a.a.a)+5;le(this.a.a.b)&&(g+=this.a.a.b.G.clientWidth|0)}else{f=jh(this.a.a.b.G);d=hh(this.a.a.b.G);g=this.a.a.b.G.clientWidth|0;c=gh(this.a.a.b.a)+5}g>(ie(this.a.a.Q).clientWidth|0)&&(g=ie(this.a.a.Q).clientWidth|0);this.a.a.Q.Tc>0?(e=jh(this.a.a.Q.Qc)):(e=jh(this.a.a.Q.zc));f-=b+5;d+=(g/2|0)-(a/2|0);e>f&&(f=c+5);lN(this.a.a.$,d,f)};var qx=Xdb(Hwb,'SelectionWidget/3/1',415);GE(141,13,Wub,NS);_.b=0;_.c=0;_.e=0;_.f=0;_.g=0;_.i=0;_.n=0;_.o=0;var sx=Xdb(Hwb,'SelectionWidget/PaintOutlineWidget',141);GE(140,13,Wub,bT);_.b=false;_.e=0;_.f=0;_.j=0;_.n=false;_.q=0;_.r=0;_.s=0;_.t=0;_.v=false;_.C=0;_.D=0;_.H=false;_.K=0;var ux=Xdb(Hwb,'SelectionWidget/SelectionOutlineWidget',140);GE(411,1,Wwb,cT);_.xd=function dT(a){var b,c,d;b=(JF(),(Gh(),Fh).Wd(a));d=$G(a.type);c=d==_vb||d==Rtb||d==awb||d==bwb;if(this.a.F.C){PS(this.a,a);a.stopPropagation()}else if(d==4){if(nf(b,this.a.g)){PS(this.a,a);a.stopPropagation()}else if(nf(b,this.a.G));else if(nf(b,this.a.k));else if(nf(b,this.a.u));else nf(b,this.a.a)}else if(c){if(d==Rtb||d==bwb){aG(this.a.B);qS(this.a.F)}else if(nf(b,this.a.g)||nf(b,this.a.i)){if(d==_vb){bG(this.a.B);XR(this.a.F,a)}else{dS(this.a.F,a)}}else{this.a.F.p&&PS(this.a,a)}a.stopPropagation()}};var tx=Xdb(Hwb,'SelectionWidget/SelectionOutlineWidget/1',411);GE(651,1,Wwb,jT);_.xd=function kT(a){var b,c,d;c=(b=a.composedPath(),ssb(Flb(b,b.length),new lT));if(kh(kY(a)).indexOf(jxb)!=-1){aX(this.c,true);return}d=(JF(),$G((Gh(),a).type));if(d==2048){aX(this.c,true);this.b=true}else if(d==4096&&!c){aX(this.c,false);this.b=false}else if(d==awb){a.stopPropagation()}else if(this.c.xc){Ztb==d&&dW(this.c);gT(this,a)}else{switch(d){case Ztb:dW(this.c);break;case 256:fT(this,a);break;case 128:eT(this,a);break;case 4:Fh.Sd(a)!=2&&_V(this.c,a);break;case $vb:_V(this.c,a);break;case 2:hT(this,a);break;case 32:case 16:bW(this.c,a);break;case 64:aW(this.c);}}};_.a=false;_.b=false;var xx=Xdb(Hwb,'SheetEventListener',651);GE(247,1,{},lT);var wx=Xdb(Hwb,'SheetEventListener/lambda$0$Type',247);GE(578,1,{728:1,181:1,739:1,736:1,741:1,251:1,252:1,27:1},qT);_.qe=function rT(a){var b;aX(this.b,false);if(this.a.f){nP(this.a,false)}else if(this.b._){Z$(this.b.a,(b=gL(this.b.sb),b==null?'':b));NO(this.a)}Ej(a.a)};_.re=function sT(a){this.b._&&(this.a.v=true);Ej(a.a)};_.ue=function tT(a){var b;if(uj(a.a)==2){b=this.b.wb;AT(b,oV(this.b));c_(this.b.a,a.a,b.a,b.b)}Ej(a.a)};_.ve=function uT(a){nP(this.a,false)};var yx=Xdb(Hwb,'SheetInputEventListener',578);GE(352,1,{},DT);_.a=0;_.b=0;var zx=Xdb(Hwb,'SheetJsniUtil',352);GE(237,132,{18:1,14:1,9:1,16:1,31:1,19:1,12:1,13:1,237:1},KT);_.a=0;_.b=0;var Ax=Xdb(Hwb,'SheetOverlay',237);GE(304,13,Wub,_T);_.b='';_.d=false;_.j=false;_.r=-1;_.s=0;_.t=0;var Ex=Xdb(Hwb,'SheetTabSheet',304);GE(305,1,Wwb,aU);_.xd=function bU(a){var b,c,d,e,f;d=kY(a);f=(JF(),$G((Gh(),a).type));if(nf(d,this.a.g)){return}a.stopPropagation();if(f==1){this.a.d&&!this.a.j&&MT(this.a);TU(this.a.e.V,false);if($g(this.a.i,d)&&!sh(d,Bvb)){if(nf(d,this.a.n)){this.a.t=0;this.a.s=0;this.a.c.style[Kwb]=this.a.t+(im(),rwb);YT(this.a)}else if(nf(d,this.a.p)){if(this.a.s>0){--this.a.s;this.a.s==0?(this.a.t=0):(this.a.t+=RT(this.a,this.a.s));this.a.c.style[Kwb]=this.a.t+(im(),rwb)}YT(this.a)}else if(nf(d,this.a.q)){if(this.a.s<this.a.u.length-1){this.a.t-=RT(this.a,this.a.s);this.a.c.style[Kwb]=this.a.t+(im(),rwb);++this.a.s;YT(this.a)}}else if(nf(d,this.a.o)){e=PT(this.a);ST(this.a,e)}else nf(d,this.a.a)&&(this.a.j||p_(this.a.e))}else if($g(this.a.c,d)){for(c=0;c<this.a.u.length;c++){nf(this.a.u[c],d)&&c!=this.a.r&&B_(this.a.e,c)}}}else if(f==2){if(!this.a.j){for(c=0;c<this.a.u.length;c++){if(nf(this.a.u[c],d)){if(c!=this.a.r){B_(this.a.e,c)}else{this.a.d=true;b=this.a.u[c];this.a.b=Fh.be(b);tj(this.a.g,this.a.b);Fh.fe(b,'');Wg(b,this.a.g);this.a.g.focus();ZT(this.a)}}}}}};var Bx=Xdb(Hwb,'SheetTabSheet/1',305);GE(306,1,Wwb,cU);_.xd=function dU(a){var b,c;c=(JF(),$G((Gh(),a).type));if(this.a.d){if(c==4096){MT(this.a)}else{switch(a.keyCode|0){case 13:case 9:MT(this.a);break;case 27:this.a.d=false;bh(this.a.g);b=this.a.u[this.a.r];b.style[Rub]='';WT(b,this.a.b);SU(this.a.e.V);break;default:OT(this.a);}}}a.stopPropagation()};var Cx=Xdb(Hwb,'SheetTabSheet/2',306);GE(307,1,mwb,eU);_.Ld=function fU(){ZT(this.a)};var Dx=Xdb(Hwb,'SheetTabSheet/3',307);GE(160,688,{18:1,14:1,9:1,16:1,31:1,19:1,12:1,13:1,160:1},jY);_.Oe=function lY(){return UV(this)};_.wd=function mY(){Le(this);uO(this.M);!this.M.Xc&&LH(this,this.M)};_.Ad=function nY(){fN(this.qb,false);fN(this.Xb,false);sO(this.M)};_.Pe=function oY(a){return sW(this,a)};_.f=0;_.g=0;_.k=-1;_.n=-1;_.o=false;_.v=true;_.A=0;_.B=0;_.C=true;_.G=false;_.H=0;_.L=false;_.O=false;_.P=false;_.R=false;_.V=-1;_.X=0;_.Y=0;_.Z=false;_._=false;_.ab=false;_.bb=0;_.cb=0;_.db=0;_.eb=0;_.nb=null;_.ob=0;_.ub=null;_.vb=false;_.xb=0;_.yb=0;_.zb=0;_.Ab=0;_.Bb=0;_.Cb=0;_.Db=false;_.Lb=0;_.Ob=0;_.Pb=0;_.Sb=0;_.Tb=0;_.Zb=false;_.$b=-1;_._b=-1;_.ac=false;_.bc=false;_.ec=false;_.fc=0;_.jc=false;_.nc=false;_.oc=0;_.pc=0;_.rc=0;_.sc=0;_.xc=false;_.Fc=false;_.Hc=false;_.Ic=false;_.Jc=false;_.Kc=0;_.Lc=0;_.Mc=0;_.Pc=0;_.Sc=false;_.Tc=0;var Zx=Xdb(Hwb,'SheetWidget',160);GE(335,1,mwb,pY);_.Ld=function qY(){this.a.k!=-1&&this.a.n!=-1&&pX(this.a,this.a.k,this.a.n)};var Ox=Xdb(Hwb,'SheetWidget/1',335);GE(344,1,mwb,rY);_.Ld=function sY(){var a,b;if(this.a.jc){return}b=this.d;while(Q$(this.a.a,b)){--b}if(b==0){return}bG(ie(this.a));this.a.bc=true;this.a._b=b;this.a.$b=-1;this.a._b<=this.a.Tc?(a=Qkb(this.a.jb,this.a._b-1)):(a=Qkb(this.a.ic,b-this.a.db));this.a.Sb=(Gh(),Fh).Zd(a);this.a.Tb=Fh.Zd(a)+((a.offsetHeight||0)|0);N$(this.a.a,b)>0?HI(this.a.Yb,'Height: '+N$(this.a.a,b)+'pt'):HI(this.a.Yb,'Hide row');sX(this.a,this.b,this.c);oN(this.a.Xb);eh(this.a.Gc,gyb);eh(this.a.Vb,'row'+b);++b;while(this.d<this.a.a.O&&Q$(this.a.a,b)){++b}eh(this.a.Ub,_xb+b);xV(this.a,this.b,this.c)};_.b=0;_.c=0;_.d=0;var Fx=Xdb(Hwb,'SheetWidget/10',344);GE(345,1,mwb,tY);_.Ld=function uY(){var a,b;if(this.a.L){return}b=this.d;while(P$(this.a.a,b)){--b}if(b<1){return}bG(ie(this.a));this.a.ac=true;this.a.$b=b;this.a._b=-1;this.a.$b<=this.a.ob?(a=Qkb(this.a.ib,this.a.$b-1)):(a=Qkb(this.a.K,b-this.a.bb));this.a.Sb=(Gh(),Fh).Yd(a);this.a.Tb=Fh.Yd(a)+((a.offsetWidth||0)|0);J$(this.a.a,b)>0?HI(this.a.Yb,'Width: '+J$(this.a.a,b)+rwb):HI(this.a.Yb,Sxb);sX(this.a,this.b,this.c);oN(this.a.Xb);eh(this.a.Gc,hyb);eh(this.a.Vb,Ewb+b);++b;while(this.d<=this.a.a.i&&P$(this.a.a,b)){++b}eh(this.a.Ub,Zxb+b);tV(this.a,this.b,this.c)};_.b=0;_.c=0;_.d=0;var Gx=Xdb(Hwb,'SheetWidget/11',345);GE(346,1,{},vY);_.Xe=function wY(a,b){dX(this.a,a,b,this.b)};var Hx=Xdb(Hwb,'SheetWidget/12',346);GE(134,1,mwb,xY);_.Ld=function yY(){var a,b;b=(a=gL(this.a.sb),a==null?'':a);kW(this.a,b);this.b&&b_(this.a.a,b)};_.b=false;var Ix=Xdb(Hwb,'SheetWidget/13',134);GE(348,1,mwb,zY);_.Ld=function AY(){cf(this.a.sb,true);ifb(this.b,'%')?hL(this.a.sb,this.b.length-1,0):hL(this.a.sb,this.b.length,0)};var Jx=Xdb(Hwb,'SheetWidget/14',348);GE(92,1,mwb,BY);_.Ld=function CY(){this.a.zc.focus()};var Kx=Xdb(Hwb,'SheetWidget/15',92);GE(349,1,Wwb,DY);_.xd=function EY(a){S$(this.a.a,true,this.b)};_.b=0;var Lx=Xdb(Hwb,'SheetWidget/16',349);GE(350,1,Wwb,FY);_.xd=function GY(a){S$(this.a.a,false,this.b)};_.b=0;var Mx=Xdb(Hwb,'SheetWidget/17',350);GE(351,1,mwb,HY);_.Ld=function IY(){var a,b,c,d,e,f,g,h,i,j;g=this.a.Oc.clientWidth|0;e=this.a.gc.clientWidth|0;j=g+e;if(e==0&&!NV(this.a.F)){j-=1}else if(e!=0&&!NV(this.a.F));else e!=0&&NV(this.a.F)&&(j+=2);this.a.F.style[Rub]=j+(im(),rwb);f=this.a.Oc.clientHeight|0;b=this.a.I.clientHeight|0;c=f+b;if(b==0&&!NV(this.a.dc));else b!=0&&!NV(this.a.dc)?(c+=1):b!=0&&NV(this.a.dc)&&(c+=2);this.a.dc.style[Qub]=c+rwb;a=this.a.c.clientHeight|0;i=this.a.Qc.clientWidth|0;h=i+j;d=a+c;NV(this.a.F)&&(h+=1);NV(this.a.dc)&&(d+=1);this.a.I.style[Rub]=h+rwb;this.a.D.style[Rub]=h+rwb;this.a.gc.style[Qub]=d+rwb;this.a.cc.style[Qub]=d+rwb};var Nx=Xdb(Hwb,'SheetWidget/18',351);GE(336,1,mwb,JY);_.Ld=function KY(){var b,c,d,e,f;if(this.a._){return}e=this.a.Hb;if(!Kh((Gh(),e))){return}f=kh(Kh(e)).indexOf(wxb)!=-1;b=e.getAttribute($ub)||'';if(jfb(b.substr(0,20),Twb)){return}b.indexOf(xxb)!=-1&&(b=wfb(b,0,b.indexOf(' cell')));if(jfb(b,zxb)){e=xj(this.a.Gb);b=e.getAttribute($ub)||''}else if(ZF(this.a.Gb)==16&&f){AT(this.a.wb,b);try{c=this.a.wb.a;d=this.a.wb.b;if(c==0||d==0){return}e=iV(this.a,T0(this.a.Gb),U0(this.a.Gb),WU(this.a,c,d)).d;b=e.getAttribute($ub)||'';b.indexOf(xxb)!=-1&&(b=wfb(b,0,b.indexOf(' cell')))}catch(a){a=_D(a);if(Zq(a,82)){Nrb(this.a.U,'SheetWidget:onSheetMouseOverOrOut: JSE while trying to find real event target, className:'+b)}else if(Zq(a,33)){Orb(this.a.U,'SheetWidget:onSheetMouseOverOrOut: IOOBE while trying to find correct event target, className:'+b)}else throw aE(a)}}AT(this.a.wb,b);if(jfb(b,Cwb)||jfb(b,Dwb)||jfb(b,this.a.j)||qU(this.a,b)||rU(this.a,b)){IX(this.a,this.a.Gb,e)}else{if(!this.a.o&&this.a.q.M&&b.indexOf('comment')==-1){aG(this.a.zc);KN(this.a.q);this.a.j=null;this.a.k=-1;this.a.n=-1}}if(f&&!!this.a.s&&Tib(this.a.s,b)){KX(this.a,ZF(this.a.Gb),this.a.wb.a,this.a.wb.b,Sib(this.a.s,b));return}else YJ(this.a.qb)&&fN(this.a.qb,false)};var Px=Xdb(Hwb,'SheetWidget/2',336);GE(337,45,{},LY);_.ld=function MY(){var a,b,c,d,e;Ah(this.a.zc,((this.a.zc.scrollTop||0)|0)+(this.a.Y/2|0));zh(this.a.zc,qh(this.a.zc)+(this.a.X/2|0));dW(this.a);d=this.a.A;e=this.a.B;this.a.X<0?(d=hh(this.a.zc)+5):this.a.X>0&&(d=ih(this.a.zc)-25);this.a.Y<0?(e=jh(this.a.zc)+5):this.a.Y>0&&(e=gh(this.a.zc)-25);if(this.a.Y!=0&&((this.a.zc.scrollTop||0)|0)==0){c=new f2;c2(c,this.a.Gc);b=jh(this.a.Gc)+c.d[0]+5;this.a.B>b?(e=this.a.B):(e=b)}if(this.a.X!=0&&qh(this.a.zc)==0){c=new f2;c2(c,this.a.Gc);a=hh(this.a.Gc)+c.d[3]+5;this.a.A>a?(d=this.a.A):(d=a)}yV(this.a,d,e)};var Qx=Xdb(Hwb,'SheetWidget/3',337);GE(338,45,{},PY);_.ld=function QY(){NY(this,this.a.db,this.a.zb,this.a.bb,this.a.xb);NY(this,0,this.a.Tc,0,this.a.ob);NY(this,0,this.a.Tc,this.a.bb,this.a.xb);NY(this,this.a.db,this.a.zb,0,this.a.ob);OY(this,this.a.db,this.a.zb,this.a.bb,this.a.xb);OY(this,0,this.a.Tc,0,this.a.ob);OY(this,0,this.a.Tc,this.a.bb,this.a.xb);OY(this,this.a.db,this.a.zb,0,this.a.ob)};var Rx=Xdb(Hwb,'SheetWidget/4',338);GE(339,1,mwb,RY);_.Ld=function SY(){this.a.Db&&cW(this.a)};var Sx=Xdb(Hwb,'SheetWidget/5',339);GE(340,1,mwb,TY);_.Ld=function UY(){BW(this.a)};var Tx=Xdb(Hwb,'SheetWidget/6',340);GE(341,1,mwb,VY);_.Ld=function WY(){this.a.Lb==0&&Yg(this.a.Mb)&&(this.a.Lb=(this.a.Mb.offsetWidth||0)|0);gY(this.a);LX(this.a);OX(this.a);JW(this.a,this.b,this.c);HW(this.a);u_(this.a.a,this.a.db,this.a.zb,this.a.bb,this.a.xb);DW(this.a);IW(this.a);NX(this.a);bY(this.a);CW(this.a);this.a.Db=true};_.b=0;_.c=0;var Ux=Xdb(Hwb,'SheetWidget/7',341);GE(342,1,twb,YY);_.Ee=function ZY(a){var b,c,d,e,f,g;c=ZF(a.d);f=a.d;g=kY(f);b='';!!g&&g.nodeType==1&&(b=(Gh(),g).getAttribute($ub)||'');$g(ie(this.a),kY(f))&&(_vb==c||4==c||8==c||2==c||1==c)&&aX(this.a,true);if((this.a.ac||this.a.bc)&&c==64){if(this.a.$b!=-1){tV(this.a,T0(f),U0(f))}else if(this.a._b!=-1){xV(this.a,T0(f),U0(f))}else{this.a.ac=false;this.a.bc=false}a.a=true}else if(c==8&&XY(this,g)){if(this.a.ac||this.a.bc||jfb(b,iyb)||jfb(b,jyb)){this.a.L=true;this.a.jc=true;this.a.ac=false;this.a.bc=false;vT(this.a.Wb);fN(this.a.Xb,false);a.a=true;if(this.a.$b!=-1){th(this.a.Gc,hyb);xX(this.a,T0(a.d))}else if(this.a._b!=-1){th(this.a.Gc,gyb);zX(this.a,U0(a.d))}}}else{if($g(ie(this.a),g)){if(c==1){d=zT(b);if(d==1||d==2){e=BT(b);d==1?q_(this.a.a,e,!!(Gh(),f).shiftKey,!!f.metaKey||!!f.ctrlKey):d_(this.a.a,e,!!(Gh(),f).shiftKey,!!f.metaKey||!!f.ctrlKey);a.a=true;this.a.zc.focus()}}else if(c==4&&XY(this,g)){if(jfb(b,iyb)){b=kh(Kh((Gh(),g)));d=zT(b);if(d==1){d=BT(b);this.a.jc=false;vX(this.a,d-1,T0(f),U0(f))}else if(d==2){d=BT(b);this.a.L=false;tX(this.a,d-1,T0(f),U0(f))}a.a=true}else if(jfb(b,jyb)){b=kh(Kh((Gh(),g)));d=zT(b);if(d==1){d=BT(b);this.a.jc=false;vX(this.a,d,T0(f),U0(f))}else if(d==2){d=BT(b);this.a.L=false;tX(this.a,d,T0(f),U0(f))}a.a=true}}else if(c==2&&XY(this,g)){if(jfb(b,iyb)){b=kh(Kh((Gh(),g)));d=zT(b);if(d==1){d=BT(b)-1;while(Q$(this.a.a,d)&&d>0){--d}d>0&&r_(this.a.a,d)}else if(d==2){d=BT(b)-1;while(P$(this.a.a,d)&&d>0){--d}d>0&&e_(this.a.a,d)}a.a=true}else if(jfb(b,jyb)){b=kh(Kh((Gh(),g)));d=zT(b);if(d==1){d=BT(b);while(Q$(this.a.a,d)&&d>0){--d}d>0&&r_(this.a.a,d)}else if(d==2){d=BT(b);while(P$(this.a.a,d)&&d>0){--d}d>0&&e_(this.a.a,d)}a.a=true}}}}};var Vx=Xdb(Hwb,'SheetWidget/8',342);GE(343,1,kyb,$Y);_.se=function _Y(a){var b,c,d,e;if(this.a.a.R){e=kY(a.a);b=(Gh(),e).getAttribute($ub)||'';c=zT(b);if(c==1||c==2){d=BT(b);c==1?s_(this.a.a,a.a,d):f_(this.a.a,a.a,d)}!!a.a&&Dj(a.a);Ej(a.a)}};var Wx=Xdb(Hwb,'SheetWidget/9',343);GE(53,1,{53:1},aZ);_.Zc=function bZ(a){if(a==null||!Zq(a,53)){return false}return this.b==a.b&&this.a==a.a};_._c=function cZ(){var a;a=this.b+((this.a+1)/2|0);return 31*(this.a+a*a)};_.a=0;_.b=0;var Xx=Xdb(Hwb,'SheetWidget/CellCoord',53);GE(347,1,{},dZ);_.gd=function eZ(a){VV(this.a)};var Yx=Xdb(Hwb,'SheetWidget/lambda$0$Type',347);GE(119,13,{18:1,14:1,9:1,16:1,19:1,12:1,13:1,119:1},fZ);_.b=false;var _x=Xdb(Hwb,'Slot',119);GE(562,1,myb,hZ);_.ze=function iZ(a){gZ(this.b,this.a,a)};var $x=Xdb(Hwb,'Slot/lambda$0$Type',562);GE(127,1,nyb);_.ad=function lZ(){return 'Action [owner='+this.g+', iconUrl='+this.f+', caption='+this.e+']'};_.e='';_.f=null;var Mz=Xdb(Nwb,'Action',127);GE(248,127,nyb,nZ);_.Ld=function oZ(){mZ(this)};_.a='';_.c=0;var by=Xdb(Hwb,'SpreadsheetAction',248);GE(196,1,{196:1,3:1},pZ);_.type=0;var ay=Xdb(Hwb,'SpreadsheetActionDetails',196);var cy=Zdb(Hwb,'SpreadsheetClientRpc');GE(283,157,fxb,BZ);_.yf=function FZ(){return !this.L&&(this.L=new m1),this.L};_.uf=function GZ(){return !this.L&&(this.L=new m1),this.L};_.zf=function HZ(){return !this.D&&(this.D=new S0),this.D};_.sf=function CZ(){return new m1};_.xf=function DZ(){return new S0};_.tf=function EZ(a){return this.j};_.vf=function IZ(a){vZ(this,a)};var ky=Xdb(Hwb,'SpreadsheetConnector',283);GE(284,1,{732:1,3:1},TZ);var ey=Xdb(Hwb,'SpreadsheetConnector/1',284);GE(285,1,{},VZ);var dy=Xdb(Hwb,'SpreadsheetConnector/1/1',285);GE(286,1,{},ZZ);var fy=Xdb(Hwb,'SpreadsheetConnector/4',286);GE(287,1,kyb,$Z);_.se=function _Z(a){!!a.a&&Dj(a.a);Ej(a.a)};var gy=Xdb(Hwb,'SpreadsheetConnector/5',287);GE(288,1,mwb,a$);_.Ld=function b$(){uZ(this.a,this.b)};var hy=Xdb(Hwb,'SpreadsheetConnector/6',288);GE(290,1,{},e$);var iy=Xdb(Hwb,'SpreadsheetConnector/7',290);GE(289,1,{},f$);_.Df=function g$(a,b){tZ(this.a,this.b,a,b)};var jy=Xdb(Hwb,'SpreadsheetConnector/lambda$0$Type',289);GE(652,1,{743:1,722:1,721:1,250:1,27:1},h$);_.we=function i$(a){!!this.b&&pb(this.b)};_.xe=function j$(a){!!this.b&&pb(this.b)};_.ye=function k$(a){var b,c,d,e,f,g,h,i,j,k,l;e=a.a;j=(Gh(),Fh).Wd(e);!j&&(j=ie(this.a));d=j;k=e.targetTouches;l=null;!!k&&k.length>0&&(l=k[0]);h=l?ai(l.screenX||0):ai(e.screenX||0);i=!l?ai(e.screenY||0):ai(l.screenY||0);b=l?ai(l.clientX||0):ai(e.clientX||0);c=l?ai(l.clientY||0):ai(e.clientY||0);f=l?l.target:Fh.Wd(e);g=f?f:null;this.b=new l$(h,i,b,c,g,d);qb(this.b,750)};var my=Xdb(Hwb,'SpreadsheetContextMenuPolyfill',652);GE(653,45,{},l$);_.ld=function m$(){var a;a=Xi($doc,this.e,this.f,this.a,this.b,this.d);fh(this.c,a)};_.a=0;_.b=0;_.e=0;_.f=0;var ly=Xdb(Hwb,'SpreadsheetContextMenuPolyfill/1',653);GE(400,136,iwb);_.d=0;_.f=0;var Wz=Xdb(Nwb,'VContextMenu',400);GE(401,400,iwb,s$);_.kf=function t$(){var a;return a=(JF(),JF(),$doc.getElementById(Owb)),!a?(QK(),$doc.body):a};var ny=Xdb(Hwb,'SpreadsheetOverlay/SpreadsheetContextMenu',401);var py=Zdb(Hwb,'SpreadsheetServerRpc');var Fy=Zdb(vyb,'Focusable');GE(28,692,{18:1,14:1,9:1,16:1,179:1,19:1,12:1,13:1,28:1},S0);_.rf=function V0(a,b,c){gbb(this.W,a,b,c)};_.pd=function W0(a){$_(this,a)};_.rd=function X0(a){E0(this,a)};_.a=0;_.c=false;_.d=false;_.e=false;_.i=0;_.j=0;_.o=false;_.q=0;_.r=0;_.t=false;_.B=false;_.C=false;_.D=true;_.F=true;_.K=true;_.L=0;_.O=0;_.P=false;_.T=false;_.X=0;_.Y=0;_.Z=false;var xy=Xdb(Hwb,'SpreadsheetWidget',28);GE(298,1,Jtb,$0);var qy=Xdb(Hwb,'SpreadsheetWidget/1',298);GE(299,45,{},_0);_.ld=function a1(){};var ry=Xdb(Hwb,'SpreadsheetWidget/2',299);GE(300,1,myb,b1);_.ze=function c1(a){if(a.a){kX(this.b.V,this.a,this.c)}else{this.a=qh(this.b.V.zc);this.c=(this.b.V.zc.scrollTop||0)|0}};_.a=0;_.c=0;var sy=Xdb(Hwb,'SpreadsheetWidget/3',300);GE(301,1,mwb,d1);_.Ld=function e1(){kX(this.a.V,this.b,this.c)};_.b=0;_.c=0;var ty=Xdb(Hwb,'SpreadsheetWidget/4',301);GE(302,1,mwb,f1);_.Ld=function g1(){var a,b,c;B$(this.a);if(this.b){b=0;while(b<this.b.a.length){c=Qkb(this.b,b);iU(this.a.V,c);a=WU(this.a.V,c.col1,c.row1);!!a&&MM(a,a.o,a.b,false);++b}tU(this.a.V)}!this.b?(this.a.J=null):(this.a.J=new Ykb(this.b))};var uy=Xdb(Hwb,'SpreadsheetWidget/5',302);GE(303,45,{},h1);_.ld=function i1(){this.a.K=true};var vy=Xdb(Hwb,'SpreadsheetWidget/6',303);GE(200,1,mwb,j1);_.Ld=function k1(){if(!this.a.c){wab(this.a.W,this.a.V.sc,this.a.V.rc,this.c);y$(this.a,this.c,this.b)}};_.b=false;var wy=Xdb(Hwb,'SpreadsheetWidget/7',200);GE(238,1,{238:1,3:1},l1);_.collapsed=false;_.endIndex=0;_.level=0;_.startIndex=0;_.uniqueIndex=0;var yy=Xdb(yyb,'GroupingData',238);GE(128,67,{67:1,75:1,128:1,3:1});var bB=Xdb(zyb,'TabIndexState',128);GE(48,128,{48:1,67:1,75:1,128:1,3:1},m1);_.d=null;_.f=false;_.g=0;_.j=0;_.k=200;_.n=null;_.p=null;_.q=0;_.r=0;_.s=true;_.t=true;_.u=false;_.v=null;_.w=null;_.B=0;_.G='Invalid formula';_.H=true;_.I=true;_.J=null;_.K=null;_.O=false;_.P=200;_.R=false;_.S=0;_.U=null;_.V=0;_.W=1;_.X=null;_.Y=false;_.Z=null;_.$=false;_.ab=0;_.cb=false;_.db=false;var zy=Xdb(yyb,'SpreadsheetState',48);var n1;GE(253,1,{},x1);var v1;var Ay=Xdb(vyb,'ApplicationConfiguration',253);GE(212,1,{14:1,212:1},B1);_.ud=function C1(a){};_.b=null;var By=Xdb(vyb,'ApplicationConnection',212);GE(46,1,{},O1);_.b=false;var D1=null,E1;var Cy=Xdb(vyb,'BrowserInfo',46);GE(118,1,{},T1);var Dy=Xdb(vyb,'ComputedStyle',118);GE(691,690,{});var Ly=Xdb(Hyb,'AbstractServerConnectorEvent',691);var W1;GE(487,1,{},Z1);var Ey=Xdb(vyb,'ConnectorMap',487);GE(85,1,{},f2);_.b=-1;_.e=-1;var Hy=Xdb(vyb,'MeasuredSize',85);GE(547,1,{},i2);var Gy=Xdb(vyb,'MeasuredSize/MeasureResult',547);GE(380,1,{},n2);var Iy=Xdb(vyb,'ResourceLoader',380);GE(416,356,{},t2);_.a=0;var Ky=Xdb(vyb,'VSchedulerImpl',416);GE(417,1,mwb,u2);_.Ld=function v2(){this.a.a--};var Jy=Xdb(vyb,'VSchedulerImpl/lambda$0$Type',417);var M2;GE(199,691,{},U2);_.le=function V2(a){a.vf(this)};_.ne=function X2(){return P2};_.me=function W2(){return P2};_.Hf=function Y2(a){return S2(this,a)};_.b=false;var P2;var My=Xdb(Hyb,'StateChangeEvent',199);GE(668,1,{},$2);var Ny=Xdb(Hyb,'URLReference_Serializer',668);GE(170,1,{170:1});_.d=0;var Oy=Xdb(Kyb,'AsyncBundleLoader',170);GE(371,1,{});var b3;var tz=Xdb(Kyb,'ConnectorBundleLoader',371);GE(372,1,Uwb,h3);_.re=function i3(a){Oe(this.a.e)};var Py=Xdb(Kyb,'ConnectorBundleLoader/lambda$0$Type',372);GE(373,1,Lyb,j3);_.ye=function k3(a){Oe(this.a.e)};var Qy=Xdb(Kyb,'ConnectorBundleLoader/lambda$1$Type',373);GE(78,371,{},l3);var sz=Xdb(Kyb,'ConnectorBundleLoaderXXImpl',78);GE(488,170,{170:1},m3);var rz=Xdb(Kyb,'ConnectorBundleLoaderXXImpl/1',488);GE(489,1,{},s3);var qz=Xdb(Kyb,'ConnectorBundleLoaderXXImpl/1/1',489);GE(490,1,Mzb,t3);_.If=function u3(a,b){return new k6};var bz=Xdb(Kyb,'ConnectorBundleLoaderXXImpl/1/1/1',490);GE(499,1,Mzb,v3);_.If=function w3(a,b){return new rdb};var Ry=Xdb(Kyb,'ConnectorBundleLoaderXXImpl/1/1/10',499);GE(500,1,Mzb,x3);_.If=function y3(a,b){return new sdb};var Sy=Xdb(Kyb,'ConnectorBundleLoaderXXImpl/1/1/11',500);GE(710,1,Mzb);_.If=function z3(a,b){var c,d,e,f,g;c=[];for(e=b,f=0,g=e.length;f<g;++f){d=e[f];c.push(d)}return this.Jf(a,c)};var uz=Xdb(Kyb,'JsniInvoker',710);GE(501,710,Mzb,A3);_.Jf=function B3(a,b){a.Cf();return null};var Ty=Xdb(Kyb,'ConnectorBundleLoaderXXImpl/1/1/12',501);GE(502,710,Mzb,C3);_.Jf=function D3(a,b){a.Af();return null};var Uy=Xdb(Kyb,'ConnectorBundleLoaderXXImpl/1/1/13',502);GE(503,710,Mzb,E3);_.Jf=function F3(a,b){a.Lf();return null};var Vy=Xdb(Kyb,'ConnectorBundleLoaderXXImpl/1/1/14',503);GE(504,710,Mzb,G3);_.Jf=function H3(a,b){a.Mf();return null};var Wy=Xdb(Kyb,'ConnectorBundleLoaderXXImpl/1/1/15',504);GE(505,710,Mzb,I3);_.Jf=function J3(a,b){a.Nf();return null};var Xy=Xdb(Kyb,'ConnectorBundleLoaderXXImpl/1/1/16',505);GE(506,1,Mzb,K3);_.If=function L3(a,b){return new M3};var Zy=Xdb(Kyb,'ConnectorBundleLoaderXXImpl/1/1/17',506);GE(507,1,{},M3);var Yy=Xdb(Kyb,'ConnectorBundleLoaderXXImpl/1/1/17/1',507);GE(508,1,Mzb,N3);_.If=function O3(a,b){return new $2};var $y=Xdb(Kyb,'ConnectorBundleLoaderXXImpl/1/1/18',508);GE(509,1,Mzb,P3);_.If=function Q3(a,b){return new R3};var az=Xdb(Kyb,'ConnectorBundleLoaderXXImpl/1/1/19',509);GE(510,1,{},R3);var _y=Xdb(Kyb,'ConnectorBundleLoaderXXImpl/1/1/19/1',510);GE(491,1,Mzb,S3);_.If=function T3(a,b){return new A6};var iz=Xdb(Kyb,'ConnectorBundleLoaderXXImpl/1/1/2',491);GE(511,1,Mzb,U3);_.If=function V3(a,b){return new W3};var dz=Xdb(Kyb,'ConnectorBundleLoaderXXImpl/1/1/20',511);GE(512,1,{},W3);var cz=Xdb(Kyb,'ConnectorBundleLoaderXXImpl/1/1/20/1',512);GE(513,1,Mzb,X3);_.If=function Y3(a,b){return new Z3};var fz=Xdb(Kyb,'ConnectorBundleLoaderXXImpl/1/1/21',513);GE(514,1,{},Z3);var ez=Xdb(Kyb,'ConnectorBundleLoaderXXImpl/1/1/21/1',514);GE(515,1,Mzb,$3);_.If=function _3(a,b){return new a4};var hz=Xdb(Kyb,'ConnectorBundleLoaderXXImpl/1/1/22',515);GE(516,1,{},a4);var gz=Xdb(Kyb,'ConnectorBundleLoaderXXImpl/1/1/22/1',516);GE(492,1,Mzb,b4);_.If=function c4(a,b){return new bdb};var jz=Xdb(Kyb,'ConnectorBundleLoaderXXImpl/1/1/3',492);GE(493,1,Mzb,d4);_.If=function e4(a,b){return new jdb};var kz=Xdb(Kyb,'ConnectorBundleLoaderXXImpl/1/1/4',493);GE(494,1,Mzb,f4);_.If=function g4(a,b){return new kdb};var lz=Xdb(Kyb,'ConnectorBundleLoaderXXImpl/1/1/5',494);GE(495,1,Mzb,h4);_.If=function i4(a,b){return new ldb};var mz=Xdb(Kyb,'ConnectorBundleLoaderXXImpl/1/1/6',495);GE(496,1,Mzb,j4);_.If=function k4(a,b){return new mdb};var nz=Xdb(Kyb,'ConnectorBundleLoaderXXImpl/1/1/7',496);GE(497,1,Mzb,l4);_.If=function m4(a,b){return new odb};var oz=Xdb(Kyb,'ConnectorBundleLoaderXXImpl/1/1/8',497);GE(498,1,Mzb,n4);_.If=function o4(a,b){return new qdb};var pz=Xdb(Kyb,'ConnectorBundleLoaderXXImpl/1/1/9',498);GE(72,1,{72:1},r4);_.Zc=function s4(a){var b;if(a===this){return true}else if(Zq(a,72)){b=a;return jfb(b.b.b+'.'+b.a,this.b.b+'.'+this.a)}else{return false}};_._c=function t4(){return Dtb(this.b.b+'.'+this.a)};_.ad=function u4(){return this.b.b+'.'+this.a};var vz=Xdb(Kyb,'Method',72);GE(80,21,{80:1,3:1,21:1,20:1},v4);var wz=Xdb(Kyb,'NoDataException',80);GE(84,1,{84:1},x4,y4);var xz=Xdb(Kyb,'OnStateChangeMethod',84);GE(5,1,{5:1},A4,B4);_.Zc=function D4(a){var b;if(a===this){return true}else if(Zq(a,5)){b=a;return jfb(b.b,this.b)}else{return false}};_._c=function E4(){return Dtb(this.b)};_.ad=function F4(){return this.b};var zz=Xdb(Kyb,'Type',5);GE(419,1,{},O4);_.Kf=function U4(a,b,c){this.c[Rdb(a),a.k][b]=c};var yz=Xdb(Kyb,'TypeDataStore',419);GE(375,1,Nzb);_.se=function X4(a){eQ(this.c,this.b)&&!!a.a&&Dj(a.a)};_.ue=function Y4(a){this.d=z2(a.a);this.g=false;this.e=$F(this.f)};_.ve=function Z4(a){eQ(this.c,this.b)&&this.g&&!!this.d&&z2(a.a)==this.d&&n5(this,a.a);this.g=false;this.d=null};_.g=false;var Bz=Xdb(Nwb,'AbstractClickEventHandler',375);GE(379,1,twb,$4);_.Ee=function _4(a){W4(this.a,a)};var Az=Xdb(Nwb,'AbstractClickEventHandler/lambda$0$Type',379);GE(292,45,{},a5);_.ld=function b5(){this.a.tf(RA).tg();x2();this.a.s=true};var Cz=Xdb(Nwb,'AbstractComponentConnector/2',292);GE(294,1,{721:1,27:1},d5);_.xe=function e5(a){c5(this,a)&&pQ(this.a)};var Dz=Xdb(Nwb,'AbstractComponentConnector/3',294);GE(291,1,kyb,f5);_.se=function g5(a){sQ(this.a,a)};var Ez=Xdb(Nwb,'AbstractComponentConnector/lambda$0$Type',291);GE(293,1,Lyb,h5);_.ye=function i5(a){tQ(this.a,this.b,a)};var Fz=Xdb(Nwb,'AbstractComponentConnector/lambda$1$Type',293);GE(295,1,{722:1,27:1},j5);_.we=function k5(a){uQ(this.a,a)};var Gz=Xdb(Nwb,'AbstractComponentConnector/lambda$2$Type',295);GE(296,199,{},l5);_.Hf=function m5(a){return true};var Iz=Xdb(Nwb,'AbstractConnector/FullStateChangeEvent',296);GE(374,157,fxb);var Lz=Xdb(Nwb,'AbstractSingleComponentContainerConnector',374);GE(217,375,Nzb);var Nz=Xdb(Nwb,'ClickEventHandler',217);GE(670,1,twb,E5);_.Ee=function H5(a){var b;b=ZF(a.d);if(this.r){a.a=true;b==_vb&&u5(this,a.d);return}switch(b){case awb:if(!a.a){y5(this,a.d);this.j&&(a.a=true)}break;case Rtb:case bwb:if(!a.a){this.j&&(a.a=true);x5(this)}break;case 64:this.j&&(a.a=true);break;default:Hrb(Qrb((Rdb(Qz),Qz.k)),'Non touch event:'+Cj(a.d));a.a=true;}};_.a=0;_.c=0;_.e=0;_.f=0;_.j=false;_.k=0;_.n=0;_.o=0;_.r=false;var o5=false,p5;var Qz=Xdb(Nwb,'TouchScrollDelegate',670);GE(672,133,{},J5);_.bd=function K5(a){return 1+$wnd.Math.pow(a-1,3)};_.cd=function L5(){var a;a=er(this.b-this.a.e);this.a.c-=a;w5(this.a);this.a.r=false};_.dd=function M5(){I5(this,1+$wnd.Math.pow(0,3));this.a.r=false;A5(this.a)};_.fd=function N5(a){I5(this,a)};_.b=0;_.c=0;var Oz=Xdb(Nwb,'TouchScrollDelegate/1',672);GE(671,1,Lyb,R5);_.ye=function S5(a){z5(this.a,a)};_.b=false;var Pz=Xdb(Nwb,'TouchScrollDelegate/TouchScrollHandler',671);GE(404,1,wwb,T5);_.Ae=function U5(a){var b;b=A2();if(!!this.a.c&&(!b||$g(ie(this.a.e),b)||nf((QK(),$doc.body),b))){this.a.c.focus();this.a.c=null}};var Rz=Xdb(Nwb,'VContextMenu/1',404);GE(402,139,{738:1,737:1,18:1,27:1,14:1,9:1,16:1,19:1,12:1,13:1},W5);_.Te=function X5(){(VL(),VL(),TL).bf((JF(),this.Yc))};_.te=function Y5(a){zj(a.a)==27&&fN(this.a,false)};var Sz=Xdb(Nwb,'VContextMenu/CMenuBar',402);GE(403,1,mwb,Z5);_.Ld=function $5(){n$(this.a)};var Tz=Xdb(Nwb,'VContextMenu/lambda$0$Type',403);GE(406,1,{},_5);_.Xe=function a6(a,b){o$(this.a,a,b)};var Uz=Xdb(Nwb,'VContextMenu/lambda$1$Type',406);GE(405,1,mwb,b6);_.Ld=function c6(){p$(this.a)};var Vz=Xdb(Nwb,'VContextMenu/lambda$2$Type',405);GE(120,145,Wub,d6);_.xd=function e6(a){Me(this,a);JF();if($G((Gh(),a).type)==Xvb){p2(this);a.stopPropagation();return}};_.rd=function f6(a){(JF(),this.Yc).style[Rub]=a;a==null||a.length==0?Be(this.Yc,Qzb,true):Be(this.Yc,Qzb,false)};var Xz=Xdb(Nwb,'VLabel',120);GE(93,1,{},h6);_.b=0;var Zz=Xdb(Nwb,'VLazyExecutor',93);GE(409,45,{},i6);_.ld=function j6(){this.a.c=null;this.a.a.Ld()};var Yz=Xdb(Nwb,'VLazyExecutor/1',409);GE(234,132,{18:1,724:1,27:1,14:1,9:1,735:1,60:1,16:1,31:1,19:1,12:1,13:1,234:1},k6);_.zd=function l6(){if(((JF(),this.Yc).ownerDocument.body.className||'').indexOf('v-generated-body')==-1){this.a=new p6(this);qb(this.a,evb)}};_.Be=function m6(a){FG();rj($doc).clientWidth|0;rj($doc).clientHeight|0};_.Ad=function n6(){if(this.a){pb(this.a);this.a=null}};_.Cd=function o6(a){Bh((JF(),this.Yc),a)};var bA=Xdb(Nwb,'VUI',234);GE(554,45,{},p6);_.ld=function q6(){FG();rj($doc).clientWidth|0;rj($doc).clientHeight|0;qb(this.a.a,evb)};var _z=Xdb(Nwb,'VUI/1',554);GE(553,1,mwb,r6);_.Ld=function s6(){FG();rj($doc).clientWidth|0;rj($doc).clientHeight|0};var aA=Xdb(Nwb,'VUI/lambda$0$Type',553);GE(137,374,{27:1,100:1,123:1,137:1,3:1},A6);_.yf=function B6(){return !this.L&&(this.L=_P(this)),this.L};_.uf=function C6(){return !this.L&&(this.L=_P(this)),this.L};_.zf=function D6(){return x6(this)};_.Bf=function E6(){return true};_.vf=function F6(a){var b;vQ(this,a);if(a.Hf(Kzb)){null.tg((!this.L&&(this.L=_P(this)),this.L).q.a);null.tg((!this.L&&(this.L=_P(this)),this.L).q.c);null.tg((!this.L&&(this.L=_P(this)),this.L).q.d);null.tg((!this.L&&(this.L=_P(this)),this.L).q.e);null.tg((!this.L&&(this.L=_P(this)),this.L).q.b)}if(a.Hf(kzb)){null.tg((!this.L&&(this.L=_P(this)),this.L).c.a);null.tg((!this.L&&(this.L=_P(this)),this.L).c.b);null.tg((!this.L&&(this.L=_P(this)),this.L).c.c)}a.Hf(uzb)&&u6(this);if(a.Hf('pageState.title')){b=(!this.L&&(this.L=_P(this)),this.L).g.b;b!=null&&(FG(),$doc.title=b,undefined)}a.Hf(wzb)&&null.tg((!this.L&&(this.L=_P(this)),this.L).j.b!=(Ecb(),Ccb));a.Hf(Azb)&&null.tg();a.Hf(szb)&&DN(this.F,(!this.L&&(this.L=_P(this)),this.L).f)};_.Lf=function G6(){var a,b,c,d;c=this.a;a=(!this.L&&(this.L=_P(this)),this.L).o;d=w6();b=w6();if(tdb(c,a)){if(a==null){return}!v6(d)?y6(this,null,a,null,b):sh(ie((!this.D&&(this.D=qQ(this)),this.D).Xc),a)||t6(this,a);return}Hrb(Qrb((Rdb(fA),fA.k)),'Changing theme from '+c+' to '+a);y6(this,c,a,d,b)};_.Mf=function H6(){null.tg((!this.L&&(this.L=_P(this)),this.L).p)};_.Nf=function I6(){cQ(this,_A).tg((!this.L&&(this.L=_P(this)),this.L).b)};var fA=Xdb(Szb,'UIConnector',137);GE(376,217,Nzb,J6);var eA=Xdb(Szb,'UIConnector/1',376);GE(377,45,{},K6);_.ld=function L6(){if(dQ(this.a).i<0){pb(this.a.b);this.a.b=null;return}cQ(this.a,fB).tg();null.tg()};var cA=Xdb(Szb,'UIConnector/10',377);GE(378,1,{},M6);_.Ff=function N6(a){Orb(Qrb((Rdb(fA),fA.k)),'Could not load theme from '+w6())};_.Gf=function O6(a){Hrb(Qrb((Rdb(fA),fA.k)),'Loading of '+this.b+' from '+this.c+' completed');!!this.d&&ah(Xg(this.d),this.d);t6(this.a,this.b)};var dA=Xdb(Szb,'UIConnector/11',378);GE(368,1,{},P6);_.Ef=function Q6(a){var b;b=q1(a);if(b.indexOf(Iwb)!=-1){s1(ie(this.a),this);me(this.a,Iwb)}};var gA=Xdb(Mwb,'Overlay/1',368);GE(369,1,{},R6);_.Ef=function S6(a){if(q1(a).indexOf(Iwb)!=-1){s1(ie(this.a),this);iN(this.a,this.b)}};_.b=false;var hA=Xdb(Mwb,'Overlay/2',369);GE(370,1,{},T6);_.Ef=function U6(a){var b;b=q1(a);if(b.indexOf(Jwb)!=-1){s1(ie(this.a),this);me(this.a,Iwb);me(this.a,Jwb);iN(this.a,this.b)}};_.b=false;var iA=Xdb(Mwb,'Overlay/3',370);GE(366,1,{},X6);_.a=0;_.b=0;_.c=0;_.d=0;var jA=Xdb(Mwb,'Overlay/PositionAndSize',366);GE(367,133,{},Y6);_.fd=function Z6(a){hN(this.a,a)};var kA=Xdb(Mwb,'Overlay/ResizeAnimation',367);GE(107,1,{},d7);_.Of=function e7(a){return KE(a)};var mA=Xdb(Tzb,'Parser/10methodref$toString$Type',107);GE(316,1,{},f7);_.Of=function g7(a){return KE(a)};var nA=Xdb(Tzb,'Parser/11methodref$toString$Type',316);GE(319,1,{},h7);_.Of=function i7(a){return KE(a)};var oA=Xdb(Tzb,'Parser/12methodref$toString$Type',319);GE(203,1,{},j7);_.Pf=function k7(){return new l1};var pA=Xdb(Tzb,'Parser/2methodref$ctor$Type',203);GE(204,1,{},l7);_.Of=function m7(a){return Neb(peb(a))};var qA=Xdb(Tzb,'Parser/3methodref$valueOf$Type',204);GE(205,1,{},n7);_.Of=function o7(a){return KE(a)};var rA=Xdb(Tzb,'Parser/4methodref$toString$Type',205);GE(206,1,{},p7);_.Of=function q7(a){return Neb(peb(a))};var sA=Xdb(Tzb,'Parser/5methodref$valueOf$Type',206);GE(105,1,{},r7);_.Of=function s7(a){return KE(a)};var tA=Xdb(Tzb,'Parser/6methodref$toString$Type',105);GE(158,1,{},t7);var uA=Xdb(Tzb,'Parser/7methodref$intValue$Type',158);GE(315,1,{},u7);var vA=Xdb(Tzb,'Parser/8methodref$doubleValue$Type',315);GE(106,1,{},v7);_.Of=function w7(a){return KE(a)};var wA=Xdb(Tzb,'Parser/9methodref$toString$Type',106);GE(131,1,{},x7);_.Of=function y7(a){return ar(a)?udb(a):oeb(KE(a))};var xA=Xdb(Tzb,'Parser/lambda$12$Type',131);GE(317,1,{},z7);_.Pf=function A7(){return new SP};var yA=Xdb(Tzb,'Parser/lambda$17$Type',317);GE(108,1,{},B7);_.Pf=function C7(){return new $N};var zA=Xdb(Tzb,'Parser/lambda$18$Type',108);GE(318,1,{},D7);_.Pf=function E7(){return new pZ};var AA=Xdb(Tzb,'Parser/lambda$19$Type',318);GE(69,1,{},F7);_.Of=function G7(a){return $6(this.a,a)};var BA=Xdb(Tzb,'Parser/lambda$20$Type',69);GE(320,1,{},H7);_.Of=function I7(a){var b;return b=new WP,Object.assign(b,a),b};var CA=Xdb(Tzb,'Parser/lambda$22$Type',320);GE(207,1,{},J7);_.Of=function K7(a){return Neb(er(ar(a)?udb(a):oeb(KE(a))))};var DA=Xdb(Tzb,'Parser/lambda$6$Type',207);GE(208,1,{},L7);_.Of=function M7(a){return Neb(er(ar(a)?udb(a):oeb(KE(a))))};var EA=Xdb(Tzb,'Parser/lambda$7$Type',208);GE(209,1,{},N7);_.Of=function O7(a){return Neb(er(ar(a)?udb(a):oeb(KE(a))))};var FA=Xdb(Tzb,'Parser/lambda$9$Type',209);GE(687,1,{},T7);_.addPopupButton=function U7(a){var b,c,d,e;d=b7(a);c=d.sheet+'_'+d.row+'_'+d.col;if(Tib(this.c,c)){e=Sib(this.c,c)}else{Vib(this.a,c,b=new NQ);Vib(this.c,c,e=(!b.D&&(b.D=new rR),b.D));Vib(this.b,c,d);MQ(b,this.e.j);hQ(b,cx,b.a);Ie((!b.D&&(b.D=new rR),b.D),b,(ln(),ln(),kn));fR((!b.D&&(b.D=new rR),b.D),b);kR(e,d.col);oR(e,d.row);lR(e,d.headerHidden);qR(e,this.f.V,ie(this.f.V));nR(e,d.popupWidth);mR(e,d.popupHeight)}hR(e,d.active);v$(this.f,e)};_.cellsUpdated=function V7(a){var b;b=sfb((Rdb(cy),cy.k),$tb,'.');JZ(bQ(this.e,b).Oe().Ze(),_6(a,new F7(new B7)))};_.closePopup=function W7(a,b){var c;c=Sib(this.c,H$(this.f)+'_'+a+'_'+b);!!c&&(fN(c.e,false),gR(c))};_.disconnected=function X7(){!!this.e&&wZ(this.e)};_.editCellComment=function Y7(a,b){s2((og(),ng),new mab(this,a,b))};_.invalidCellAddress=function Z7(){var a;a=sfb((Rdb(cy),cy.k),$tb,'.');ZO(rQ(bQ(this.e,a).Oe().Ze().a).u)};_.layout=function $7(){D_(this.f);C_(rQ(this.e))};_.load=function _7(){T$(this.f)};_.notifyStateChanges=function a8(a,b){var c,d,e,f,g,h;h={};for(e=a,f=0,g=e.length;f<g;++f){d=e[f];h[d]=''}c=new U2(h,b);P7(this,this.e,c);vZ(this.e,c)};_.onPopupButtonOpened=function b8(a,b,c,d){var e,f,g;e=ET(c,d);if(!e){return}f=new fab(Jh((Gh(),e)));JF();if(f.Yc){g=Sib(this.c,H$(this.f)+'_'+a+'_'+b);g.e.M||!!g.d&&GV(g.d,g.b,g.k)&&iR(g);g.c=f;Nkb(g.f,f);BL(g.i,f)}};_.refreshCellStyles=function c8(){s2((og(),ng),new kab(this))};_.relayout=function d8(){s2((og(),ng),new gab(this))};_.relayoutSheet=function e8(){D_(this.f)};_.removePopupButton=function f8(a){var b,c,d;d=b7(a);b=d.sheet+'_'+d.row+'_'+d.col;c=Sib(this.c,b);if(c){G_(this.f,c);Xib(this.c,b);Xib(this.a,b);Xib(this.b,b)}};_.resize=function g8(){R0(this.f)};_.setActionOnColumnHeaderCallback=function h8(a){Rab(this.e.j,a)};_.setActionOnCurrentSelectionCallback=function i8(a){Sab(this.e.j,a)};_.setActionOnRowHeaderCallback=function j8(a){Tab(this.e.j,a)};_.setCellAddedToSelectionAndSelectedCallback=function k8(a){Uab(this.e.j,a)};_.setCellCommentAuthors=function l8(a){dQ(this.e).a=c7(a,new v7,new d7)};_.setCellComments=function m8(a){dQ(this.e).b=c7(a,new v7,new d7)};_.setCellKeysToEditorIdMap=function n8(a){dQ(this.e).c=c7(a,new v7,new d7)};_.setCellRangePaintedCallback=function o8(a){Vab(this.e.j,a)};_.setCellRangeSelectedCallback=function p8(a){Wab(this.e.j,a)};_.setCellSelectedCallback=function q8(a){Xab(this.e.j,a)};_.setCellStyleToCSSStyle=function r8(a){dQ(this.e).d=c7(a,new l7,new n7)};_.setCellValueEditedCallback=function s8(a){Zab(this.e.j,a)};_.setCellsAddedToRangeSelectionCallback=function t8(a){$ab(this.e.j,a)};_.setClass=function u8(a){var b,c,d,e,f,g,h;for(d=this.d,f=0,h=d.length;f<h;++f){b=d[f];se(this.f,b,false)}this.d=a.length==0?gq(QB,_tb,2,0,6,1):ufb(a,' ',0);for(c=this.d,e=0,g=c.length;e<g;++e){b=c[e];se(this.f,b,true)}};_.setClearSelectedCellsOnCutCallback=function v8(a){_ab(this.e.j,a)};_.setColGroupingData=function w8(a){dQ(this.e).e=_6(a,new F7(new j7))};_.setColGroupingInversed=function x8(a){dQ(this.e).f=a};_.setColGroupingMax=function y8(a){dQ(this.e).g=a};_.setColW=function z8(a){dQ(this.e).i=lsb(wsb(new zsb(null,new rqb(_6(a,new x7))),new t7))};_.setCols=function A8(a){dQ(this.e).j=a};_.setColumnAddedToSelectionCallback=function B8(a){abb(this.e.j,a)};_.setColumnBufferSize=function C8(a){dQ(this.e).k=a};_.setColumnHeaderContextMenuOpenCallback=function D8(a){bbb(this.e.j,a)};_.setColumnIndexToStyleIndex=function E8(a){dQ(this.e).n=c7(a,new p7,new J7)};_.setColumnResizedCallback=function F8(a){cbb(this.e.j,a)};_.setColumnSelectedCallback=function G8(a){dbb(this.e.j,a)};_.setComponentIDtoCellKeysMap=function H8(a){dQ(this.e).o=c7(a,new v7,new d7)};_.setConditionalFormattingStyles=function I8(a){dQ(this.e).p=c7(a,new l7,new n7)};_.setContextMenuOpenOnSelectionCallback=function J8(a){ebb(this.e.j,a)};_.setDefColW=function K8(a){dQ(this.e).q=a};_.setDefRowH=function L8(a){dQ(this.e).r=a};_.setDeleteSelectedCellsCallback=function M8(a){fbb(this.e.j,a)};_.setDisplayGridlines=function N8(a){dQ(this.e).s=a};_.setDisplayRowColHeadings=function O8(a){dQ(this.e).t=a};_.setGroupingCollapsedCallback=function P8(a){hbb(this.e.j,a)};_.setHasActions=function Q8(a){dQ(this.e).u=a};_.setHeight=function R8(a){dQ(this.e).kb=a};_.setHiddenColumnIndexes=function S8(a){dQ(this.e).v=_6(a,new N7)};_.setHiddenRowIndexes=function T8(a){dQ(this.e).w=_6(a,new N7)};_.setHorizontalScrollPositions=function U8(a){dQ(this.e).A=lsb(wsb(new zsb(null,new rqb(_6(a,new x7))),new t7))};_.setHorizontalSplitPosition=function V8(a){dQ(this.e).B=a};_.setHyperlinksTooltips=function W8(a){dQ(this.e).C=c7(a,new v7,new d7)};_.setId=function X8(a){dQ(this.e).lb=a};_.setInfoLabelValue=function Y8(a){dQ(this.e).D=a};_.setInvalidFormulaCells=function Z8(a){dQ(this.e).F=new wob(_6(a,new f7))};_.setInvalidFormulaErrorMessage=function $8(a){dQ(this.e).G=a};_.setLevelHeaderClickedCallback=function _8(a){ibb(this.e.j,a)};_.setLinkCellClickedCallback=function a9(a){jbb(this.e.j,a)};_.setLockFormatColumns=function b9(a){dQ(this.e).H=a};_.setLockFormatRows=function c9(a){dQ(this.e).I=a};_.setLockedColumnIndexes=function d9(a){dQ(this.e).J=new wob(_6(a,new L7))};_.setLockedRowIndexes=function e9(a){dQ(this.e).K=new wob(_6(a,new L7))};_.setMergedRegions=function f9(a){dQ(this.e).L=_6(a,new F7(new z7))};_.setNamedRanges=function g9(a){dQ(this.e).M=_6(a,new r7)};_.setOnColumnAutofitCallback=function h9(a){kbb(this.e.j,a)};_.setOnConnectorInitCallback=function i9(a){lbb(this.e.j,a)};_.setOnPasteCallback=function j9(a){mbb(this.e.j,a)};_.setOnRedoCallback=function k9(a){nbb(this.e.j,a)};_.setOnRowAutofitCallback=function l9(a){obb(this.e.j,a)};_.setOnSheetScrollCallback=function m9(a){pbb(this.e.j,a)};_.setOnUndoCallback=function n9(a){qbb(this.e.j,a)};_.setOverlays=function o9(a){dQ(this.e).N=c7(a,new h7,new H7)};_.setPopupButtonClickCallback=function p9(a){rbb(this.e.j,a)};_.setPopupCloseCallback=function q9(a){sbb(this.e.j,a)};_.setProtectedCellWriteAttemptedCallback=function r9(a){tbb(this.e.j,a)};_.setReload=function s9(a){dQ(this.e).O=true};_.setResources=function t9(a,b){var c;c=_6(b,new r7);Pkb(c,new iab(this,a))};_.setRowAddedToRangeSelectionCallback=function u9(a){ubb(this.e.j,a)};_.setRowBufferSize=function v9(a){dQ(this.e).P=a};_.setRowGroupingData=function w9(a){dQ(this.e).Q=_6(a,new F7(new j7))};_.setRowGroupingInversed=function x9(a){dQ(this.e).R=a};_.setRowGroupingMax=function y9(a){dQ(this.e).S=a};_.setRowH=function z9(a){dQ(this.e).T=a7(a)};_.setRowHeaderContextMenuOpenCallback=function A9(a){vbb(this.e.j,a)};_.setRowIndexToStyleIndex=function B9(a){dQ(this.e).U=c7(a,new p7,new J7)};_.setRowSelectedCallback=function C9(a){wbb(this.e.j,a)};_.setRows=function D9(a){dQ(this.e).V=a};_.setRowsResizedCallback=function E9(a){xbb(this.e.j,a)};_.setSelectedCellAndRange=function F9(a,b,c,d,e,f,g,h){var i;i=sfb((Rdb(cy),cy.k),$tb,'.');LZ(bQ(this.e,i).Oe().Ze(),a,b,c,d,e,f,g,h)};_.setSelectionDecreasePaintedCallback=function G9(a){ybb(this.e.j,a)};_.setSelectionIncreasePaintedCallback=function H9(a){zbb(this.e.j,a)};_.setSetCellStyleWidthRatiosCallback=function I9(a){Abb(this.e.j,a)};_.setSheetAddressChangedCallback=function J9(a){Bbb(this.e.j,a)};_.setSheetCreatedCallback=function K9(a){Cbb(this.e.j,a)};_.setSheetIndex=function L9(a){dQ(this.e).W=a};_.setSheetNames=function M9(a){dQ(this.e).X=Vkb(_6(a,new r7),gq(QB,_tb,2,0,6,1))};_.setSheetProtected=function N9(a){dQ(this.e).Y=a};_.setSheetRenamedCallback=function O9(a){Dbb(this.e.j,a)};_.setSheetSelectedCallback=function P9(a){Ebb(this.e.j,a)};_.setShiftedCellBorderStyles=function Q9(a){dQ(this.e).Z=_6(a,new r7)};_.setShowCustomEditorOnFocus=function R9(a){dQ(this.e).$=a};_.setUpdateCellCommentCallback=function S9(a){Fbb(this.e.j,a)};_.setVerticalScrollPositions=function T9(a){dQ(this.e)._=lsb(wsb(new zsb(null,new rqb(_6(a,new x7))),new t7))};_.setVerticalSplitPosition=function U9(a){dQ(this.e).ab=a};_.setVisibleCellComments=function V9(a){dQ(this.e).bb=_6(a,new r7)};_.setWidth=function W9(a){dQ(this.e).ob=a};_.setWorkbookChangeToggle=function X9(a){dQ(this.e).cb=a};_.setWorkbookProtected=function Y9(a){dQ(this.e).db=a};_.showActions=function Z9(a){var b;b=sfb((Rdb(cy),cy.k),$tb,'.');MZ(bQ(this.e,b).Oe().Ze(),_6(a,new F7(new D7)))};_.showSelectedCell=function $9(a,b,c,d,e,f,g){var h;h=sfb((Rdb(cy),cy.k),$tb,'.');NZ(bQ(this.e,h).Oe().Ze(),a,b,c,d,e,f,g)};_.updateBottomLeftCellValues=function _9(a){var b;b=sfb((Rdb(cy),cy.k),$tb,'.');OZ(bQ(this.e,b).Oe().Ze(),_6(a,new F7(new B7)))};_.updateBottomRightCellValues=function aab(a){var b;b=sfb((Rdb(cy),cy.k),$tb,'.');PZ(bQ(this.e,b).Oe().Ze(),_6(a,new F7(new B7)))};_.updateCellsAndRefreshCellStyles=function bab(){};_.updateFormulaBar=function cab(a,b,c){var d;d=sfb((Rdb(cy),cy.k),$tb,'.');QZ(bQ(this.e,d).Oe().Ze(),a,b,c)};_.updateTopLeftCellValues=function dab(a){var b;b=sfb((Rdb(cy),cy.k),$tb,'.');RZ(bQ(this.e,b).Oe().Ze(),_6(a,new F7(new B7)))};_.updateTopRightCellValues=function eab(a){var b;b=sfb((Rdb(cy),cy.k),$tb,'.');SZ(bQ(this.e,b).Oe().Ze(),_6(a,new F7(new B7)))};var LA=Xdb(Tzb,'SpreadsheetJsApi',687);GE(273,13,Wub,fab);var GA=Xdb(Tzb,'SpreadsheetJsApi/ContentWidget',273);GE(274,1,mwb,gab);_.Ld=function hab(){D_(this.a.f)};var HA=Xdb(Tzb,'SpreadsheetJsApi/lambda$0$Type',274);GE(275,1,{},iab);_.Qf=function jab(a){R7(this.a,this.b,a)};var IA=Xdb(Tzb,'SpreadsheetJsApi/lambda$1$Type',275);GE(276,1,mwb,kab);_.Ld=function lab(){oW(rQ(Q7(this.a).a).V)};var JA=Xdb(Tzb,'SpreadsheetJsApi/lambda$2$Type',276);GE(277,1,mwb,mab);_.Ld=function nab(){S7(this.a,this.b,this.c)};_.b=0;_.c=0;var KA=Xdb(Tzb,'SpreadsheetJsApi/lambda$3$Type',277);GE(197,1,{730:1,3:1},Lbb);_.rf=function Obb(a,b,c){gbb(this,a,b,c)};var OA=Xdb(Tzb,'SpreadsheetServerRpcImpl',197);GE(278,1,{},Qbb);_.Df=function Rbb(a,b){Mbb(this.a,a,b)};var MA=Xdb(Tzb,'SpreadsheetServerRpcImpl/lambda$0$Type',278);GE(279,1,{},Sbb);_.Df=function Tbb(a,b){Nbb(this.a,a,b)};var NA=Xdb(Tzb,'SpreadsheetServerRpcImpl/lambda$1$Type',279);GE(673,1,Jtb,dcb);_.ad=function ecb(){return Nj(this.b)+','+this.c+','+this.d+','+this.a+','+this.e+','+this.f+','+this.j+','+this.k+','+this.g+','+this.i};_.a=false;_.c=0;_.d=0;_.e=false;_.f=false;_.g=-1;_.i=-1;_.j=false;_.k=0;var TA=Xdb(cxb,'MouseEventDetails',673);GE(122,4,{122:1,3:1,6:1,4:1},jcb);var fcb,gcb,hcb;var SA=Ydb(cxb,'MouseEventDetails/MouseButton',122,kcb);GE(566,1,Jtb,ucb);_.a=-1;_.b=-1;_.c=-1;_.e=false;_.f=false;_.g=false;_.i=false;_.j=false;_.k=false;_.n=false;_.o=false;_.p=false;_.q=false;_.r=false;_.s=false;_.t=0;_.u=-1;_.v=-1;var UA=Xdb(cxb,'VBrowserDetails',566);var vcb;GE(174,1,{174:1,3:1},xcb);_.Zc=function ycb(a){var b;if(!Zq(a,174)){return false}b=a;if(!tdb(this.a,b.a)){return false}if(!tdb(this.b,b.b)){return false}if(!tdb(this.c,b.c)){return false}if(!tdb(this.d,b.d)){return false}return true};_._c=function zcb(){var a;a=1;a=31*a+(this.a==null?0:Dtb(this.a));a=31*a+(this.b==null?0:Dtb(this.b));a=31*a+(this.c==null?0:Dtb(this.c));a=31*a+xlb(this.d);return a};_.ad=function Acb(){return this.a+':'+this.b+'.'+this.c+'('+Xtb+')'};var VA=Xdb(hxb,'MethodInvocation',174);GE(117,4,{117:1,3:1,6:1,4:1},Fcb);var Bcb,Ccb,Dcb;var WA=Ydb(hxb,'PushMode',117,Gcb);GE(420,67,ixb);var ZA=Xdb(zyb,'AbstractSingleComponentContainerState',420);GE(112,4,{112:1,3:1,6:1,4:1},Ocb);var Kcb,Lcb,Mcb;var $A=Ydb(zyb,'ContentMode',112,Pcb);GE(77,4,{77:1,3:1,6:1,4:1},Wcb);var Qcb,Rcb,Scb,Tcb,Ucb;var aB=Ydb(zyb,'ErrorLevel',77,Xcb);GE(143,4,{143:1,3:1,6:1,4:1},_cb);var Ycb,Zcb;var cB=Ydb(uAb,'NotificationRole',143,adb);GE(233,1,Jtb,bdb);_.a=false;_.b=null;var dB=Xdb(uAb,'PageState',233);GE(99,4,{99:1,3:1,6:1,4:1},hdb);var cdb,ddb,edb,fdb;var eB=Ydb(uAb,'Transport',99,idb);GE(168,420,{67:1,75:1,168:1,3:1},jdb);_.a=false;_.b=0;_.f='This content is announced automatically and does not need to be navigated into.';_.i=-1;_.n=0;_.p=true;var nB=Xdb(uAb,'UIState',168);GE(226,1,Jtb,kdb);_.a=300;_.b=1500;_.c=5000;var gB=Xdb(uAb,'UIState/LoadingIndicatorConfigurationState',226);GE(421,1,Jtb,ldb);_.d=0;_.n=false;var hB=Xdb(uAb,'UIState/LocaleData',421);GE(230,1,Jtb,mdb);var iB=Xdb(uAb,'UIState/LocaleServiceState',230);GE(94,1,Jtb,odb,pdb);var jB=Xdb(uAb,'UIState/NotificationTypeConfiguration',94);GE(228,1,Jtb,qdb);_.a=false;_.d=null;var kB=Xdb(uAb,'UIState/PushConfigurationState',228);GE(229,1,Jtb,rdb);_.a=400;_.b=false;_.c='Server connection lost, trying to reconnect...';_.d='Server connection lost.';_.e=10000;_.f=5000;var lB=Xdb(uAb,'UIState/ReconnectDialogConfigurationState',229);GE(227,1,Jtb,sdb);_.a=300;_.b=500;_.c=750;_.d=100;_.e=evb;var mB=Xdb(uAb,'UIState/TooltipConfigurationState',227);GE(418,24,Utb,ydb);var oB=Xdb('elemental.json','JsonException',418);GE(126,1,{184:1});_.ad=function Bdb(){return this.a};var sB=Xdb(oub,'AbstractStringBuilder',126);GE(59,24,Utb,Cdb);var tB=Xdb(oub,'ArithmeticException',59);var Ndb,Odb;GE(129,83,{3:1,6:1,129:1,83:1},teb);_.je=function ueb(a){return seb(this.a,a.a)};_.Zc=function veb(a){return Zq(a,129)&&qeb(this.a,a.a)};_._c=function web(){return er(this.a)};_.ad=function yeb(){return ''+this.a};_.a=0;var AB=Xdb(oub,'Float',129);var Oeb;GE(104,83,{3:1,6:1,104:1,83:1},Qeb);_.je=function Seb(a){return Reb(this.a,a.a)};_.Zc=function Teb(a){return Zq(a,104)&&gE(a.a,this.a)};_._c=function Ueb(){return wE(this.a)};_.Yf=function Veb(){return this.a};_.ad=function Web(){return ''+xE(this.a)};_.a=0;var GB=Xdb(oub,'Long',104);var Yeb;GE(49,68,{3:1,21:1,49:1,24:1,20:1},cfb);var IB=Xdb(oub,'NumberFormatException',49);GE(74,1,{3:1,74:1},dfb);_.Zc=function efb(a){var b;if(Zq(a,74)){b=a;return this.c==b.c&&this.d==b.d&&this.a==b.a&&this.b==b.b}return false};_._c=function ffb(){return xlb(jq(eq(KB,1),Jtb,1,5,[Neb(this.c),this.a,this.d,this.b]))};_.ad=function gfb(){return this.a+'.'+this.d+'('+(this.b!=null?this.b:'Unknown Source')+(this.c>=0?':'+this.c:'')+')'};_.c=0;var MB=Xdb(oub,'StackTraceElement',74);GE(263,126,{184:1},Ffb);var NB=Xdb(oub,'StringBuffer',263);GE(30,126,{184:1},Mfb,Nfb,Ofb);var OB=Xdb(oub,'StringBuilder',30);GE(832,1,{});var Qfb;GE(36,83,{3:1,6:1,83:1,36:1},ogb,pgb,qgb,rgb);_.je=function ugb(a){return ggb(this,a)};_.Zc=function vgb(a){var b;if(this===a){return true}if(Zq(a,36)){b=a;return this.e==b.e&&ggb(this,b)==0}return false};_._c=function wgb(){var a;if(this.b!=0){return this.b}if(this.a<54){a=hE(this.f);this.b=wE(cE(a,-1));this.b=33*this.b+wE(cE(rE(a,32),-1));this.b=17*this.b+er(this.e);return this.b}this.b=17*Tgb(this.c)+er(this.e);return this.b};_.ad=function ygb(){return ngb(this)};_.a=0;_.b=0;_.d=0;_.e=0;_.f=0;var Yfb,Zfb,$fb,_fb,agb,bgb,cgb,dgb,egb;var UB=Xdb('java.math','BigDecimal',36);GE(11,83,{3:1,6:1,83:1,11:1},_gb,ahb,bhb,chb,dhb,ehb);_.je=function ghb(a){return Jgb(this,a)};_.Zc=function hhb(a){return Ogb(this,a)};_._c=function jhb(){return Tgb(this)};_.ad=function lhb(){return Ahb(this,0)};_.b=-2;_.c=0;_.d=0;_.e=0;var Cgb,Dgb,Egb,Fgb,Ggb,Hgb;var VB=Xdb('java.math','BigInteger',11);var vhb,whb;var Rhb,Shb,Thb;GE(159,697,{151:1});_.clear=function $ib(){Yib(this)};_.containsKey=function _ib(a){return Pib(this,a)};_.containsValue=function ajb(a){return Qib(a,this.b)||Qib(a,this.a)};_.$f=function bjb(){return new kjb(this)};_.get=function cjb(a){return Rib(this,a)};_.put=function djb(a,b){return Uib(this,a,b)};_.remove=function ejb(a){return Wib(this,a)};_.size=function fjb(){return Zib(this)};var ZB=Xdb(rub,'AbstractHashMap',159);GE(40,698,dub,kjb);_.clear=function ljb(){Yib(this.a)};_.contains=function mjb(a){return jjb(this,a)};_.Oe=function njb(){return new sjb(this.a)};_.remove=function ojb(a){var b;if(jjb(this,a)){b=a.gg();Wib(this.a,b);return true}return false};_.size=function pjb(){return Zib(this.a)};var YB=Xdb(rub,'AbstractHashMap/EntrySet',40);GE(41,1,{},sjb);_.Ze=function ujb(){return rjb(this)};_.Ye=function tjb(){return this.b};_.$e=function vjb(){ttb(!!this.c);bob(this.e,this);this.c.$e();this.c=null;this.b=qjb(this);cob(this.e,this)};_.b=false;var XB=Xdb(rub,'AbstractHashMap/EntrySetIterator',41);GE(709,1,eub);_.Zc=function Ckb(a){var b;if(!Zq(a,101)){return false}b=a;return Lpb(this.b.value[0],b.gg())&&Lpb(fpb(this),b.hg())};_._c=function Dkb(){return Mpb(this.b.value[0])^Mpb(fpb(this))};_.ad=function Ekb(){return this.b.value[0]+'='+fpb(this)};var iC=Xdb(rub,'AbstractMapEntry',709);GE(700,696,cub);_.addAtIndex=function Fkb(a,b){var c;c=this.ag(a);c.cg(b)};_.addAllAtIndex=function Gkb(a,b){var c,d,e,f;ptb(b);f=false;e=this.ag(a);for(d=b.Oe();d.Ye();){c=d.Ze();e.cg(c);f=true}return f};_.getAtIndex=function Hkb(b){var c;c=this.ag(b);try{return c.Ze()}catch(a){a=_D(a);if(Zq(a,66)){throw aE(new Edb("Can't get element "+b))}else throw aE(a)}};_.Oe=function Ikb(){return this.ag(0)};_.removeAtIndex=function Jkb(b){var c,d;c=this.ag(b);try{d=c.Ze();c.$e();return d}catch(a){a=_D(a);if(Zq(a,66)){throw aE(new Edb("Can't remove element "+b))}else throw aE(a)}};_.setAtIndex=function Kkb(b,c){var d,e;d=this.ag(b);try{e=d.Ze();d.fg(c);return e}catch(a){a=_D(a);if(Zq(a,66)){throw aE(new Edb("Can't set element "+b))}else throw aE(a)}};var kC=Xdb(rub,'AbstractSequentialList',700);GE(130,696,fub,Hlb);_.contains=function Ilb(a){return wjb(this,a)!=-1};_.getAtIndex=function Jlb(a){return otb(a,this.a.length),this.a[a]};_.setAtIndex=function Klb(a,b){var c;c=(otb(a,this.a.length),this.a[a]);this.a[a]=b;return c};_.size=function Llb(){return this.a.length};_.toArray=function Mlb(){return Glb(this,gq(KB,Jtb,1,this.a.length,5,1))};_.Zf=function Nlb(a){return Glb(this,a)};var oC=Xdb(rub,'Arrays/ArrayList',130);GE(163,1,{38:1},wmb);_.add=function xmb(a){throw aE(new Wfb)};_.addAll=function ymb(a){throw aE(new Wfb)};_.clear=function zmb(){throw aE(new Wfb)};_.contains=function Amb(a){return this.b.contains(a)};_.containsAll=function Bmb(a){return this.b.containsAll(a)};_.isEmpty=function Cmb(){return this.b.isEmpty()};_.Oe=function Dmb(){return new Mmb(this.b.Oe())};_.remove=function Emb(a){throw aE(new Wfb)};_.removeAll=function Fmb(a){throw aE(new Wfb)};_.retainAll=function Gmb(a){throw aE(new Wfb)};_.size=function Hmb(){return this.b.size()};_.toArray=function Imb(){return this.b.toArray()};_.Zf=function Jmb(a){return this.b.Zf(a)};_.ad=function Kmb(){return KE(this.b)};var uC=Xdb(rub,'Collections/UnmodifiableCollection',163);GE(165,1,{},Mmb);_.Ye=function Nmb(){return this.b.Ye()};_.Ze=function Omb(){return this.b.Ze()};_.$e=function Pmb(){Lmb()};var tC=Xdb(rub,'Collections/UnmodifiableCollectionIterator',165);GE(164,163,cub,Qmb);_.addAtIndex=function Rmb(a,b){throw aE(new Wfb)};_.addAllAtIndex=function Smb(a,b){throw aE(new Wfb)};_.Zc=function Tmb(a){return M(this.a,a)};_.getAtIndex=function Umb(a){return this.a.getAtIndex(a)};_._c=function Vmb(){return Q(this.a)};_.indexOf=function Wmb(a){return this.a.indexOf(a)};_.isEmpty=function Xmb(){return this.a.isEmpty()};_.lastIndexOf=function Ymb(a){return this.a.lastIndexOf(a)};_._f=function Zmb(){return new cnb(this.a.ag(0))};_.ag=function $mb(a){return new cnb(this.a.ag(a))};_.removeAtIndex=function _mb(a){throw aE(new Wfb)};_.setAtIndex=function anb(a,b){throw aE(new Wfb)};_.subList=function bnb(a,b){return new Qmb(this.a.subList(a,b))};var wC=Xdb(rub,'Collections/UnmodifiableList',164);GE(225,165,{},cnb);_.$e=function gnb(){Lmb()};_.cg=function dnb(a){throw aE(new Wfb)};_.dg=function enb(){return this.a.dg()};_.eg=function fnb(){return this.a.eg()};_.fg=function hnb(a){throw aE(new Wfb)};var vC=Xdb(rub,'Collections/UnmodifiableListIterator',225);GE(393,1,{151:1},jnb);_.getOrDefault=function qnb(a,b){var c;return c=this.c.get(a),c==null&&!this.c.containsKey(a)?b:c};_.putIfAbsent=function wnb(a,b){var c;return c=this.c.get(a),c!=null?c:inb()};_.replace=function ynb(a,b){return this.c.containsKey(a)?inb():null};_.clear=function knb(){throw aE(new Wfb)};_.containsKey=function lnb(a){return this.c.containsKey(a)};_.containsValue=function mnb(a){return this.c.containsValue(a)};_.$f=function nnb(){!this.a&&(this.a=new Gnb(this.c.$f()));return this.a};_.Zc=function onb(a){return M(this.c,a)};_.get=function pnb(a){return this.c.get(a)};_._c=function rnb(){return Q(this.c)};_.isEmpty=function snb(){return this.c.isEmpty()};_.keySet=function tnb(){!this.b&&(this.b=new Cnb(this.c.keySet()));return this.b};_.put=function unb(a,b){return inb()};_.putAll=function vnb(a){throw aE(new Wfb)};_.remove=function xnb(a){throw aE(new Wfb)};_.size=function znb(){return this.c.size()};_.ad=function Anb(){return KE(this.c)};_.values=function Bnb(){!this.d&&(this.d=new wmb(this.c.values()));return this.d};var AC=Xdb(rub,'Collections/UnmodifiableMap',393);GE(223,163,dub,Cnb);_.Zc=function Dnb(a){return M(this.b,a)};_._c=function Enb(){return Q(this.b)};var CC=Xdb(rub,'Collections/UnmodifiableSet',223);GE(394,223,dub,Gnb);_.contains=function Hnb(a){return this.b.contains(a)};_.containsAll=function Inb(a){return this.b.containsAll(a)};_.Oe=function Jnb(){var a;a=this.b.Oe();return new Mnb(a)};_.toArray=function Knb(){var a;a=this.b.toArray();Fnb(a,a.length);return a};_.Zf=function Lnb(a){var b;b=this.b.Zf(a);Fnb(b,this.b.size());return b};var zC=Xdb(rub,'Collections/UnmodifiableMap/UnmodifiableEntrySet',394);GE(396,1,{},Mnb);_.Ze=function Onb(){return new Qnb(this.a.Ze())};_.Ye=function Nnb(){return this.a.Ye()};_.$e=function Pnb(){throw aE(new Wfb)};var xC=Xdb(rub,'Collections/UnmodifiableMap/UnmodifiableEntrySet/1',396);GE(224,1,eub,Qnb);_.Zc=function Rnb(a){return this.a.Zc(a)};_.gg=function Snb(){return this.a.gg()};_.hg=function Tnb(){return this.a.hg()};_._c=function Unb(){return this.a._c()};_.ig=function Vnb(a){throw aE(new Wfb)};_.ad=function Wnb(){return KE(this.a)};var yC=Xdb(rub,'Collections/UnmodifiableMap/UnmodifiableEntrySet/UnmodifiableEntry',224);GE(395,164,{38:1,81:1,180:1},Xnb);var BC=Xdb(rub,'Collections/UnmodifiableRandomAccessList',395);var Ynb;GE(669,1,Jtb,_nb);_.Zc=function aob(a){return this===a};var DC=Xdb(rub,'Comparators/NaturalOrderComparator',669);GE(666,24,Utb,eob);var EC=Xdb(rub,'ConcurrentModificationException',666);GE(178,1,{3:1,6:1,178:1},gob);_.je=function hob(a){return Reb(hE(this.a.getTime()),hE(a.a.getTime()))};_.Zc=function iob(a){return Zq(a,178)&&gE(hE(this.a.getTime()),hE(a.a.getTime()))};_._c=function job(){var a;a=hE(this.a.getTime());return wE(yE(a,sE(a,32)))};_.ad=function lob(){return fob(this)};var FC=Xdb(rub,'Date',178);var mob,nob;GE(26,159,gub,qob,rob);var GC=Xdb(rub,'HashMap',26);GE(35,698,hub,vob,wob);_.add=function xob(a){return sob(this,a)};_.clear=function yob(){Yib(this.a)};_.contains=function zob(a){return tob(this,a)};_.isEmpty=function Aob(){return Zib(this.a)==0};_.Oe=function Bob(){var a;return a=(new bkb(this.a)).a.$f().Oe(),new hkb(a)};_.remove=function Cob(a){return uob(this,a)};_.size=function Dob(){return Zib(this.a)};var HC=Xdb(rub,'HashSet',35);GE(485,1,{},Job);_.Oe=function Kob(){return new Lob(this)};_.c=0;var JC=Xdb(rub,'InternalHashCodeMap',485);GE(232,1,{},Lob);_.Ze=function Nob(){return this.d=this.a[this.c++],this.d};_.Ye=function Mob(){var a;if(this.c<this.a.length){return true}a=this.b.next();if(!a.done){this.a=a.value[1];this.c=0;return true}return false};_.$e=function Oob(){Iob(this.e,this.d.gg());this.c!=0&&--this.c};_.c=0;_.d=null;var IC=Xdb(rub,'InternalHashCodeMap/1',232);var Rob;GE(483,1,{},_ob);_.Oe=function apb(){return new bpb(this)};_.c=0;_.d=0;var MC=Xdb(rub,'InternalStringMap',483);GE(231,1,{},bpb);_.Ze=function dpb(){return this.c=this.a,this.a=this.b.next(),new gpb(this.d,this.c,this.d.d)};_.Ye=function cpb(){return !this.a.done};_.$e=function epb(){$ob(this.d,this.c.value[0])};var KC=Xdb(rub,'InternalStringMap/1',231);GE(484,709,eub,gpb);_.gg=function hpb(){return this.b.value[0]};_.hg=function ipb(){return fpb(this)};_.ig=function jpb(a){return Zob(this.a,this.b.value[0],a)};_.c=0;var LC=Xdb(rub,'InternalStringMap/2',484);GE(270,700,{3:1,38:1,81:1},opb);_.add=function ppb(a){lpb(this,a,this.c.b,this.c);return true};_.clear=function qpb(){npb(this)};_.ag=function rpb(a){var b,c;rtb(a,this.b);if(a>=this.b>>1){c=this.c;for(b=this.b;b>a;--b){c=c.b}}else{c=this.a.a;for(b=0;b<a;++b){c=c.a}}return new tpb(this,a,c)};_.size=function spb(){return this.b};_.b=0;var PC=Xdb(rub,'LinkedList',270);GE(397,1,{},tpb);_.cg=function upb(a){lpb(this.d,a,this.b.b,this.b);++this.a;this.c=null};_.Ye=function vpb(){return this.b!=this.d.c};_.dg=function wpb(){return this.b.b!=this.d.a};_.Ze=function xpb(){ntb(this.b!=this.d.c);this.c=this.b;this.b=this.b.a;++this.a;return this.c.c};_.eg=function ypb(){ntb(this.b.b!=this.d.a);this.c=this.b=this.b.b;--this.a;return this.c.c};_.$e=function zpb(){var a;ttb(!!this.c);a=this.c.a;mpb(this.d,this.c);this.b==this.c?(this.b=a):--this.a;this.c=null};_.fg=function Apb(a){ttb(!!this.c);this.c.c=a};_.a=0;_.c=null;var NC=Xdb(rub,'LinkedList/ListIteratorImpl',397);GE(166,1,{},Bpb);var OC=Xdb(rub,'LinkedList/Node',166);GE(676,1,{});var Cpb,Dpb;var TC=Xdb(rub,'Locale',676);GE(264,676,{},Fpb);_.ad=function Gpb(){return ''};var RC=Xdb(rub,'Locale/1',264);GE(265,676,{},Hpb);_.ad=function Ipb(){return 'unknown'};var SC=Xdb(rub,'Locale/4',265);GE(187,1,{152:1},Opb);_.ng=function Ppb(a){this.a.Qf(a)};var XC=Xdb(rub,'Spliterator/OfDouble/0methodref$accept$Type',187);GE(188,1,{152:1},Qpb);_.ng=function Rpb(a){ctb(this.a,a)};var YC=Xdb(rub,'Spliterator/OfDouble/1methodref$accept$Type',188);GE(189,1,{153:1},Spb);_.og=function Tpb(a){this.a.Qf(Neb(a))};var ZC=Xdb(rub,'Spliterator/OfInt/2methodref$accept$Type',189);GE(190,1,{153:1},Upb);_.og=function Vpb(a){ctb(this.a,Neb(a))};var $C=Xdb(rub,'Spliterator/OfInt/3methodref$accept$Type',190);GE(259,154,{});_.lg=function bqb(a){Wpb(this,new Qpb(a))};_.mg=function cqb(a){return Zq(a,152)?Isb(this,a):Isb(this,new Opb(a))};var _C=Xdb(rub,'Spliterators/AbstractDoubleSpliterator',259);GE(260,154,{});_.lg=function eqb(a){Wpb(this,new Upb(a))};_.mg=function fqb(a){return Zq(a,153)?Nsb(this,a):Nsb(this,new Spb(a))};var aD=Xdb(rub,'Spliterators/AbstractIntSpliterator',260);GE(125,1,{},rqb);_.jg=function sqb(){return this.a};_.kg=function tqb(){qqb(this);return this.c};_.lg=function uqb(a){qqb(this);kpb(this.d,a)};_.mg=function vqb(a){ptb(a);qqb(this);if(plb(this.d)){a.Qf(qlb(this.d));return true}return false};_.a=0;_.c=0;var fD=Xdb(rub,'Spliterators/IteratorSpliterator',125);GE(201,1,{},xqb);_.ad=function yqb(){return !this.a?this.c:this.e.length==0?this.a.a:this.a.a+(''+this.e)};var gD=Xdb(rub,'StringJoiner',201);GE(711,1,Jtb);_.Tf=function Lqb(){return 'DUMMY'};_.Xf=function Mqb(){return -1};_.ad=function Oqb(){return this.Tf()};var Bqb,Cqb,Dqb,Eqb,Fqb,Gqb,Hqb,Iqb,Jqb;var tD=Xdb(Rvb,'Level',711);GE(518,711,Jtb,Pqb);_.Tf=function Qqb(){return 'ALL'};_.Xf=function Rqb(){return Ytb};var kD=Xdb(Rvb,'Level/LevelAll',518);GE(519,711,Jtb,Sqb);_.Tf=function Tqb(){return 'CONFIG'};_.Xf=function Uqb(){return 700};var lD=Xdb(Rvb,'Level/LevelConfig',519);GE(520,711,Jtb,Vqb);_.Tf=function Wqb(){return 'FINE'};_.Xf=function Xqb(){return 500};var mD=Xdb(Rvb,'Level/LevelFine',520);GE(521,711,Jtb,Yqb);_.Tf=function Zqb(){return 'FINER'};_.Xf=function $qb(){return 400};var nD=Xdb(Rvb,'Level/LevelFiner',521);GE(522,711,Jtb,_qb);_.Tf=function arb(){return 'FINEST'};_.Xf=function brb(){return 300};var oD=Xdb(Rvb,'Level/LevelFinest',522);GE(523,711,Jtb,crb);_.Tf=function drb(){return 'INFO'};_.Xf=function erb(){return 800};var pD=Xdb(Rvb,'Level/LevelInfo',523);GE(524,711,Jtb,frb);_.Tf=function grb(){return 'OFF'};_.Xf=function hrb(){return Otb};var qD=Xdb(Rvb,'Level/LevelOff',524);GE(525,711,Jtb,irb);_.Tf=function jrb(){return 'SEVERE'};_.Xf=function krb(){return evb};var rD=Xdb(Rvb,'Level/LevelSevere',525);GE(526,711,Jtb,lrb);_.Tf=function mrb(){return tAb};_.Xf=function nrb(){return 900};var sD=Xdb(Rvb,'Level/LevelWarning',526);GE(546,1,{},rrb);var orb;var uD=Xdb(Rvb,'LogManager',546);GE(646,1,Jtb,urb);_.b='';_.c=0;_.e=null;var vD=Xdb(Rvb,'LogRecord',646);GE(138,1,{138:1},Prb);_.e=false;var vrb=false,wrb=false,xrb=false,yrb=false,zrb=false;var wD=Xdb(Rvb,'Logger',138);GE(569,171,{},hsb);var DD=Xdb(tub,'DoubleStreamImpl',569);GE(570,1,{152:1},jsb);_.ng=function ksb(a){isb(this.a,a)};var CD=Xdb(tub,'DoubleStreamImpl/lambda$0$Type',570);GE(567,171,{},msb);var FD=Xdb(tub,'IntStreamImpl',567);GE(568,1,{153:1},osb);_.og=function psb(a){nsb(this.a,a)};var ED=Xdb(tub,'IntStreamImpl/lambda$6$Type',568);GE(536,186,{},Esb);_.mg=function Fsb(a){this.a=false;while(!this.a&&this.b.mg(new Gsb(this,a)));return this.a};_.a=false;var HD=Xdb(tub,'StreamImpl/FilterSpliterator',536);GE(541,1,{},Gsb);_.Qf=function Hsb(a){Dsb(this.a,this.b,a)};var GD=Xdb(tub,'StreamImpl/FilterSpliterator/lambda$0$Type',541);GE(535,259,{},Jsb);_.pg=function Ksb(a){return Isb(this,a)};var JD=Xdb(tub,'StreamImpl/MapToDoubleSpliterator',535);GE(540,1,{},Lsb);_.Qf=function Msb(a){this.a.ng((ptb(a),a))};var ID=Xdb(tub,'StreamImpl/MapToDoubleSpliterator/lambda$0$Type',540);GE(534,260,{},Osb);_.pg=function Psb(a){return Nsb(this,a)};var LD=Xdb(tub,'StreamImpl/MapToIntSpliterator',534);GE(539,1,{},Qsb);_.Qf=function Rsb(a){this.a.og(er((ptb(a),a)))};var KD=Xdb(tub,'StreamImpl/MapToIntSpliterator/lambda$0$Type',539);GE(718,1,{});var UD=Xdb('javaemul.internal','ConsoleLogger',718);var fr=$db('char','C');var ir=$db('int','I');var RA=Zdb(cxb,'ContextClickRpc');var gr=$db('double','D');var hr=$db('float','F');var fB=Zdb(uAb,'UIServerRpc');var _A=Zdb(zyb,'DelayedCallbackRpc');var YA=Xdb(hxb,'URLReference',null);_=JE('Vaadin.Spreadsheet.Api',T7);_=JE('Vaadin.Spreadsheet.CellData',$N);_=JE('Vaadin.Spreadsheet.OverlayInfo',WP);_.COMPONENT=UP;_.IMAGE=VP;_=JE('Vaadin.Spreadsheet.PopupButtonState',eR);_=JE('Vaadin.Spreadsheet.SpreadsheetActionDetails',pZ);_=JE('java.io.Serializable');_.$isInstance=zdb;Gdb();_=JE('java.lang.Boolean');_.$isInstance=Hdb;_=JE('java.lang.CharSequence');_.$isInstance=Mdb;_=JE('java.lang.Cloneable');_.$isInstance=keb;_=JE('java.lang.Comparable');_.$isInstance=leb;_=JE('java.lang.Double');_.$isInstance=reb;_=JE('java.lang.Number');_.$isInstance=neb;_=JE('java.lang.String');_.$isInstance=nfb;_=JE('java.lang.Throwable');_.of=Hf;var Ftb=(bg(),eg);var gwtOnLoad=gwtOnLoad=CE;AE(ME);DE('permProps',[[[FAb,GAb],[HAb,'gecko1_8']],[[FAb,GAb],[HAb,'gecko1_8']],[[FAb,GAb],[HAb,'safari']]]);if (SpreadsheetApi) SpreadsheetApi.onScriptLoad(gwtOnLoad);})();

let Spreadsheet = Vaadin.Spreadsheet.Api;

export { Spreadsheet };
