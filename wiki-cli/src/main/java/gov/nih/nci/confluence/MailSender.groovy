package gov.nih.nci.confluence

import org.apache.commons.net.smtp.SimpleSMTPHeader
import org.apache.commons.net.smtp.SMTPClient


class MailSender {

  static boolean sendMessage( String host, int port, String from, List recipients, String subject, String message)
  {
    int timeout = 2000
    int soTimeout = 10000

    def client = new SMTPClient()
    client.defaultTimeout = timeout
    println "Set default timeout to ${client.defaultTimeout}."

    println "Connecting to ${host} ..."
    client.connect(host, port)

    client.soTimeout = soTimeout

    if (!client.setSender(from)) {
      throw new Exception("Error setting sender!");
    }
    println client.replyString

    recipients.each {
      println "Adding recipient ${it} ..."
      if (!client.addRecipient(it)) {
        throw new Exception("Error adding recipient!");
      }
      println client.replyString
    }

    def header = new SimpleSMTPHeader(from, recipients[0], subject)
    if (recipients.size() > 1) {
      recipients[1..-1].each {
        println "Adding CC recipient ${it} ..."
        header.addCC(it)
      }
    }
    println "Headers ...\n${header}"

    if (!client.sendShortMessageData(header.toString() + message)) {
      throw new Exception("Error sending message!");
    }
    println client.replyString

    disconnect(client)

    return true;
  }

  static void disconnect(SMTPClient client) 
  {
    if ((client != null) && (client.connected)) {
      println "Disconnecting client ..."
      try {
        client.disconnect()
        println "Client disconnected."
      } catch (Throwable t) {
        println "Error disconnecting client: ${t.localizedMessage}!"
      }
    }
  }
  
}