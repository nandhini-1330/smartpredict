SmartPredict – Intelligent Vehicle Fault Detection and Priority Alert System

Overview
SmartPredict is a real-time vehicle fault detection system that monitors operational parameters such as speed, engine temperature, battery level, tire pressure, and brake condition.
The system detects abnormal conditions and generates prioritized alerts (CRITICAL / HIGH) to ensure vehicle safety and preventive maintenance.

Problem Statement
Modern vehicles generate continuous telemetry data. Detecting faults early and prioritizing them correctly is essential for:
Preventing accidents
Reducing maintenance cost
Improving vehicle reliability
Enhancing driver safety
SmartPredict provides a lightweight backend system that analyzes vehicle data and produces intelligent alerts in real time.

Features
Real-time fault detection
Priority-based alert classification
Critical and High alert levels
Lightweight pure Java backend (No frameworks used)
Modern responsive web interface
Custom HTTP server implementation
Easy local deployment

Demo Test Case
Input:
Speed: 90
Temperature: 120
Battery: 10
Tire Pressure: 20
Brake: Not Working

Output:
Engine Overheat - CRITICAL
Brake Failure - CRITICAL
Battery Low - HIGH
Low Tire Pressure - HIGH
