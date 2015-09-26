// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Plane.java

import java.awt.Color;
import java.awt.Graphics;

public class Plane
{

    public Plane(Medium medium, int ai[], int ai1[], int ai2[], int i, int ai3[], boolean flag, 
            int j, int k, int l, int i1, byte light, boolean hidepoly)
    {
    	this.hidepoly = hidepoly;
    	this.light = light;
        c = new int[3];
        hsb = new float[3];
        glass = false;
        gr = 0;
        fs = 0;
        wx = 0;
        wz = 0;
        deltaf = 1.0F;
        projf = 1.0F;
        av = 0;
        lav = 0;
        imlast = false;
        m = medium;
        n = i;
        ox = new int[n];
        oz = new int[n];
        oy = new int[n];
        for(int j1 = 0; j1 < n; j1++)
        {
            ox[j1] = ai[j1];
            oy[j1] = ai2[j1];
            oz[j1] = ai1[j1];
        }

        glass = flag;
        if(!glass)
			for(int k1 = 0; k1 < 3; k1++)
                c[k1] = ai3[k1];
		else
			for(int l1 = 0; l1 < 3; l1++)
                c[l1] = m.csky[l1];
        Color.RGBtoHSB(c[0], c[1], c[2], hsb);
        gr = j;
        fs = k;
        wx = l;
        wz = i1;
        for(int i2 = 0; i2 < 3; i2++)
			for(int j2 = 0; j2 < 3; j2++)
                if(j2 != i2)
                    deltaf *= (float)(Math.sqrt((ox[j2] - ox[i2]) * (ox[j2] - ox[i2]) + (oy[j2] - oy[i2]) * (oy[j2] - oy[i2]) + (oz[j2] - oz[i2]) * (oz[j2] - oz[i2])) / 100D);

        deltaf /= 3F;
    }

    public void loadprojf()
    {
        projf = 1.0F;
        for(int i = 0; i < 3; i++)
			for(int j = 0; j < 3; j++)
                if(j != i)
                    projf *= (float)(Math.sqrt((ox[i] - ox[j]) * (ox[i] - ox[j]) + (oz[i] - oz[j]) * (oz[i] - oz[j])) / 100D);

        projf /= 3F;
    }

    public void d(Graphics g, int i, int j, int k, int l, int i1, int j1, 
            int k1, boolean flag, boolean flag1, boolean toofar)
    {
        int ai[] = new int[n];
        int ai1[] = new int[n];
        int ai2[] = new int[n];
        for(int l1 = 0; l1 < n; l1++)
        {
            ai[l1] = ox[l1] + i;
            ai2[l1] = oy[l1] + j;
            ai1[l1] = oz[l1] + k;
        }

        if(wx != 0)
            rot(ai, ai1, wx + i, wz + k, k1, n);
        rot(ai, ai2, i, j, i1, n);
        rot(ai2, ai1, j, k, j1, n);
        rot(ai, ai1, i, k, l, n);
        if(i1 != 0 || j1 != 0 || l != 0)
        {
            projf = 1.0F;
            for(int i2 = 0; i2 < 3; i2++)
				for(int j2 = 0; j2 < 3; j2++)
                    if(j2 != i2)
                        projf *= (float)(Math.sqrt((ai[i2] - ai[j2]) * (ai[i2] - ai[j2]) + (ai1[i2] - ai1[j2]) * (ai1[i2] - ai1[j2])) / 100D);

            projf /= 3F;
        }
        rot(ai, ai1, m.cx, m.cz, m.xz, n);
        boolean flag2 = false;
        int ai3[] = new int[n];
        int ai4[] = new int[n];
        int k2 = 500;
        for(int l2 = 0; l2 < n; l2++)
        {
            ai3[l2] = xs(ai[l2], ai1[l2]);
            ai4[l2] = ys(ai2[l2], ai1[l2]);
        }

        int i3 = 0;
        int j3 = 1;
        for(int k3 = 0; k3 < n; k3++)
			for(int j4 = 0; j4 < n; j4++)
                if(k3 != j4 && Math.abs(ai3[k3] - ai3[j4]) - Math.abs(ai4[k3] - ai4[j4]) < k2)
                {
                    j3 = k3;
                    i3 = j4;
                    k2 = Math.abs(ai3[k3] - ai3[j4]) - Math.abs(ai4[k3] - ai4[j4]);
                }

        if(ai4[i3] < ai4[j3])
        {
            int l3 = i3;
            i3 = j3;
            j3 = l3;
        }
        if(spy(ai[i3], ai1[i3]) > spy(ai[j3], ai1[j3]))
        {
            flag2 = true;
            int i4 = 0;
            for(int k4 = 0; k4 < n; k4++)
                if(ai1[k4] < 50 && ai2[k4] > m.cy)
                    flag2 = false;
                else
                if(ai2[k4] == ai2[0])
                    i4++;

            if(i4 == n && ai2[0] > m.cy)
                flag2 = false;
        }
        rot(ai2, ai1, m.cy, m.cz, m.zy, n);
        boolean flag3 = true;
        int ai5[] = new int[n];
        int ai6[] = new int[n];
        int l4 = 0;
        int i5 = 0;
        int j5 = 0;
        int k5 = 0;
        for(int l5 = 0; l5 < n; l5++)
        {
            ai5[l5] = xs(ai[l5], ai1[l5]);
            ai6[l5] = ys(ai2[l5], ai1[l5]);
            if(ai6[l5] < 0 || ai1[l5] < 10)
                l4++;
            if(ai6[l5] > m.h || ai1[l5] < 10)
                i5++;
            if(ai5[l5] < 0 || ai1[l5] < 10)
                j5++;
            if(ai5[l5] > m.w || ai1[l5] < 10)
                k5++;
        }

        if(j5 == n || l4 == n || i5 == n || k5 == n)
        {
            flag3 = false;
            toofar = true;
        }
        if(flag3)
        {
            if(imlast)
				for(int i6 = 0; i6 < 3; i6++)
                {
                    m.lxp[i6] = ai5[i6];
                    m.lyp[i6] = ai6[i6];
                }
            int j6 = 1;
            byte byte0 = 1;
            byte byte1 = 1;
            if(Math.abs(ai6[0] - ai6[1]) > Math.abs(ai6[2] - ai6[1]))
            {
                byte0 = 0;
                byte1 = 2;
            } else
            {
                byte0 = 2;
                byte1 = 0;
                j6 *= -1;
            }
            if(ai6[1] > ai6[byte0])
                j6 *= -1;
            if(ai5[1] > ai5[byte1])
                j6 *= -1;
            int l6 = gr;
            if(fs != 0)
            {
                j6 *= fs;
                if(j6 == -1)
                {
                    l6 += 40;
                    if(m.mode == 0)
                        flag3 = false;
                }
            }
            int j7 = 0;
            int l7 = 0;
            int j8 = 0;
            int k8 = 0;
            int l8 = 0;
            int i9 = 0;
            for(int j9 = 0; j9 < n; j9++)
            {
                int l9 = 0;
                int j10 = 0;
                int l10 = 0;
                int i11 = 0;
                int j11 = 0;
                int k11 = 0;
                for(int l11 = 0; l11 < n; l11++)
                {
                    if(ai2[j9] >= ai2[l11])
                        l9++;
                    if(ai2[j9] <= ai2[l11])
                        j10++;
                    if(ai[j9] >= ai[l11])
                        l10++;
                    if(ai[j9] <= ai[l11])
                        i11++;
                    if(ai1[j9] >= ai1[l11])
                        j11++;
                    if(ai1[j9] <= ai1[l11])
                        k11++;
                }

                if(l9 == n)
                    j7 = ai2[j9];
                if(j10 == n)
                    l7 = ai2[j9];
                if(l10 == n)
                    j8 = ai[j9];
                if(i11 == n)
                    k8 = ai[j9];
                if(j11 == n)
                    l8 = ai1[j9];
                if(k11 == n)
                    i9 = ai1[j9];
            }

            int k9 = (j7 + l7) / 2;
            int i10 = (j8 + k8) / 2;
            int k10 = (l8 + i9) / 2;
            av = (int)Math.sqrt((m.cy - k9) * (m.cy - k9) + (m.cx - i10) * (m.cx - i10) + k10 * k10 + l6 * l6 * l6);
            if(av > m.fade[7] || av == 0)
            {
                flag3 = false;
                toofar = true;
            }
            if(l6 > 0 && av > 2500)
                flag3 = false;
            if(l6 > 0 && av > 1000)
                toofar = true;
            if(av > 2000)
                toofar = true;
        }
        if(flag3)
        {
            float f = (float)((double)(projf / deltaf) + 0.5D);
            if(f > 1.0F)
                f = 1.0F;
            if((double)f < 0.5D || flag2)
                f = 0.5F;
            if(toofar)
                f = (float)((double)f * 0.90000000000000002D);
            new Color(c[0], c[1], c[2]);
            Color color = Color.getHSBColor(hsb[0], hsb[1], hsb[2] * f);
            int k6 = color.getRed();
            int i7 = color.getGreen();
            int k7 = color.getBlue();
            for(int i8 = 0; i8 < 8; i8++)
                if(av > m.fade[i8])
                {
                    k6 = (k6 * 3 + m.cfade[0]) / 4;
                    i7 = (i7 * 3 + m.cfade[1]) / 4;
                    k7 = (k7 * 3 + m.cfade[2]) / 4;
                }
            
            g.setColor(new Color(k6, i7, k7));
            g.fillPolygon(ai5, ai6, n);
        }
        if(!toofar && !hidepoly)
        {
            int k6;
            int i7;
            int k7;
        	if(m.lightson && light != 0)
            {
            	k6 = c[0];
                if(k6 > 255)
					k6 = 255;
                if(k6 < 0)
					k6 = 0;
                i7 = c[1];
                if(i7 > 255)
					i7 = 255;
                if(i7 < 0)
					i7 = 0;
                k7 = c[2];
                if(k7 > 255)
					k7 = 255;
                if(k7 < 0)
					k7 = 0;  
                g.setColor(new Color(k6, i7, k7));
            }
        	else 
        		g.setColor(new Color(0, 0, 0));
            g.drawPolygon(ai5, ai6, n);
        }
    }

    public void s(Graphics g, int i, int j, int k, int l, int i1, int j1, 
            boolean flag)
    {
        if(gr <= 0 && av < m.fade[7] && av != 0)
        {
            int ai[] = new int[n];
            int ai1[] = new int[n];
            int ai2[] = new int[n];
            for(int k1 = 0; k1 < n; k1++)
            {
                ai[k1] = ox[k1] + i;
                ai2[k1] = oy[k1] + j;
                ai1[k1] = oz[k1] + k;
            }

            rot(ai, ai2, i, j, i1, n);
            rot(ai2, ai1, j, k, j1, n);
            rot(ai, ai1, i, k, l, n);
            int l1 = (int)((double)(float)m.cgrnd[0] / 1.5D);
            int i2 = (int)((double)(float)m.cgrnd[1] / 1.5D);
            int j2 = (int)((double)(float)m.cgrnd[2] / 1.5D);
            for(int k2 = 0; k2 < n; k2++)
                ai2[k2] = m.ground;

            for(int l2 = 0; l2 < m.tr.nt; l2++)
                if(m.tr.in[l2])
                {
                    int i3 = 0;
                    for(int j3 = 0; j3 < n; j3++)
                        if(Math.abs(ai[j3] - m.tr.x[l2]) < m.tr.radx[l2] && Math.abs(ai1[j3] - m.tr.z[l2]) < m.tr.radz[l2])
                            i3++;

                    if(i3 == n)
                    {
                        for(int k3 = 0; k3 < n; k3++)
                            ai2[k3] = m.tr.y[l2];

                        if(m.tr.xy[l2] != 0)
                        {
                            for(int l3 = 0; l3 < n; l3++)
                            {
                                ai[l3] -= i;
                                ai[l3] = (int)((double)ai[l3] * (1.0D / Math.cos((double)m.tr.xy[l2] * 0.017453292519943295D)));
                                ai[l3] += i;
                            }

                            rot(ai, ai2, m.tr.x[l2], m.tr.y[l2], m.tr.xy[l2], n);
                        }
                        if(m.tr.zy[l2] != 0)
                        {
                            for(int i4 = 0; i4 < n; i4++)
                            {
                                ai1[i4] -= k;
                                ai1[i4] = (int)((double)ai1[i4] * (1.0D / Math.cos((double)m.tr.zy[l2] * 0.017453292519943295D)));
                                ai1[i4] += k;
                            }

                            rot(ai1, ai2, m.tr.z[l2], m.tr.y[l2], m.tr.zy[l2], n);
                        }
                        l1 = (int)((double)(float)m.tr.c[l2][0] / 1.5D);
                        i2 = (int)((double)(float)m.tr.c[l2][1] / 1.5D);
                        j2 = (int)((double)(float)m.tr.c[l2][2] / 1.5D);
                    }
                }

            rot(ai, ai1, m.cx, m.cz, m.xz, n);
            rot(ai2, ai1, m.cy, m.cz, m.zy, n);
            boolean flag1 = false;
            int ai3[] = new int[n];
            int ai4[] = new int[n];
            for(int j4 = 0; j4 < n; j4++)
            {
                ai3[j4] = xs(ai[j4], ai1[j4]);
                ai4[j4] = ys(ai2[j4], ai1[j4]);
                if(ai4[j4] > 0 && ai4[j4] < m.h && ai3[j4] > 0 && ai3[j4] < m.w && ai1[j4] > 10)
                    flag1 = true;
            }

            if(flag1)
            {
                for(int k4 = 0; k4 < 8; k4++)
                    if(av > m.fade[k4])
                    {
                        l1 = (l1 * 3 + m.cfade[0]) / 4;
                        i2 = (i2 * 3 + m.cfade[1]) / 4;
                        j2 = (j2 * 3 + m.cfade[2]) / 4;
                    }

                g.setColor(new Color(l1, i2, j2));
                g.fillPolygon(ai3, ai4, n);
            }
        }
    }

    public int xs(int i, int j)
    {
        if(j < 10)
            j = 10;
        return ((j - m.focus_point) * (m.cx - i)) / j + i;
    }

    public int ys(int i, int j)
    {
        if(j < 10)
            j = 10;
        return ((j - m.focus_point) * (m.cy - i)) / j + i;
    }

    public void rot(int ai[], int ai1[], int i, int j, int k, int l)
    {
        if(k != 0)
			for(int i1 = 0; i1 < l; i1++)
            {
                int j1 = ai[i1];
                int k1 = ai1[i1];
                ai[i1] = i + (int)((double)(j1 - i) * Math.cos((double)k * 0.017453292519943295D) - (double)(k1 - j) * Math.sin((double)k * 0.017453292519943295D));
                ai1[i1] = j + (int)((double)(j1 - i) * Math.sin((double)k * 0.017453292519943295D) + (double)(k1 - j) * Math.cos((double)k * 0.017453292519943295D));
            }
    }

    public int spy(int i, int j)
    {
        return (int)Math.sqrt((i - m.cx) * (i - m.cx) + j * j);
    }

    Medium m;
    int ox[];
    int oy[];
    int oz[];
    int n;
    int c[];
    float hsb[];
    boolean glass;
    boolean hidepoly;
    byte light;
    int gr;
    int fs;
    int wx;
    int wz;
    float deltaf;
    float projf;
    int av;
    int lav;
    boolean imlast;
}
