#!/bin/bash

sed \
	-e '/.acc.org/ i\
American College of Cardiology  \\' \
	-e '/.aemf.org/ i\
Alfred Mann Foundation  \\' \
 	-e '/.c-path.org/ i\
Critical Path Institute  \\' \
	-e '/.cancerresearchuk.org/ i\
Cancer Research UK  \\' \
	-e '/.chomp.org/ i\
Community Hospital of the Monterey Peninsula  \\' \
	-e '/.coh.org/ i\
City of Hope  \\' \
	-e '/.fhcrc.org/ i\
Fred Hutchinson Cancer Research Center  \\' \
	-e '/.ghc.org/ i\
Group Health Cooperative  \\' \
	-e '/.mskcc.org/ i\
Sloan-Kettering  \\' \
	-e '/.nmdp.org/ i\
The National Marrow Donor Program  \\' \
	-e '/.partners.org/ i\
Partners HealthCare  \\' \
	-e '/.regenstrief.org/ i\
Regenstrief Institute  \\' \
	-e '/.rhsc.org/ i\
Rochester Hearing & Speech Center  \\' \
	-e '/.roswellpark.org/ i\
Roswell Park Cancer Institute  \\' \
	-e '/.stjude.org/ i\
St. Jude Childrens Research Hospital  \\' \
	-e '/.w3.org/ i\
World Wide Web Consortium  \\' \
	-e '/.curesearch.org/ i\
CureSearch for Childrens Cancer  \\' \
	-e '/.mitre.org/ i\
The MITRE Corporation  \\' \
	-e '/.ccf.org/ i\
Cleveland Clinic  \\' \
	-e '/.d64.org/ i\
Park Ridge-Niles School District  \\' \
	-e '/.mercydesmoines.org/ i\
Mercy Medical Center, Des Moines  \\' \
	-e '/.rcctvm.org/ i\
Regional Cancer Centre Thiruvananthapuram  \\' \
	-e '/.cardiosource.org/ i\
American College of Cardiology  \\' \
	-e '/.promedica.org/ i\
ProMedica  \\' \
	-e '/.bchosp.org/ i\
BC Childrens Hospital  \\' \
	-e '/.christianacare.org/ i\
Chistiana Care Health System  \\' \
	-e '/.bergonie.org/ i\
Institut Bergonie  \\' \
	-e '/.hitchcock.org/ i\
Dartmouth-Hitchcock  \\' \
	-e '/.isupark.org/ i\
Iowa State University Research Park  \\' \
	-e '/.sjmc.org/ i\
St. John Medical Center  \\' \
	-e '/.informatics.jax.org/ i\
Mouse Genome Informatics  \\' \
	-e '/.darienlibrary.org/ i\
Darien Public Library \\' \
	-e '/.westsuburbanymca.org/ i\
West Suburban YMCA \\' \
	-e '/.sgcmh.org/ i\
Ste. Genevieve County Memorial Hospital  \\' \
	-e '/.gbmc.org/ i\
Greater Baltimore Medical Center  \\' \
	-e '/.slrmc.org/ i\
St Lukes Regional Medical Center  \\' \
	-e '/.hcawv.org/ i\
West Virginia Health Care Authority  \\' \
	-e '/.bcrc.org/ i\
Breast Cancer Resource Center  \\' \
	-e '/.lifepath-hospice.org/ i\
Lifepath Hospice  \\' \
	-e '/.gogstats.org/ i\
Gynecologic Oncology Group  \\' \
	-e '/.aurorahealthcare.org/ i\
Aurora Health Care \\' \
	-e '/.ahsys.org/ i\
Atlantic Health System \\' \
	-e '/.dogstrust.org/ i\
Dogs Trust \\' \
	-e '/.paloaltojcc.org/ i\
Oshman Family Jewish Community Center \\' \
	-e '/.usd447.org/ i\
Lincoln Center Elementary School - Cherryvale USD 447 \\' \
	-e '/.kmcnetwork.org/ i\
Kettering Health Network \\' \
	-e '/.ghs.org/ i\
Greenville Hospital System \\' \
	-e '/.trinity-health.org/ i\
Trinity Health - Catholic Health Care System \\' \
	-e '/.cchmc.org/ i\
Cincinnati Childrens Hospital Medical Center \\' \
	-e '/.gundluth.org/ i\
Gundersen Lutheran Health System \\' \
	-e '/.rti.org/ i\
RTI International \\' \
	-e '/.nch.org/ i\
Northwest Community Hospital \\' \
	-e '/.caphealth.org/ i\
caphealth.org \\' \
	-e '/.cancercare.org/ i\
Cancer Care, Inc. \\' \
	-e '/.centura.org/ i\
Colorado Hospitals & Health Care System \\' \
	-e '/.eushc.org/ i\
Emory University Healthcare \\' \
	-e '/.regionalmentalhealth.org/ i\
Regional Mental Health Center \\' \
	-e '/.sivit.org/ i\
ISP \\' \
	-e '/.montefiore.org/ i\
Montefiore Medical Center \\' \
	-e '/.lacusc.org/ i\
Los Angeles County - University of Southern California \\' \
	-e '/.shermanhospital.org/ i\
Sherman Hospital \\' \
	-e '/\.uhs.org/ i\
UHS \\' \
	-e '/.petermac.org/ i\
Peter MacCullum Cancer Centre \\' \
	-e '/.ssg.org.au/ i\
Melbourne Health Shared Services \\' \
	-e '/.nmh.org/ i\
Northwestern Memorial Hospital \\' \
	-e '/.ibcsg.org/ i\
International Breast Cancer Study Group \\' \
	-e '/.cancer.org/ i\
American Cancer Society \\' \
	-e '/.texaschildrenshospital.org/ i\
Texas Childrens Hospital \\' \
	-e '/.jax.org/ i\
The Jackson Laboratory \\' \
	-e '/.childrenscolorado.org/ i\
Childrens Hospital Colorado \\' \
	-e '/.luhs.org/ i\
Loyola University Health System \\' \
	-e '/.providence.org/ i\
Providence Health & Services \\' \
	-e '/.hendrickhealth.org/ i\
Hendrick Medical Center \\' \
	-e '/.atame.org/ i\
Red de Entretenimiento en Espanol SA \\' \
	-e '/.neuinfo.org/ i\
Neuroscience Information Framework \\' \
	-e '/.dchs.org/ i\
Dickenson County Healthcare System \\' \
	-e '/.dhha.org/ i\
Denver Health and Hospital Authority \\' \
	-e '/.carolinashealthcare.org/ i\
Carolinas HealthCare System \\' \
	-e '/.tmcmed.org/ i\
Truman Medical Centers, Kansas City \\' \
	< $1 > res-$1
sed '/\\$/N; s/\\\n//; ' res-$1 >alter-$1



