package com.digi.xbee.api.packet.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;

import com.digi.xbee.api.models.XBee16BitAddress;
import com.digi.xbee.api.models.XBee64BitAddress;
import com.digi.xbee.api.packet.XBeeAPIPacket;
import com.digi.xbee.api.packet.XBeeAPIType;
import com.digi.xbee.api.utils.HexUtils;

/**
 * This class represents a ZigBee Receive packet. Packet is built using the parameters of 
 * the constructor.
 * 
 * When the module receives an RF packet, it is sent out the UART using this message type.
 * 
 * This packet is received when external devices send transmit request packets to this module.
 * See {@link com.digi.xbee.packet.common.TransmitPacket}.
 * 
 * Among received data, some options can also be received indicating transmission parameters.
 * See {@link com.digi.xbee.models.XBeeReceiveOptions}. 
 */
public class ReceivePacket extends XBeeAPIPacket {

	// Variables
	private XBee64BitAddress sourceAddress64;
	
	private XBee16BitAddress sourceAddress16;
	
	private int receiveOptions;
	
	private byte[] receivedData;

	/**
	 * Class constructor. Instances a new object of type ZigBeeReceivePacket with
	 * the given parameters.
	 * 
	 * @param sourceAddress64 64-bit address of the sender.
	 * @param sourceAddress16 16-bit address of the sender.
	 * @param receiveOptions Bitfield indicating the receive options. See {@link com.digi.xbee.models.XBeeReceiveOptions}.
	 * @param receivedData Received RF data.
	 */
	public ReceivePacket( XBee64BitAddress sourceAddress64, XBee16BitAddress sourceAddress16, int receiveOptions, byte[] receivedData){
		super(XBeeAPIType.RECEIVE_PACKET);
		this.sourceAddress64 = sourceAddress64;
		this.sourceAddress16 = sourceAddress16;
		this.receiveOptions = receiveOptions;
		this.receivedData = receivedData;
	}


	/*
	 * (non-Javadoc)
	 * @see com.digi.xbee.packet.XBeeAPIPacket#getAPIData()
	 */
	public byte[] getAPIData() {
		ByteArrayOutputStream data = new ByteArrayOutputStream();
		try {
			data.write(sourceAddress64.getValue());
			data.write(sourceAddress16.getValue());
			data.write(receiveOptions);
			if (receivedData != null)
				data.write(receivedData);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data.toByteArray();
	}

	/*
	 * (non-Javadoc)
	 * @see com.digi.xbee.packet.XBeeAPIPacket#hasAPIFrameID()
	 */
	public boolean hasAPIFrameID() {
		return false;
	}
	
	/**
	 * Sets the 64 bit sender/source address.
	 * 
	 * @param sourceAddress64 The 64 bit sender/source address.
	 */
	public void set64bitAddress(XBee64BitAddress sourceAddress64) {
		this.sourceAddress64 = sourceAddress64;
	}
	
	/**
	 * Retrieves the 64 bit sender/source address. 
	 * 
	 * @return The 64 bit sender/source address.
	 */
	public XBee64BitAddress get64bitAddress() {
		return sourceAddress64;
	}
	
	/**
	 * Sets the 16 bit sender/source address.
	 * 
	 * @param sourceAddress16 The 16 bit sender/source address.
	 */
	public void set16bitAddress(XBee16BitAddress sourceAddress16) {
		this.sourceAddress16 = sourceAddress16;
	}
	
	/**
	 * Retrieves the 16 bit sender/source address.
	 * 
	 * @return The 16 bit sender/source address.
	 */
	public XBee16BitAddress get16bitAddress() {
		return sourceAddress16;
	}
	
	/**
	 * Sets the receive options bitfield.
	 * See {@link com.digi.xbee.models.XBeeReceiveOptions}.
	 * 
	 * @param options Receive options bitfield.
	 */
	public void setReceiveOptions(int options) {
		this.receiveOptions = options;
	}
	
	/**
	 * Retrieves the receive options bitfield.
	 * See {@link com.digi.xbee.models.XBeeReceiveOptions}.
	 * 
	 * @return Receive options bitfield.
	 */
	public int getReceiveOptions() {
		return receiveOptions;
	}
	
	/**
	 * Sets the received RF data.
	 * 
	 * @param receivedData Received RF data.
	 */
	public void setReceivedData(byte[] receivedData) {
		this.receivedData = receivedData;
	}
	
	/**
	 * Retrieves the received RF data.
	 * 
	 * @return Received RF data.
	 */
	public byte[] getReceivedData() {
		return receivedData;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.digi.xbee.packet.XBeeAPIPacket#getAPIPacketParameters()
	 */
	public LinkedHashMap<String, String> getAPIPacketParameters() {
		LinkedHashMap<String, String> parameters = new LinkedHashMap<String, String>();
		parameters.put("64-bit source address", HexUtils.prettyHexString(sourceAddress64.toString()));
		parameters.put("16-bit source address", HexUtils.prettyHexString(sourceAddress16.toString()));
		parameters.put("Receive options", HexUtils.prettyHexString(HexUtils.integerToHexString(receiveOptions, 1)));
		if (receivedData != null)
			parameters.put("Received data", HexUtils.prettyHexString(HexUtils.byteArrayToHexString(receivedData)));
		return parameters;
	}
}
