import React, { Component } from 'react';

// This imports all of the external functionality we want from react-google-maps
import {GoogleMap, Marker, Polyline, withGoogleMap, KmlLayer} from 'react-google-maps';


class Map extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            currentTrip : this.props.currentTrip,
            allPairs: this.props.allPairs
        };

        this.startLocation
        this.googleMap
    }

    /* Re-renders the map when currentTrip is updated*/
    componentWillReceiveProps(nextProps) {
        if (nextProps.currentTrip !== this.props.currentTrip) {
            this.setState({currentTrip: nextProps.currentTrip});
        }

        if (nextProps.allPairs !== this.props.allPairs) {
            this.setState({allPairs: nextProps.allPairs});
        }
    }

    /*
     * returns an array of coordinate pairs for google maps to draw the trip on the map
     */
    buildCoordinateArray() {

        var trip = [];

        // Add each coordinate pair to the trip
        if (this.state.allPairs.length > 0) {
            for (let i in this.state.allPairs) {
                trip.push(this.buildCoordinatePair(i));
            }
        } else this.startLocation = null;

        // make round trip again and update start location
        if (trip.length > 0) {
            trip.push(trip[0]);
            trip.push(trip[0]);
            this.startLocation = trip[0]
        }
            return trip
    }

    /*
     * Builds a pair of coordinates for a destination
     */
    buildCoordinatePair(i){
        let lng = this.getLongitude(i);
        let lat = this.getLatitude(i);
        return {lng: lng, lat: lat}
    }

    /*
     * returns Longitude of a destination
     */
    getLongitude(i){
        return parseFloat(this.state.allPairs[i]['start longitude'].slice(17))
    }

    /*
     * returns Latitude of a destination
     */
    getLatitude(i){
        return parseFloat(this.state.allPairs[i]['start latitude'].slice(16))
    }

    /*
     * returns the starting location coordinates
     */
    getStartLocation(){

        if (this.startLocation){
            return this.startLocation
        }
        return null
    }

    getKmlString(){
      if(this.googleMap.props.children[0].props.path.length <= 0)
      {
        return ""
      }
      let setup = this.getKmlSetup()
      let path  = this.getKmlPath()
      let start = this.getKmlStart()

      return setup + path + start

    }

  getKmlStart() {
    return '    </Placemark>\n'
        + '\t<Placemark>\n'
        + '\t    <name>Simple placemark</name>\n'
        + '\t    <description>Attached to the ground. Intelligently places itself \n'
        + '\t       at the height of the underlying terrain.</description>\n'
        + '\t    <Point>\n'
        + '\t      <coordinates>9.290300369262695,47.40549850463867,0</coordinates>\n'
        + '\t    </Point>\n'
        + '\t  </Placemark>\n'
        + '  </Document>\n'
        + '</kml>'
  }

  getKmlPath() {
    return '<LineString>\n'
        + '        <extrude>1</extrude>\n'
        + '        <tessellate>1</tessellate>\n'
        + '        <coordinates>\n'
        + this.getCords()
        + '        </coordinates>\n'
        + '      </LineString>\n'
        + '    </Placemark>'
  }

  getCords() {
    return "\t\t9.290300369262695,47.40549850463867,2573\n"
        + "\t\t7.8380556,51.8844444,2573\n"
        + "\t\t10.8169444,50.7783333,2573\n"
        + "\t\t-97.92030334472656,49.166900634765625,2573\n"
        + "\t\t-103.65299987792969,38.055599212646484,2573\n"
        + "\t\t-81.85559844970703,26.650800704956055,2573\n"
        + "\t\t-83.90139770507812,43.186100006103516,2573\n"
        + "\t\t-123.80500030517578,42.701499938964844,2573\n"
        + "\t\t-103.090833,31.849722,2573\n"
        + "\t\t-103.200996399,31.779600143399996,2573\n"
        + "\t\t9.290300369262695,47.40549850463867,2573\n"
        + "\t\t9.290300369262695,47.40549850463867,2573"
  }

  getKmlSetup() {
    return '<?xml version="1.0" encoding="UTF-8"?>\n'
        + '<kml xmlns="http://earth.google.com/kml/2.0">\n'
        + '  <Document>\n'
        + '    <name>Paths</name>\n'
        + '    <description>Examples of paths. Note that the tessellate tag is by default\n'
        + '      set to 0. If you want to create tessellated lines, they must be authored\n'
        + '      (or edited) directly in KML.</description>\n'
        + '    <Placemark>\n'
        + '      <name>Absolute Extruded</name>\n'
        + '      <description>Transparent green wall with yellow outlines</description>\n'
        + '\t<Style>\n'
        + '\t<LineStyle>\n'
        + '<width>5</width>\n'
        + '<color>#ff0000ff</color>\n'
        + '</LineStyle>\n'
        + '</Style>\n'
  }
    // Render method of the Map component
    render() {
      this.setUpGoogleMap()
      if(this.refs.Gmap){
        //console.log("google path: ", this.googleMap.props.children[0])
        //console.log("google marker: ", this.googleMap.props.children[1])
        if(this.googleMap.props.children[0].props.path.length > 0){
          console.log(this.getKmlString())
        }
      }

        // Return the stuff we actually want rendered on the page
        return (
            this.googleMap
        )
    }

  setUpGoogleMap() {
    this.googleMap =

        <GoogleMap
            defaultCenter={{
              lat: 0,
              lng: 0
            } /*Sets the default center for the map to start at */}
            defaultZoom={3 /* Sets the default zoom ie how much of the world is on the screen*/}
            ref = "Gmap"
        >
          {/* Everything that is in between <GoogleMap> and </GoogleMap> get rendered onto the
                map. Polyline is an easy google library that draws lines from coordiates.*/}
            <Polyline
                visible={true /*Make sure the map is visable on screen*/}

                path={this.buildCoordinateArray()/* Set polyline path to the coordiates array*/}

                options={{
                  /* This is a list of optional things line line color and line weight this does not
                        need to be included. See documentation for more options*/
                  strokeColor: '#ff31a6',
                  strokeWeight: 4,
                }}
            />
            <Marker
                name={"Start"}
                position={this.getStartLocation() /* Set start location */}
            />

          {/*Close our GoogleMap*/}
        </GoogleMap>;
  }
}
// This is important what this does is it wraps the Map module in
// a withGoogleMap module. Without this the map will not load
export default withGoogleMap(Map)
