<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : profil.xsl
    Created on : 6 octobre 2020, 17:03
    Author     : DELL
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
xmlns:profil="http://myGame/tux">
    <xsl:output method="html"/>

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    <xsl:template match="/">
        <html>
            <head>
                <title>profil.xsl</title>
            </head>
            <body>
                <h1>Dictionnaire</h1>
                
                <table border="1">
                    <tr>
                        <th>Avatar</th>
                        <th>Nom</th>
                        <th>Score</th> 
                    </tr>
                      
                    <tr>
                        <td><xsl:apply-templates select="profil:profil/profil:avatar"/></td>
                        <td><xsl:apply-templates select="profil:profil/profil:nom"/></td>
                        <td><xsl:value-of select="sum(profil:profil/profil:parties/profil:partie/profil:mot/@niveau)"/></td>
                    </tr>
       
                </table>
            </body>
        </html>
    </xsl:template>
    
    <xsl:template match="profil:nom">
        <xsl:value-of select="."/>
    </xsl:template>

</xsl:stylesheet>
