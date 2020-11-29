#!/usr/bin/python
import subprocess

import flask

from flask import Flask, request

app = flask.Flask("natbag2020_app")

@app.route("/departures")
def dep():
    return subprocess.check_output(["java", "-classpath",
                                    "C:/Users/romvi/git/AirportFinal/NewNatbag2020/bin", "Airport.Main",
                                    request.args.get('outformat'), "departures",
                                    request.args.get('airline'), request.args.get('country'),
                                    request.args.get('city'), request.args.get('airport'),
                                    request.args.get('day1'), request.args.get('month1'),
                                    request.args.get('year1'), request.args.get('day2'),
                                    request.args.get('month2'), request.args.get('year2'),
                                    request.args.get('sunday'), request.args.get('monday'),
                                    request.args.get('tuesday'), request.args.get('wednesday'),
                                    request.args.get('thursday'), request.args.get('friday'),
                                    request.args.get('saturday')])

@app.route("/arrivals")
def arr():
    return subprocess.check_output(["java", "-classpath",
                                    "C:/Users/romvi/git/AirportFinal/NewNatbag2020/bin", "Airport.Main",
                                    request.args.get('outformat'), "arrivals",
                                    request.args.get('airline'), request.args.get('country'),
                                    request.args.get('city'), request.args.get('airport'),
                                    request.args.get('day1'), request.args.get('month1'),
                                    request.args.get('year1'), request.args.get('day2'),
                                    request.args.get('month2'), request.args.get('year2'),
                                    request.args.get('sunday'), request.args.get('monday'),
                                    request.args.get('tuesday'), request.args.get('wednesday'),
                                    request.args.get('thursday'), request.args.get('friday'),
                                    request.args.get('saturday')])

app.run(port=8000, host="0.0.0.0")