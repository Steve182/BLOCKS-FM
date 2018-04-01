package com.blocksfm.test;

import java.io.BufferedReader;
import java.io.IOException;

import jline.internal.InputStreamReader;

public class TestUtils
{
	public static String readConsoleLine() throws IOException
	{
		try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in)))
		{
			return br.readLine();
		}
	}
}