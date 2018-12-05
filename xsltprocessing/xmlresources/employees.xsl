<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:output method="text"/>

  <xsl:template match="/">
     Employees: <xsl:apply-templates select="/Personnel/Employee"/>
  </xsl:template>

  <xsl:template match="Employee">
    Name - <xsl:value-of select="Name" />
    Age -  <xsl:value-of select="Age" />
    <xsl:text>&#xa;</xsl:text>
  </xsl:template>

</xsl:stylesheet>