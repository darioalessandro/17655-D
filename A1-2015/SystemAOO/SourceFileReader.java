/******************************************************************************************************************
* File:SourceFileReader.java
* Course: 17655
* Project: Assignment 1
* Copyright: Copyright (c) 2003 Carnegie Mellon University
* Versions:
*	1.0 November 2008 - Initial rewrite of original assignment 1 (ajl).
*
* Description:
*
* Reads bytes and writes Measurement objects.
*
* Parameters: 		None
*
* Internal Methods:
*
*	public void run() - this method must be overridden by this class.
*
******************************************************************************************************************/
import java.io.*;

public class SourceFileReader extends FilterFramework {

	final String filePath;

	public SourceFileReader(String filePath) {
		this.filePath = filePath;
	}

	public void run() {

		int var2 = 0;
		int var3 = 0;
		DataInputStream var4 = null;
		boolean var5 = false;

		try {
			var4 = new DataInputStream(new FileInputStream(this.filePath));
			System.out.println("\n" + this.getName() + "::Source reading file...");

			while(true) {
				byte var11 = var4.readByte();
				++var2;
				this.WriteFilterOutputPort(var11);
				++var3;
			}
		} catch (EOFException var9) {
			System.out.println("\n" + this.getName() + "::End of file reached...");

			try {
				var4.close();
				this.ClosePorts();
				System.out.println("\n" + this.getName() + "::Read file complete, bytes read::" + var2 + " bytes written: " + var3);
			} catch (Exception var8) {
				System.out.println("\n" + this.getName() + "::Problem closing input data file::" + var8);
			}
		} catch (IOException var10) {
			System.out.println("\n" + this.getName() + "::Problem reading input data file::" + var10);
		}
	}
}