## Rob Wynne, LMCO
## Create a flat XML file from the CDRH subset report.
## This is based on sample file sample_devicecodes-flat.xml

use strict;
use Spreadsheet::ParseExcel;
use Spreadsheet::ParseExcel::Utility qw(col2int);

my $start = time();

# Modify these values for the text of item elements.
my $eventCodeText = "EventCode";
my $subsetItemText = "SubsetItem";
my $codeItemText = "CodeItem";

# These arrays are ordered based on how you would like tags and item attributes to appear in the XML.
# You may rearrange, and add to them.  They must match values listed in the config file.
#my @codeTags = ("fda_synonym", "fda_definition", "nci_definition", "parent_ncicode", "parent_fdacode", "parent_fdapt");
my @codeTags = ("fda_synonym", "fda_definition", "nci_definition");
my @codeItemAttributes = ("nci_code", "fda_code", "fda_pt");
my @subsetItemAttributes = ("source", "subset_code", "subset_name");
my @eventCodeAttributes = ("version", "date");

# These are our identifiers.
my $subsetName = "subset_name";
my $thesaurusCode = "nci_code";

# Do not modify past this point.

my %tagToCol = ();
my %eventMap = ();
my %subsetInfo = ();
my %subsetMap = ();
my %codeInfo = ();
my %codeMap = ();
my $parser   = Spreadsheet::ParseExcel->new();
my $workbook = $parser->parse($ARGV[0]);
my $date = "unknown";
my $version = "unknown";
open( my $configFile, $ARGV[1] ) or die "Couldn't open the config file!\nUsage:\n\tthisscript.pl excelfile.xls config.txt nameofoutput.xml\n";
open my $xmlOut, '>', $ARGV[2] or die "Couldn't create XML file!\nUsage:\n\tthisscript.pl excelfile.xls config.txt nameofoutput.xml\n";

if( $ARGV[0] =~ /.*(\d{4}\-\d{2}\-\d{2})\.xls/ ) {
  $date = $1;
}

while(<$configFile>) {
  chomp($_);
  my @args = split( /=/, $_ );
  $tagToCol{$args[1]} = col2int($args[0]);
}

for my $worksheet ( $workbook->worksheets() ) {
    my $sheetName = $worksheet->get_name();
    if( $sheetName =~ /.*(\d{2}\.\d{2}[a-z]?).*/ ) {
      $version = $1;
    }
    push @{$eventMap{$version}}, $version;
    push @{$eventMap{$version}}, $date;
    my ( $row_min, $row_max ) = $worksheet->row_range();
    for my $row ( $row_min+1 .. $row_max ) {             # We do not wish to parse the column headers.
        my $subset = $worksheet->get_cell( $row, $tagToCol{$subsetName} )->value;
        my $nciCode = $worksheet->get_cell( $row, $tagToCol{$thesaurusCode} )->value;
        if( !exists $subsetInfo{$subset} ) {
            loadSubsetAttrs($worksheet, $row, $subset);
        }
        if( !exists $codeInfo{$nciCode}) {
            loadCodeItemAttrs($worksheet, $row, $nciCode);
        }
        if( !exists $codeMap{$nciCode}) {
            loadCodeItem($worksheet, $row, $nciCode);
        }
        push @{$subsetMap{$subset}}, $nciCode;
    }
}


# TODO: Make purty w/subs using recursion
print $xmlOut "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
print $xmlOut "<$eventCodeText";
for( my $i=0; $i < @eventCodeAttributes; $i++ ) {
  print $xmlOut " $eventCodeAttributes[$i]='$eventMap{$version}[$i]'";
}
print $xmlOut ">\n";

for my $subsetKey ( sort keys %subsetInfo ) {
  print $xmlOut "\t<$subsetItemText";
  for( my $i=0; $i < @subsetItemAttributes; $i++ ) {
    print $xmlOut " $subsetItemAttributes[$i]='$subsetInfo{$subsetKey}[$i]'";
  }
  print $xmlOut ">\n";
  foreach( @{$subsetMap{$subsetKey}} ) {
    my $codeKey = $_;
    print $xmlOut "\t\t<$codeItemText";
    for( my $i=0; $i < @codeItemAttributes; $i++ ) {
      print $xmlOut " $codeItemAttributes[$i]='$codeInfo{$codeKey}[$i]'";
    }
    print $xmlOut ">\n";
    for( my $i=0; $i < @codeTags; $i++ ) {
      my $storedValue = $codeMap{$codeKey}[$i];
      if( $storedValue eq q{} ) {
        print $xmlOut "\t\t\t<$codeTags[$i] \/>\n";
      }
      else {
        my @values = split( /\s?\|\s?/, $storedValue );
        foreach( @values ) {
          print $xmlOut "\t\t\t<$codeTags[$i]>$_<\/$codeTags[$i]>\n";
        }
      }
    }
    print $xmlOut "\t\t<\/$codeItemText>\n";
  }
  print $xmlOut "\t<$\/$subsetItemText>\n";
}
print $xmlOut "<\/$eventCodeText>\n";

sub loadSubsetAttrs {
  my ($worksheet, $row, $subset) = @_;
  foreach(@subsetItemAttributes) {
    my $value = q{};
    my $cell = $worksheet->get_cell($row, $tagToCol{$_});
    if( defined $cell ) {
      $value = $cell->value();
      $value =~ s/'/&apos;/g;
    }
    push @{$subsetInfo{$subset}}, $value;
  }
}

sub loadCodeItemAttrs {
  my ($worksheet, $row, $nciCode) = @_;
  foreach(@codeItemAttributes) {
    my $value = q{};
    my $cell = $worksheet->get_cell($row, $tagToCol{$_});
    if( defined $cell ) {
        $value = $cell->value();
        $value =~ s/'/&apos;/g;
    }
    pushToCodeInfoMap($value, $nciCode);
  }
}

sub loadCodeItem {
  my ($worksheet, $row, $nciCode) = @_;
  foreach(@codeTags) {
    my $value = q{};
    my $cell = $worksheet->get_cell($row, $tagToCol{$_});
    if( defined $cell ) {
        $value = $cell->value();
    }
    pushToCodeMap($value, $nciCode);
  }
}

sub pushToCodeMap {
    my ($value, $nciCode) = @_;
    push @{$codeMap{$nciCode}}, $value;
}

sub pushToCodeInfoMap {
    my ($value, $nciCode) = @_;
    push @{$codeInfo{$nciCode}}, $value;
}

my $end = time();
my $runtime = ($end - $start);
print "Finished in $runtime seconds.\n";

# Just some debugging stuff.

# sub printTag {
#   my ($indentLevel, $tagName, $value, $attrRef) = @_;
#   my @attributes = @$attrRef;
#   for( my $i=0; $i < $indentLevel; $i++ ) {
#     print $xmlOut "\t";
#   }
#   print $xmlOut "<$tagName "
# }

# for my $key ( sort keys %codeMap ) {
#           print $xmlOut "$key\n======";
#           foreach(@{$codeMap{$key}}) {
#             print $xmlOut "\n$_";
#           }
#           print $xmlOut "\n\n";
# }

#     foreach(@{$subsetMap{$key}}) {
#       print $xmlOut "\n$_";
#     }
#     print $xmlOut "\n";
# }



#        for my $col ( $col_min .. $col_max ) {
#           push @{$subsetInfo{$subset}}, $worksheet->get_cell($row, $tagToCol{"source"} )->value;
#           push @{$subsetInfo{$subset}}, $worksheet->get_cell($row, $tagToCol{"subset_code"} )->value;
# for my $key ( keys %tagToCol ) {
#           print $xmlOut "$key\t$tagToCol{$key}\n"
# }


