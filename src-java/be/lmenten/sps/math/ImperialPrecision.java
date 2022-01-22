package be.lmenten.sps.math;

public enum ImperialPrecision
{
	ONE_INCH,    // 0
	ONE_2TH,    // 1
	ONE_4TH,    // 2
	ONE_8TH,    // 3
	ONE_16TH,    // 4
	ONE_32TH,    // 5
	ONE_64TH,    // 6
	;

	double getDivisor()
	{
		return 1.0D / getPowerOf2();
	}

	int getPowerOf2()
	{
		return 1 << ordinal();
	}
}
