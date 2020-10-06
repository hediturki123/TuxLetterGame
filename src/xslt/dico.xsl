<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : dico.xsl
    Created on : 6 octobre 2020, 15:40
    Author     : DELL
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
xmlns:dico="http://myGame/tux">
    <xsl:output method="html"/>

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    <xsl:template match="/">
        <html>
            <head>
                <title>dico.xsl</title>
            </head>
            <body>
                <h1>Dictionnaire</h1>
                <ul>
                    <xsl:apply-templates select="dico:dictionnaire/dico:mot"> 
                        <xsl:sort />
                    </xsl:apply-templates>
                </ul>
                
                    
            </body>
        </html>
    </xsl:template>
    
   
    <xsl:template match="dico:mot">
        <li><xsl:value-of select="."/>
        <b> niveau = </b> 
        <xsl:apply-templates select="@niveau" />
        </li>
    </xsl:template>
    
    
 </xsl:stylesheet>
