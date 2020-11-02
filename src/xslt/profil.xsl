<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:tux="http://myGame/tux"
    version="1.0"
>
    <xsl:output method="html" version="5.0" encoding="UTF-8" indent="yes"/>

    <xsl:template match="/">
        <xsl:variable name="nom" select="//tux:nom"/>
        <xsl:text disable-output-escaping='yes'>&lt;!DOCTYPE html&gt;&#013;</xsl:text>
        <html lang="fr">
            <head>
                <meta charset="UTF-8"/>
                <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
                <title>Tux Lettergame • Profil</title>
                <link rel="stylesheet" href="../css/main.css"/>
                <link rel="stylesheet" href="../css/profil.css"/>
            </head>
            <body>
                <h1>Tux Lettergame • Profil</h1>
                <hr/>
                <div class="profil_parties">
                    <section id="profil">
                        <div class="contenu-profil">
                            <h2><xsl:value-of select="//tux:nom"/></h2>
                            <h3><xsl:text>
                                <small>Anniversaire le</small>
                                <br/>
                                <xsl:value-of select="
                                concat(
                                    substring(//tux:anniversaire,9,2),'/',
                                    substring(//tux:anniversaire,6,2),'/',
                                    substring(//tux:anniversaire,1,4)
                                )"/>
                            </xsl:text></h3>
                        </div>
                        <xsl:element name="img">
                            <xsl:attribute name="src">
                                <xsl:text>../img/profil/<xsl:value-of select="//tux:avatar"/></xsl:text>
                            </xsl:attribute>
                            <xsl:attribute name="alt"><xsl:value-of select="//tux:nom"/></xsl:attribute>
                        </xsl:element>
                    </section>
                    <section id="parties">
                        <h2>Historique des parties</h2>
                        <table>
                            <thead>
                                <th>Date</th>
                                <th>Mot à trouver</th>
                                <th>Difficulté</th>
                                <th>Progression</th>
                                <th>Temps écoulé</th>
                            </thead>
                            <tbody>
                                <xsl:apply-templates select="//tux:partie">
                                    <xsl:sort select="@date" order="ascending"/>
                                </xsl:apply-templates>
                            </tbody>
                        </table>
                    </section>
                </div>
            </body>
        </html>
    </xsl:template>

    <xsl:template match="tux:partie">
        <xsl:variable name="progress">
            <xsl:choose>
                <xsl:when test="string-length(@trouvé) = 3">
                    <xsl:value-of select="substring(@trouvé,1,2)"/>
                </xsl:when>
                <xsl:when test="string-length(@trouvé) = 2">
                    <xsl:value-of select="substring(@trouvé,1,1)"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="0"/>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>
        <xsl:variable name="temps">
            <xsl:choose>
                <xsl:when test="tux:temps"><xsl:value-of select="tux:temps"/></xsl:when>
                <xsl:otherwise>0.0</xsl:otherwise>
            </xsl:choose>
        </xsl:variable>
        <tr>
            <td><xsl:value-of select="
                concat(
                    substring(@date,9,2),'/',
                    substring(@date,6,2),'/',
                    substring(@date,1,4)
                )"/></td>
            <td><xsl:value-of select="tux:mot"/></td>
            <td>
                <xsl:choose>
                    <xsl:when test="tux:mot/@niveau = 1">
                        <p class="niveau_1">Facile</p>
                    </xsl:when>
                    <xsl:when test="tux:mot/@niveau = 2">
                        <p class="niveau_2">Normal</p>
                    </xsl:when>
                    <xsl:when test="tux:mot/@niveau = 3">
                        <p class="niveau_3">Avancé</p>
                    </xsl:when>
                    <xsl:when test="tux:mot/@niveau = 4">
                        <p class="niveau_4">Difficile</p>
                    </xsl:when>
                    <xsl:when test="tux:mot/@niveau = 5">
                        <p class="niveau_5">Expert</p>
                    </xsl:when>
                </xsl:choose>
                <small>Niveau <xsl:value-of select="tux:mot/@niveau"/></small>
            </td>
            <td>
                <xsl:element name="progress">
                    <xsl:attribute name="value"><xsl:value-of select="$progress"/></xsl:attribute>
                    <xsl:attribute name="max">100</xsl:attribute>
                    <xsl:value-of select="concat($progress,'%')"/>
                </xsl:element>
                <br/>
                <small><xsl:value-of select="concat($progress,'%')"/> complété</small>
            </td>
            <td><xsl:value-of select="$temps"/></td>
        </tr>
    </xsl:template>

    <!--
        Cette petite template permet d'éviter la génération d'espaces "sauvages" dans le document HTML.
        On devra en revanche récupérer nous-mêmes les noeuds textes dont on a besoin par la suite.
    -->
    <xsl:template match="text()"/>

</xsl:stylesheet>
