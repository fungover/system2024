import {defineConfig} from 'vite'
import react from '@vitejs/plugin-react'

export default defineConfig({
  plugins: [react()],
  server: {
    proxy: {
      '/login': 'http://localhost:8080',
      '/logout': 'http://localhost:8080',
      '/oauth2': 'http://localhost:8080',
      '/api': 'http://localhost:8080'
    }
  },
  build: {
    outDir: '../src/main/resources/static/',
    emptyOutDir: true,
  },
});