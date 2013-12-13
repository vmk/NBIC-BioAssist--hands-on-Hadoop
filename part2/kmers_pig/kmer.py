@outputSchema("bag{}")
def kmer(str, k):
	out = []
	for i in range(0, len(str)+1-k):
		out.append(str[i:i+k])
	return out
