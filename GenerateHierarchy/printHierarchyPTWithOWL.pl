# Rob Wynne, LMCO
#
# September 16, 2010
# Generate source hierarchy using input map file of the form:
# concept identifier <tab> preferred term <tab> parent concept identifier
#
# October 2, 2012 - Add flag to generate a minimal OWL file or not

use strict;
use Config;
my $owlFlag = $ARGV[0] or die "Please set the OWL flag (-o or -n)";
open( my $map, $ARGV[1] ) or die "Couldn't open map file!\n";
open my $out, '>', $ARGV[4] or die "Couldn't create output file!\n";
my $root = $ARGV[2] or die "Please provide root identifier\n";
my $rootname = $ARGV[3] or die "Please provide root identifier label\n";
my $os = "unknown";

open my $owl, '>', "$root.owl" or die "Couldn't create the optional OWL file!\n";

my %parents=();
my %pts;
my $self_ref_end = "false";
my $generate_owl = "false";

if( $owlFlag =~ /\-[Oo]/ ) {
  print "You have chosen to generate a text hierarchy with an OWL file.\nThe assumption is you have a file in this directory named header.txt (if not, see Rob)\nOpening OWL header...\n";
  open( my $header, "header.txt" ) or die "Unable to find header.txt.  It needs to be in this directory.\nIf you don't have it, see Rob.\n";
  while(<$header>) {
    print $owl $_;
  }
  $generate_owl = "true";
}
elsif( $owlFlag =~ /\-[Nn]/ ) {
  print "You have chosen to generate just a text hierarchy.\n";
}
else {
  print "Incorrect flag value.\n\nUsage:\nGenerateHierarchy.exe [-o|-n] mapfile.txt rootidentifier rootname output.txt\n"
}

if( $Config{osname} =~ /(linux|darwin|MSWin32|MSWin64)/ ) {
    print "Detecting OS...\n";
    $os = $1;
}
else {
  print "Undetected Operating System: $Config{osname} (see Rob)\n";
}

while(<$map>) {
  if( $_ =~ /(.*)\t(.*)\t(.*)(\n)?/ ) {
    if( $3 eq $1 ) {
      print $out "Self referencing concept: $2 ($3) cannot be its own parent.\n";
      $self_ref_end = "true";
    }
    push @{$parents{$3}}, $1;
    $pts{$1} = $2;
  }
}

if( $self_ref_end eq "true" ) {
  die "The input contains self referencing concepts.  Fix those listed in output and run again.\n";
}

for my $key ( sort keys %parents ) {
         @{$parents{$key}} = sort { lc($pts{$a}) cmp lc($pts{$b}) } @{$parents{$key}};
         # remove duplicate children, while allowing poly-hierarchy
         my $prev = "not equal to $parents{$key}[0]";
         @{$parents{$key}} = grep($_ ne $prev && ($prev = $_, 1), @{$parents{$key}});
#          print "$key=>\n";
#          foreach( @{$parents{$key}} ) {
#            print $_."\n";
#          }
#          print "\n";
}

print $out $rootname."\n";

if( $generate_owl eq "true" ) {
  print $owl "  <owl:Class rdf:about=\"http://ncicb.nci.nih.gov/xml/owl/EVS/Thesaurus.owl#$root\">\n";
  print $owl "    <Preferred_Name rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\">$rootname</Preferred_Name>\n";
  print $owl "  </owl:Class>\n";
  print $owl "\n";
}

printChildren( $root, 1 );

sub printChildren {
  my ($name, $level) = @_;
  if( defined $parents{$name} ) {
    foreach(@{$parents{$name}}) {
      my $parent = $name;
      my $child = $_;
      my $level_print = $level;
      while( $level_print != 0 ) {
        print $out "\t"; $level_print--;
      }
      if( $generate_owl eq "true" ) {
        print $owl "  <owl:Class rdf:about=\"http://ncicb.nci.nih.gov/xml/owl/EVS/Thesaurus.owl#$child\">\n";
        print $owl "    <rdfs:subClassOf rdf:resource=\"http://ncicb.nci.nih.gov/xml/owl/EVS/Thesaurus.owl#$parent\"\/>\n";
        print $owl "    <Preferred_Name rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\">$pts{$child}</Preferred_Name>\n";
        print $owl "  </owl:Class>\n";
        print $owl "\n";
      }
      print $out $pts{$child}."\n";
      printChildren($child, $level+1);
    }
  }
}

if( $generate_owl eq "true" ) {
  print $owl "</rdf:RDF>\n";
  close($owl);
}
else {
  close($owl);
  if( $os eq "linux" || $os eq "darwin" ) {
    system("rm $root.owl");
  }
  elsif( $os eq "MSWin32" || $os eq "MSWin64" ) {
    system("DEL $root.owl");
  }
  else {
    print "Unable to remove temporary OWL file.\n";
  }
}
print "Generation complete.\n";
close($out);


# Rearticulate OWL based on multiple parenting


# for my $key ( sort keys %parents ) {
#         my $value = $parents{$key};
#         print "$key => ";
#         foreach( @{$parents{$key}} ) {
#           print $_;
#         }
#         print "\n";
# }