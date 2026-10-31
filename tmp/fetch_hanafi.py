import urllib.request
import re

req = urllib.request.Request('https://shamela.ws/category/14', headers={'User-Agent': 'Mozilla/5.0'})
try:
    with urllib.request.urlopen(req) as resp:
        html = resp.read().decode('utf-8')
        # Books usually have links like /book/123 or https://shamela.ws/book/123
        matches = re.findall(r'<a\s+[^>]*href=["\'](/book/\d+|https://shamela\.ws/book/\d+)["\'][^>]*>(.*?)</a>', html, re.DOTALL)
        for url, inner in matches:
            clean_title = re.sub(r'<[^>]+>', '', inner).strip()
            if clean_title:
                full_url = url if url.startswith('http') else 'https://shamela.ws' + url
                print(f"{full_url} | {clean_title}")
except Exception as e:
    print(f"Error: {e}")
