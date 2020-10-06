<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:tux="http://myGame/tux"
    version="1.0"
>
    <xsl:output method="html" indent="yes" encoding="UTF-8"/>
    <xsl:template match="/">
        <xsl:text disable-output-escaping='yes'>&lt;!DOCTYPE html&gt;&#013;</xsl:text>
        <html>
            <head>
                <meta charset="utf-8"/>
                <title>Dictionnaire du Tux Lettergame</title>
            </head>
            <body>
                <h1>Dictionnaire du Tux Lettergame</h1>
                <xsl:for-each select="//tux:mot[not(following-sibling::tux:mot/@niveau = @niveau)]">
                    <xsl:sort select="@niveau"/>
                    <xsl:call-template name="niveau">
                        <xsl:with-param name="numero" select="@niveau"/>
                    </xsl:call-template>
                </xsl:for-each>
            </body>
        </html>
    </xsl:template>

    <xsl:template name="niveau">
        <xsl:param name="numero"/>
        <section class="niveau_{$numero}">
            <h2>Niveau <xsl:value-of select="$numero"/></h2>
            <ul>
                <xsl:apply-templates select="//tux:mot[@niveau=$numero]">
                    <xsl:sort select="." order="ascending"/>
                </xsl:apply-templates>
            </ul>
        </section>
    </xsl:template>

    <xsl:template match="tux:mot">
        <li><xsl:value-of select="."/></li>
    </xsl:template>

</xsl:stylesheet>
