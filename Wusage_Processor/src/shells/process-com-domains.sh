#!/bin/bash

sed \
	-e '/.wowrack.com/ i\
ISP  \\' \
	-e '/.ukrtelecom.ua/ i\
ISP  \\' \
	-e '/.amgen.com/ i\
Amgen  \\' \
	-e '/.teksavvy.com/ i\
ISP  \\' \
	-e '/.allina.com/ i\
Allina Hospitals and Clinics  \\' \
	-e '/.grunenthal.com/ i\
Grunenthal Group  \\' \
	-e '/.highmark.com/ i\
Highmark  \\' \
	-e '/.holzerclinic.com/ i\
Holzer Clinic  \\' \
	-e '/.hallrender.com/ i\
Hall, Render, Killian, Heath & Lyman P.C.  \\' \
	-e '/.afga.com/ i\
AFGA Healthcare  \\' \
	-e '/.parexel.com/ i\
PAREXEL International  \\' \
	-e '/.lmco.com/ i\
Lockheed Martin  \\' \
	-e '/.bah.com/ i\
Booz Allen Hamilton  \\' \
	-e '/.ingenuity.com/ i\
Ingenuity Systems  \\' \
	-e '/.emmes.com/ i\
The Emmes Corporation  \\' \
	-e '/.roche.com/ i\
Roche  \\' \
	-e '/.indegene.com/ i\
Indegene Lifesystems  \\' \
	-e '/.ipsen.com/ i\
Ipsen Group  \\' \
	-e '/.cvty.com/ i\
Coventry Health Care  \\' \
	-e '/.insightbb.com/ i\
ISP  \\' \
	-e '/.compute-1.amazonaws.com/ i\
BOT  \\' \
	-e '/.compute.amazonaws.com/ i\
BOT  \\' \
	-e '/.btcentralplus.com/ i\
ISP  \\' \
	-e '/.btopenworld.com/ i\
ISP  \\' \
	-e '/.mycomspan.com/ i\
ISP  \\' \
	-e '/.neoviatelecom.com/ i\
ISP  \\' \
	-e '/.bb.sky.com/ i\
BOT  \\' \
	-e '/.virginmedia.com/ i\
ISP  \\' \
	-e '/.charter.com/ i\
ISP  \\' \
	-e '/.peakpeak.com/ i\
ISP  \\' \
	-e '/.purewire.com/ i\
BOT  \\' \
	-e '/.brightview.com/ i\
ISP  \\' \
	-e '/.express-scripts.com/ i\
Express Scripts  \\' \
	-e '/.belgacom.be/ i\
ISP  \\' \
	-e '/.tpgi.com.au/ i\
ISP  \\' \
	-e '/.firstcomm.com/ i\
ISP  \\' \
	-e '/.megacable.com.ar/ i\
ISP  \\' \
	-e '/.sagamino.jp.ibm.com/ i\
BOT  \\' \
	-e '/.myvzw.com/ i\
ISP  \\' \
	-e '/.westgroup.com/ i\
Thomson Reuters Westlaw  \\' \
	-e '/.xmission.com/ i\
ISP  \\' \
	-e '/.telia.com/ i\
ISP  \\' \
	-e '/.carefusion.com/ i\
CareFusion  \\' \
	-e '/\.gene.com/ i\
Genentech  \\' \
	-e '/.medtronic.com/ i\
Medtronic  \\' \
	-e '/.kimsufi.com/ i\
ISP  \\' \
	-e '/.jnj.com/ i\
Johnson and Johnson  \\' \
	-e '/.saic.com/ i\
SAIC  \\' \
	-e '/.trustmarklife.com/ i\
Trustmark Companies  \\' \
	-e '/.pfizer.com/ i\
Pfizer  \\' \
	-e '/.lilly.com/ i\
Eli Lilly and Company  \\' \
	-e '/.cortland.com/ i\
ISP  \\' \
	-e '/.mchsi.com/ i\
ISP  \\' \
	-e '/.basf-it-services.com/ i\
BASF Information Services  \\' \
	-e '/.mmsholdings.com/ i\
MMS Holdings  \\' \
	-e '/.ihc.com/ i\
Intermountain Healthcare  \\' \
	-e '/.cablenet.com/ i\
ISP  \\' \
	-e '/.qualitestrx.com/ i\
Qualitest Pharmaceuticals  \\' \
	-e '/.kai-research.com/ i\
Kai Reseach  \\' \
	-e '/.lvh.com/ i\
Lehigh Valley Health Network  \\' \
	-e '/.prod-infinitum.com/ i\
BOT  \\' \
	-e '/.practicepartner.com/ i\
McKesson Provider Technologies  \\' \
	-e '/.virtua.com/ i\
Virtua  \\' \
	-e '/.verizon.com/ i\
ISP  \\' \
	-e '/.optusnet.com/ i\
ISP  \\' \
	-e '/.maxonline.com/ i\
BOT  \\' \
	-e '/.twtelecom.net/ i\
ISP  \\' \
	-e '/.bms.com/ i\
Bristol-Myers Squibb  \\' \
	-e '/.nwinternet.com/ i\
ISP  \\' \
	-e '/.rr.com/ i\
ISP  \\' \
	-e '/.sial.com/ i\
Sigma-Aldrich  \\' \
	-e '/.propel.com/ i\
ISP  \\' \
	-e '/.netpci.com/ i\
ISP  \\' \
	-e '/.mpi.com/ i\
BOT  \\' \
	-e '/.veracitycom.net/ i\
ISP  \\' \
	-e '/.ltdomains.com/ i\
BOT  \\' \
	-e '/.linguamatics.com/ i\
Linguamatics  \\' \
	-e '/.corefocus.com/ i\
CoreFocus  \\' \
	-e '/.buckeyecom.net/ i\
ISP  \\' \
	-e '/www-900.ibm.com/ i\
IBM China Research Laboratory  \\' \
	-e '/.chinaunicom.com/ i\
ISP  \\' \
	-e '/.imagesolutions.com/ i\
Image Solutions - CSC \\' \
	-e '/\.telecom.net/ i\
ISP  \\' \
	-e '/.dwsmdplc.com/ i\
BOT  \\' \
	-e '/.dynamic.dsl/ i\
ISP  \\' \
	-e '/.mindspring.com/ i\
ISP  \\' \
	-e '/.softlayer.com/ i\
ISP  \\' \
	-e '/.westchestergov.com/ i\
Westchester County, NY  \\' \
	-e '/.mundo-r.com/ i\
ISP  \\' \
	-e '/.starcomms.net/ i\
ISP  \\' \
	-e '/.cardinal-ip.com/ i\
Cardinal Intellectual Property  \\' \
	-e '/.lexis-nexis.com/ i\
LexisNexis  \\' \
	-e '/.cohnwolfe.com/ i\
Cohn & Wolfe  \\' \
	-e '/.bigcommunications.com/ i\
BIG Healthcare Relationship Agency  \\' \
	-e '/.walgreens.com/ i\
Walgreens  \\' \
	-e '/.kcom.com/ i\
ISP  \\' \
	-e '/.telecomitalia.it/ i\
ISP  \\' \
	-e '/.immatics.com/ i\
immatics biotechnologies  \\' \
	-e '/.avintec.com/ i\
ISP  \\' \
	-e '/.dodo.com/ i\
ISP  \\' \
	-e '/.banglalionwimax.com/ i\
ISP  \\' \
	-e '/.icsincorporated.com/ i\
ISP  \\' \
	-e '/.renji.com/ i\
Renji Hospital - Shanghai \\' \
	-e '/.nexicom.net/ i\
ISP  \\' \
	-e '/.northshorelij.com/ i\
North Shore LIJ Health System  \\' \
	-e '/.cloud-ips.com/ i\
ISP  \\' \
	-e '/.seasidehighspeed.com/ i\
ISP  \\' \
	-e '/.cvs.com/ i\
CVS  \\' \
	-e '/.ztomy.com/ i\
BOT  \\' \
	-e '/.connectregus.com/ i\
ISP  \\' \
	-e '/.verizon.net/ i\
ISP  \\' \
	-e '/.mpeainet.com/ i\
ISP  \\' \
	-e '/.01.com/ i\
ISP  \\' \
	-e '/.gbbn.com/ i\
GBBN Architects  \\' \
	-e '/.watson.ibm.com/ i\
IBM Watson Research Center  \\' \
	-e '/.healingheartsolutions.com/ i\
Healing Heart Solutions  \\' \
	-e '/.aol.com/ i\
ISP  \\' \
	-e '/.cardinal.com/ i\
Cardinal Health  \\' \
	-e '/.harsco.com/ i\
Harsco Corporation  \\' \
	-e '/.pcesystems.com/ i\
BOT  \\' \
	-e '/.tulipconnect.com/ i\
ISP  \\' \
	-e '/.ucom.ne.jp/ i\
ISP  \\' \
	-e '/.netvigator.com/ i\
ISP  \\' \
	-e '/.skgf.com/ i\
Sterne, Kessler, Goldstein & Fox  \\' \
	-e '/.uhc.com/ i\
United HealthCare  \\' \
	-e '/.rogers.com/ i\
ISP  \\' \
	-e '/.wavecable.com/ i\
ISP  \\' \
	-e '/.ono.com/ i\
ISP  \\' \
	-e '/.hkcable.com.hk/ i\
ISP  \\' \
	-e '/.httnet.com.tr/ i\
ISP  \\' \
	-e '/.ppdi.com/ i\
Pharmaceutical Product Development, Inc.  \\' \
	-e '/.webtropia.com/ i\
ISP  \\' \
	-e '/.targetdevelopmentdirect.com/ i\
BOT  \\' \
	-e '/.targetdevelopmentonline.com/ i\
BOT  \\' \
	-e '/.autolivasp.com/ i\
Autoliv ASP  \\' \
	-e '/.bigpipeinc.com/ i\
BOT  \\' \
	-e '/.idispharma.com/ i\
Idis  \\' \
	-e '/.novartis.com/ i\
Novartis  \\' \
	-e '/.telusmobility.com/ i\
ISP  \\' \
	-e '/.safebridge.com/ i\
SafeBridge Consultants  \\' \
	-e '/.sanbrunocable.com/ i\
ISP  \\' \
	-e '/.eircom.net/ i\
ISP  \\' \
	-e '/.as13448.com/ i\
BOT  \\' \
	-e '/.marriott.com/ i\
ISP  \\' \
	-e '/.startdedicated.com/ i\
BOT  \\' \
	-e '/.springshare.com/ i\
Springshare, LLC.  \\' \
	-e '/.intelerad.com/ i\
Intelrad  \\' \
	-e '/.mrse.com.ar/ i\
BOT  \\' \
	-e '/.imrecoverysolutions.com/ i\
BOT  \\' \
	-e '/.ttnet.com.tr/ i\
ISP  \\' \
	-e '/.durangocancercenter.com/ i\
Durango Cancer Center  \\' \
	-e '/wonten.com.tw/ i\
ISP  \\' \
	-e '/vnpt-hanoi.com.vn/ i\
ISP  \\' \
	-e '/.comunitel.net/ i\
ISP  \\' \
	-e '/.rochester.ibm.com/ i\
IBM Rochester  \\' \
	-e '/.siemens.com/ i\
Siemens  \\' \
	-e '/.ogilvypr.com/ i\
Ogilvy Public Relations  \\' \
	-e '/.communitynet.ca/ i\
ISP  \\' \
	-e '/.northropgrumman.com/ i\
Northrup Grumman Corporation  \\' \
	-e '/.apol.com.tw/ i\
ISP  \\' \
	-e '/.smartservercontrol.com/ i\
BOT  \\' \
	-e '/.sharp.com/ i\
SHARP San Diego Health Care  \\' \
	-e '/.telecentro-reversos.com.ar/ i\
ISP  \\' \
	-e '/.virtualrad.com/ i\
Virtual Radiologic  \\' \
	-e '/.telnetww.com/ i\
ISP  \\' \
	-e '/.syntegra.com/ i\
Syntegra  \\' \
	-e '/.ctinets.com/ i\
ISP  \\' \
	-e '/.wkglobal.com/ i\
ISP  \\' \
	-e '/.adirads.com/ i\
Advanced Diagnostic Imaging  \\' \
	-e '/.prod-empresarial.com.mx/ i\
BOT  \\' \
	-e '/.veloxzone.com.br/ i\
ISP  \\' \
	-e '/.drfirst.com/ i\
DrFirst  \\' \
	-e '/.rhoworld.com/ i\
Rho  \\' \
	-e '/.cpqd.com.br/ i\
ISP  \\' \
	-e '/.francetelecom.com/ i\
ISP  \\' \
	-e '/.maclaren.com/ i\
MacLaren McCann Toronto  \\' \
	-e '/.usscript.com/ i\
US Script  \\' \
	-e '/.kbtelecom.net/ i\
ISP  \\' \
	-e '/.celleration.com/ i\
ISP  \\' \
	-e '/.d9hosting.com/ i\
ISP  \\' \
	-e '/.t-com.sk/ i\
ISP  \\' \
	-e '/.panasonic.com/ i\
Panasonic  \\' \
	-e '/.dreamhost.com/ i\
ISP  \\' \
	-e '/.vodafone.com.tr/ i\
ISP  \\' \
	-e '/.asianetcom.net/ i\
ISP  \\' \
	-e '/.mdkinc.com/ i\
MDK, Inc.  \\' \
	-e '/.akamaitechnologies.com/ i\
BOT  \\' \
	-e '/.ontelecoms.gr/ i\
ISP  \\' \
	-e '/.gtm.com.tw/ i\
GTM  \\' \
	-e '/.ge.com/ i\
GE  \\' \
	-e '/.chcbc.com/ i\
Community Health Center of Branch County  \\' \
	-e '/.shindengen.com/ i\
Shindengen  \\' \
	-e '/.catalent.com/ i\
Catalent  \\' \
	-e '/.lhpipa.com/ i\
LHP Hospital Group  \\' \
	-e '/.surveystars.com/ i\
surveystars  \\' \
	-e '/.mckesson.com/ i\
McKesson  \\' \
	-e '/.pyxis.com/ i\
CareFusion  \\' \
	-e '/.scilproteins.com/ i\
Scil Proteins  \\' \
	-e '/.speedy.com.ar/ i\
ISP  \\' \
	-e '/.ahhinc.com/ i\
American Health Holding, Inc.  \\' \
	-e '/.data.com.cn/ i\
ISP  \\' \
	-e '/.mailingeficaz.com/ i\
BOT  \\' \
	-e '/.kereda.com/ i\
Provision Data System  \\' \
	-e '/.evanhospital.com/ i\
Evangelical Community Hospital  \\' \
	-e '/.responsebio.com/ i\
Response Biomedical  \\' \
	-e '/.mfpstorrs.com/ i\
Mansfield Family Practice  \\' \
	-e '/.edirectlink.com/ i\
ISP  \\' \
	-e '/.vico-lab.com/ i\
Vico Software  \\' \
	-e '/.prevagen.com/ i\
Prevagen  \\' \
	-e '/.medicalcity.com.ph/ i\
Medical City - Phillippines  \\' \
	-e '/.frx.com/ i\
Forest Laboratories  \\' \
	-e '/.bayer.com/ i\
Bayer  \\' \
	-e '/.intuit.com/ i\
Intuit  \\' \
	-e '/.fibertel.com.ar/ i\
ISP  \\' \
	-e '/.epocrates.com/ i\
Epocrates  \\' \
	-e '/.t6b.com/ i\
ISP  \\' \
	-e '/.bektel.com/ i\
ISP  \\' \
	-e '/.telecom.ece.ntua.gr/ i\
ISP  \\' \
	-e '/.outcome.com/ i\
Outcome  \\' \
	-e '/.cornerstone-research.com/ i\
Cornerstone Research Group  \\' \
	-e '/.us.ibm.com/ i\
IBM  \\' \
	-e '/.cisco.com/ i\
Cisco Systems  \\' \
	-e '/.impaqint.com/ i\
Impaq International  \\' \
	-e '/.wideopenwest.com/ i\
ISP  \\' \
	-e '/.owfg.com/ i\
Overwaitea Food Group  \\' \
	-e '/.scorpiondesign.com/ i\
Scorpion Design  \\' \
	-e '/.jscomm.net/ i\
ISP  \\' \
	-e '/.saiadvantium.com/ i\
SAI Advantium  \\' \
	-e '/.cerner.com/ i\
Cerner  \\' \
	-e '/.sxc.com/ i\
SXC Health Solutions  \\' \
	-e '/.bcbsnc.com/ i\
Blue Cross and Blue Shield of North Carolina  \\' \
	-e '/.bankofamerica.com/ i\
Bank of America  \\' \
	-e '/.bendbroadband.com/ i\
ISP  \\' \
	-e '/.westat.com/ i\
ISP  \\' \
	-e '/.kbronet.com.tw/ i\
ISP  \\' \
	-e '/.betco.com/ i\
Betco  \\' \
	-e '/.m5media1.com/ i\
BOT  \\' \
	-e '/.emea.ibm.com/ i\
IBM  \\' \
	-e '/.bulsat.com/ i\
ISP  \\' \
	-e '/.ctstelecom.net/ i\
ISP  \\' \
	-e '/.parknicollet.com/ i\
Park Nicollet Health Services  \\' \
	-e '/.adam.com.au/ i\
ISP  \\' \
	-e '/.scenpro.com/ i\
ScenPro  \\' \
	-e '/.humed.com/ i\
Hackensack University Medical Center  \\' \
	-e '/.pavlovmedia.com/ i\
ISP  \\' \
	-e '/.gsk.com/ i\
GlaxoSmithKline  \\' \
	-e '/.epic.com/ i\
Epic Systems Corporation  \\' \
	-e '/.carecorenational.com/ i\
CareCore National  \\' \
	-e '/.ariad.com/ i\
ARIAD Pharmaceuticals  \\' \
	-e '/.albacom.net/ i\
ISP  \\' \
	-e '/.connectel.com.pk/ i\
ISP  \\' \
	-e '/.vodacom.co.za/ i\
ISP  \\' \
	-e '/.speedyterra.com.br/ i\
ISP  \\' \
	-e '/.baydenet.com.br/ i\
ISP  \\' \
	-e '/.chronimed.com/ i\
Bioscrip  \\' \
	-e '/.unitedbiosource.com/ i\
United BioSource  \\' \
	-e '/.ghsinc.com/ i\
Goold Health Systems  \\' \
	-e '/flcancer.com/ i\
Florida Cancer Specialists and Research Institute  \\' \
	-e '/.valleyhealthlink.com/ i\
Valley Health Home  \\' \
	-e '/.futis.com.tw/ i\
Futis Eyewear  \\' \
	-e '/.pathcom.com/ i\
ISP  \\' \
	-e '/.uninet-ide.com.mx/ i\
ISP  \\' \
	-e '/.siteimprove.com/ i\
BOT  \\' \
	-e '/.ipvnow.com/ i\
ISP  \\' \
	-e '/.cable.rcn.com/ i\
ISP  \\' \
	-e '/.research.philips.com/ i\
Philips Reseach  \\' \
	-e '/.ibys.com.br/ i\
ISP  \\' \
	-e '/.accenture.com/ i\
Accenture  \\' \
	-e '/.emulex.com/ i\
Emulex  \\' \
	-e '/.vodacom.co.za/ i\
ISP  \\' \
	-e '/.scansafe.net/ i\
BOT  \\' \
	-e '/.google.com/ i\
BOT  \\' \
	-e '/.questdiagnostics.com/ i\
Quest Diagnostics  \\' \
	-e '/.nauticom.net/ i\
ISP  \\' \
	-e '/.admere.com/ i\
Advanced Medical Reviews  \\' \
	-e '/.innovamd.com/ i\
InnovaMD  \\' \
	-e '/.memorialhealth.com/ i\
Memorial Health Medical Center  \\' \
	-e '/.terpsys.com/ i\
TerpSys  \\' \
	-e '/.covance.com/ i\
Covance Inc.  \\' \
	-e '/.neabaptistclinic.com/ i\
NEA Baptist Clinic  \\' \
	-e '/.ezecom.com.kh/ i\
ISP  \\' \
	-e '/.topotarget.com/ i\
Topotarget  \\' \
	-e '/.ebonenet.com/ i\
ISP  \\' \
	-e '/.museumofsex.com/ i\
Museum Of Sex  \\' \
	-e '/.zelcom.ru/ i\
ISP  \\' \
	-e '/.dnsdigger.com/ i\
BOT  \\' \
	-e '/.rainbowbroadband.com/ i\
ISP  \\' \
	-e '/.varde.com/ i\
Varde Partners  \\' \
	-e '/.bcaims.com/ i\
Associated Internal Medicine Specialists  \\' \
	-e '/.unx.sas.com/ i\
SAS  \\' \
	-e '/.nor-consult.com/ i\
Nor Consult, LLC  \\' \
	-e '/morphotek.com/ i\
Morphotek  \\' \
	-e '/.m1.com.sg/ i\
ISP  \\' \
	-e '/.waynecounty.com/ i\
Wayne County, Michigan  \\' \
	-e '/.eclipsys.com/ i\
Eclipsys  \\' \
	-e '/.heritage-info.com/ i\
Xerox  \\' \
	-e '/.microsoft.com/ i\
Microsoft  \\' \
	-e '/.nervianoms.com/ i\
Nerviano Medical Services  \\' \
	-e '/.biowisdom.com/ i\
BioWisdom Ltd  \\' \
	-e '/.ebscohost.com/ i\
EBSCO Publishing  \\' \
	-e '/calumetspecialty.com/ i\
Calumet Specialty Products Partners  \\' \
	-e '/.bmrn.com/ i\
Biomarin  \\' \
	-e '/.nextlevelinternet.com/ i\
ISP  \\' \
	-e '/.beyondbb.com/ i\
ISP  \\' \
	-e '/.singnet.com.sg/ i\
ISP  \\' \
	-e '/.psychogenics.com/ i\
PsychoGenics  \\' \
	-e '/.pharmtrace.com/ i\
pharmtrace  \\' \
	-e '/.bjtelecom.net/ i\
ISP  \\' \
	-e '/.hp.com/ i\
HP  \\' \
	-e '/.csc.com/ i\
CSC  \\' \
	-e '/.nghs.com/ i\
Northeast Georgia Health System  \\' \
	-e '/.innovamd.com/ i\
InnovaMD  \\' \
	-e '/.carle.com/ i\
Carle Foundation Hospital  \\' \
	-e '/.wppp.com/ i\
Western Pacific Pulp and Paper  \\' \
	-e '/.winicker-norimed.com/ i\
Winicker-Norimed GmbH  \\' \
	-e '/.medicalsleep.com/ i\
Medical Sleep Solutions  \\' \
	-e '/.iowatelecom.net/ i\
ISP  \\' \
	-e '/.diversified.com/ i\
Diversified  \\' \
	-e '/.oracle.com/ i\
Oracle  \\' \
	-e '/.meditech.com/ i\
MEDITECH  \\' \
	-e '/.onecommunications.net/ i\
ISP  \\' \
	-e '/.fidelity.com/ i\
Fidelity  \\' \
	-e '/.argushealth.com/ i\
Argus Health Systems, Inc.  \\' \
	-e '/.c-tasc.com/ i\
C-TASC  \\' \
	-e '/.ornis.com/ i\
Risc Group  \\' \
	-e '/.panix.com/ i\
ISP  \\' \
	-e '/.go.com.jo/ i\
ISP  \\' \
	-e '/.tw1.com/ i\
ISP  \\' \
	-e '/.landmarkhospitals.com/ i\
Landmark Hospitals  \\' \
	-e '/.webazilla.com/ i\
ISP  \\' \
	-e '/.emergingmed.com/ i\
EmergingMed  \\' \
	-e '/.mpsrx.com/ i\
Millenium Pharmacy Systems  \\' \
	-e '/.opus-ism.com/ i\
OPUS-ISM  \\' \
	-e '/.blhtech.com/ i\
BLH Technologies  \\' \
	-e '/.technatomy.com/ i\
Technatomy Corporation  \\' \
	-e '/.hostgator.com/ i\
ISP  \\' \
	-e '/.chemaxon.com/ i\
ChemAxon  \\' \
	-e '/.hostmdm.com/ i\
ISP  \\' \
	-e '/.verathon.com/ i\
Verathon International  \\' \
	-e '/.eli.net/ i\
ISP  \\' \
	-e '/.coffeemakingking.com/ i\
BOT  \\' \
	-e '/.flexiblestent.com/ i\
Flexible Stent  \\' \
	-e '/.evergreenrecycling.com/ i\
Evergreen Recycling  \\' \
	-e '/.sentrx.com/ i\
Sentrx  \\' \
	-e '/.onlinedailyupdates.com/ i\
BOT  \\' \
	-e '/.cenorin.com/ i\
Cenorin  \\' \
	-e '/.cableplus.com/ i\
ISP  \\' \
	-e '/.continuumdatacenters.com/ i\
ISP  \\' \
	-e '/.coolhandle.com/ i\
ISP  \\' \
	-e '/.ncenetworks.com/ i\
ISP  \\' \
	-e '/.servinio.com/ i\
ISP  \\' \
	-e '/.relakks.com/ i\
ISP  \\' \
	-e '/.valencehealth.com/ i\
Valence Health  \\' \
	-e '/.aetna.com/ i\
Aetna  \\' \
	-e '/.draegermed.com/ i\
Draeger  \\' \
	-e '/.hamiltoncom.net/ i\
ISP  \\' \
	-e '/.sunovion.com/ i\
Sunovion  \\' \
	-e '/.stingcomm.net/ i\
ISP  \\' \
	-e '/.accesscomm.ca/ i\
ISP  \\' \
	-e '/.comporium.net/ i\
BOT  \\' \
	-e '/.v2solutions.com/ i\
V2 Solutions  \\' \
	-e '/.movilnet.com.ve/ i\
ISP  \\' \
	<$1 > res-$1
sed '/\\$/N; s/\\\n//; ' res-$1 >alter-$1




