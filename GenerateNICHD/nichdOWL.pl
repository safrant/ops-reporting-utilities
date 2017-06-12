# Rob Wynne, MSC
# November 3, 2014
# Generate an NICHD OWL file 
# based off a Protege report of format:
# instance - code - NICHD_Hierarchy_Term - FULL_SYN - ALT_DEFINITION - DEFINITION - Has_NICHD_Parent

use strict;
open( my $report, $ARGV[0] ) or die "Couldn't open NICHD report file!\n";
open my $out, '>', $ARGV[1] or die "Couldn't create output file!\n";
open( my $header, "header.txt" ) or die "Unable to find header.txt.  It needs to be in this directory.\nIf you don't have it, see Rob.\n";

my @lines = <$report>;
my $i;
my %c2code = ();
my %c2label = ();
my %c2sys = ();
my %c2pts = ();
my %c2altdefs = ();
my %c2defs = ();
my %c2parents = ();

for($i=1; $i < @lines; $i++ ) {
  my $line = $lines[$i];
  chomp($line);
  my @tokens = split( /\t/, $line );

  my $class = trimstuff($tokens[0]);
  my $code = trimstuff($tokens[1]);
  my $label = trimstuff($tokens[2]);
  my @synonyms = split(/\|/, $tokens[3]);
  my @altdefs = split(/\|/, $tokens[4]);
  my @defs = split(/\|/, $tokens[5]);
  my @parents = split(/\|/, $tokens[6]);

#   print $class."\t".$code."\n";
  $c2code{$class} = $code;
  $c2label{$class} = $label;

  foreach(@synonyms) {
    my $sy = trimstuff($_);
    if ( $sy =~ /.*ncicp:term\-source>NICHD<.*/ ) {
      if( $sy =~ /.*ncicp:term\-group>SY<.*/ ) {
        push @{$c2sys{$class}}, trimcomplex($sy);
      }
      elsif( $sy =~ /.*ncicp:term\-group>PT<.*/ ) {
        push @{$c2pts{$class}}, trimcomplex($sy);
      }
    }
    elsif( $sy =~ /.*ncicp:term\-source>NCI<.*/ ) {
      if( $sy =~ /.*ncicp:term\-group>PT<.*/ ) {
        push @{$c2pts{$class}}, trimcomplex($sy);
      }
    }
  }

  foreach(@altdefs) {
    my $adef = trimstuff($_);
    if( $adef =~ /.*ncicp:def\-source>NICHD<.*/ ) {
      push @{$c2altdefs{$class}}, trimcomplex($adef);
    }
  }

  foreach(@defs) {
    my $def = trimstuff($_);
    if( $def =~ /.*ncicp:def\-source>NCI<.*/ ) {
      push @{$c2defs{$class}}, trimcomplex($def);
    }
  }

  foreach(@parents) {
    my $parent = trimstuff($_);
    push @{$c2parents{$class}}, $parent;
  }

}

while(<$header>) {
  print $out $_;
}
for my $key ( sort keys %c2code ) {
  print $out "  <owl:Class rdf:about=\"#$key\">\n";
  if( exists $c2label{$key} ) {
    print $out "    <rdfs:label>$c2label{$key}<\/rdfs:label>\n";
  }
  if( exists $c2parents{$key} ) {
    foreach(@{$c2parents{$key}}) {
      print $out "    <rdfs:subClassOf rdf:resource=\"#$_\"\/>\n";
    }
  }
  print $out "    <code rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\">$c2code{$key}<\/code>\n";
  if( exists $c2pts{$key} ) {
    foreach(@{$c2pts{$key}}) {
      print $out "    <FULL_SYN rdf:parseType=\"Literal\">$_<\/FULL_SYN>\n";
    }
  }
  if( exists $c2sys{$key} ) {
    foreach(@{$c2sys{$key}}) {
      print $out "    <FULL_SYN rdf:parseType=\"Literal\">$_<\/FULL_SYN>\n";
    }
  }
  if( exists $c2defs{$key} ) {
    foreach(@{$c2defs{$key}}) {
      print $out "    <DEFINITION rdf:parseType=\"Literal\">$_<\/DEFINITION>\n";
    }
  }
  if( exists $c2altdefs{$key} ) {
    foreach(@{$c2altdefs{$key}}) {
      print $out "    <ALT_DEFINITION rdf:parseType=\"Literal\">$_<\/ALT_DEFINITION>\n";
    }
  }
  print $out "  <\/owl:Class>\n";
}
print $out "<\/rdf:RDF>\n";


sub  trimstuff { my $s = shift; $s =~ s/^"+|"+$|http:\/\/ncicb\.nci\.nih\.gov\/xml\/owl\/EVS\/Thesaurus\.owl#//g; return $s };
#sub  trimcomplex { my $s = shift; $s =~ s/(.*)ncicp:(ComplexTerm|ComplexDefinition) xmlns:ncicp=""http:\/\/ncicb\.nci\.nih\.gov\/xml\/owl\/EVS\/ComplexProperties.xsd#""(.*)/$1ncicp:$2 xmlns:ncicp="http:\/\/ncicb\.nci\.nih\.gov\/xml\/owl\/EVS\/ComplexProperties.xsd#"$3/g; return $s };
sub  trimcomplex {
  my $s = shift;
  $s =~ s/\sxmlns:ncicp=("+)http:\/\/ncicb\.nci\.nih\.gov\/xml\/owl\/EVS\/ComplexProperties.xsd(#?)("+)//g;
  $s =~ s/<ncicp:Definition_Reviewer_Name>.*<\/ncicp:Definition_Reviewer_Name>//g;
  $s =~ s/<ncicp:Definition_Review_Date>.*<\/ncicp:Definition_Review_Date>//g;
  return $s };