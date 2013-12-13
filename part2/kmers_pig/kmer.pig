register 'kmer.py' using jython as udf;
in = load '$input' as (data:chararray);
seqs = filter in by (data matches '[ACTGN]+');
pairs = foreach seqs generate flatten(udf.kmer(data, 2)) as pair;
grouped = group pairs by pair;
counts = foreach grouped generate group, COUNT(pairs);
dump counts;

