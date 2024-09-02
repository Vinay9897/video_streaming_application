import React, { useState } from "react";
import videoLogo from "../assets/upload.png";
import { Card, FileInput, Label, Textarea, TextInput } from "flowbite-react";
import { Button } from "flowbite-react";


function VideoUpload() {

    const [selectedFile, setSelectedFile] = useState(null);
    const [progress, setProgress] = useState(0);
    const [meta, setMeta] = useState({
        title: "",
        description: ""
    });
    const [uploading, setUploading] = useState(false);
    const [message, setMessage] = useState("");

    function handleFileChange(event) {
        setSelectedFile(event.target.files[0])
        console.log(event);
    }

    function handleForm(formEvent) {
        console.log(formEvent.target);
        formEvent.preventDefault();
    }

    return <div className="text-white">
        <Card className="flex justify-center ">
            <div>
                <h1 className="font-semibold text-center">
                    Upload Video
                </h1>

                <form
                    noValidate
                    onSubmit={handleForm}
                    className="items-center my-2">

                    <div className="mb-3">
                        <div className="pb-2 block">
                            <Label htmlFor="file-upload" value="Upload file" />
                        </div>
                        <TextInput name="title" id="file-upload" placeholder="Enter title" />
                    </div>

                    {/* textarea */}
                    <div className="mb-3 max-w-md">
                        <div className="pb-2 block">
                            <Label htmlFor="comment" value="Video Description" />
                        </div>
                        <Textarea name="description" id="comment" placeholder="write a video description" required rows={4} />
                    </div>

                    <div className="flex justify-center  items-center space-x-6">
                        <div class="shrink-0">
                            <img class="h-16 w-16 object-cover rounded-sm" src={videoLogo} alt="Current profile photo" />
                        </div>

                        <label class="block">
                            <span class="sr-only">Choose profile photo</span>
                            <input name="file" type="file"
                                onChange={handleFileChange}
                                class="block w-full text-sm text-slate-500
                            file:mr-4 file:py-2 file:px-4
                            file:rounded-full file:border-0
                            file:text-sm file:font-semibold
                             file:bg-violet-50 file:text-violet-700
                             hover:file:bg-violet-100"/>
                        </label>
                    </div>
                    <div className="flex justify-center">
                        <Button onClick={handleForm}>
                            Upload Video</Button>
                    </div>
                </form>
            </div>

        </Card>
    </div>
}

export default VideoUpload;