import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";

export default defineConfig({
    plugins: [react()],
    server: {
        port: 5173, // keep the same as your Google OAuth settings
        proxy: {
            "/api": {
                target: "http://localhost:8080", // Spring Boot backend
                changeOrigin: true,
                secure: false,
            },
        },
    },
});
