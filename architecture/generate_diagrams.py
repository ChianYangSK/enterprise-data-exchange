from PIL import Image, ImageDraw, ImageFont
from pathlib import Path

OUT = Path(__file__).parent
W, H = 1600, 900
FONT = "C:/Windows/Fonts/arial.ttf"
BOLD = "C:/Windows/Fonts/arialbd.ttf"
def font(n, bold=False): return ImageFont.truetype(BOLD if bold else FONT, n)
def canvas(): return Image.new("RGB", (W,H), "#f7f9fc")
def box(d, xy, title, subtitle="", fill="#ffffff", outline="#cbd5e1"):
    d.rounded_rectangle(xy, radius=16, fill=fill, outline=outline, width=3)
    x1,y1,x2,y2=xy; d.text((x1+20,y1+18),title,font=font(24,True),fill="#102a43")
    if subtitle: d.multiline_text((x1+20,y1+53),subtitle,font=font(16),fill="#486581",spacing=4)
def arrow(d,a,b,label=""):
    d.line((a,b),fill="#177e89",width=5); d.polygon([(b[0],b[1]),(b[0]-10,b[1]-18),(b[0]+10,b[1]-18)],fill="#177e89")
    if label: d.text(((a[0]+b[0])//2+12,(a[1]+b[1])//2-14),label,font=font(16,True),fill="#177e89")
def header(d,title,sub):
    d.rectangle((0,0,W,96),fill="#102a43"); d.text((58,23),title,font=font(34,True),fill="white"); d.text((60,65),sub,font=font(16),fill="#d9e2ec")

# API flow
im=canvas(); d=ImageDraw.Draw(im); header(d,"Enterprise API Flow","Secure, auditable exchange between external applications and internal healthcare systems")
items=[("External Client","Patient App / Partner API"),("API Gateway","HTTPS ingress"),("Authentication","Token validation"),("Signature Verification","HMAC-SHA256"),("Replay Protection","Timestamp + nonce"),("Rate Limiting","Abuse control"),("DMZ Exchange Service","Spring Boot boundary"),("Audit Logging","Traceable records"),("Data Masking","Privacy-safe response"),("Internal System Client","Restricted adapter"),("Internal Healthcare System","Internal API"),("Database","Internal-only access")]
coords=[]
for i,(t,s) in enumerate(items):
    col=i%4; row=i//4; x=70+col*390; y=150+row*190; coords.append((x,y)); box(d,(x,y,x+290,y+105),t,s, "#e6fffa" if i in (1,6,8) else "#ffffff")
for i in range(11):
    a=coords[i];b=coords[i+1]
    if i%4<3: arrow(d,(a[0]+290,a[1]+53),(b[0],b[1]+53))
    else: arrow(d,(a[0]+145,a[1]+105),(b[0]+145,b[1]))
d.rounded_rectangle((70,755,1530,850),radius=16,fill="#edf2f7",outline="#a0aec0",width=2)
d.text((100,775),"Response path",font=font(20,True),fill="#102a43");d.text((100,810),"Internal system  →  DMZ Exchange Service  →  Data Masking  →  API response  →  External client",font=font(21),fill="#177e89")
im.save(OUT/"enterprise-api-flow.png")

# zones
im=canvas();d=ImageDraw.Draw(im);header(d,"Network Security Zones","A controlled DMZ prevents direct external access to internal services and data")
zones=[((65,145,1535,280),"INTERNET / EXTERNAL NETWORK","External Client / Patient App","#edf2f7"),((65,330,1535,590),"DMZ ZONE","API Gateway  •  DMZ Exchange Service  •  Authentication  •  Signature Validation  •  Replay Protection  •  Rate Limiting  •  Audit","#e6fffa"),((65,650,1535,770),"INTERNAL NETWORK","Healthcare Management System  •  Internal API  •  Internal Services","#eef2ff"),((420,795,1180,875),"DATABASE ZONE","PostgreSQL / Enterprise DB — no direct Internet access","#fff5f5")]
for xy,t,s,c in zones: box(d,xy,t,s,c,"#718096")
arrow(d,(800,280),(800,330),"HTTPS")
d.rectangle((700,600,900,640),fill="#c53030",outline="#742a2a");d.text((743,609),"FIREWALL",font=font(18,True),fill="white")
arrow(d,(800,590),(800,600))
arrow(d,(800,640),(800,650),"Internal API")
arrow(d,(800,770),(800,795))
d.rounded_rectangle((1050,805,1495,860),radius=12,fill="#742a2a");d.text((1072,817),"SECURITY PRINCIPLE",font=font(15,True),fill="white");d.text((1072,839),"No direct Internet access to Database",font=font(14),fill="white")
im.save(OUT/"network-security-zone.png")
